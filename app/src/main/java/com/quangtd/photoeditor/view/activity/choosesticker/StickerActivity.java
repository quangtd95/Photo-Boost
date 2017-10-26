package com.quangtd.photoeditor.view.activity.choosesticker;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.quangtd.photoeditor.R;
import com.quangtd.photoeditor.global.GlobalDefine;
import com.quangtd.photoeditor.model.cloud.FireBaseUtils;
import com.quangtd.photoeditor.model.data.CategorySticker;
import com.quangtd.photoeditor.model.local.RealmUtils;
import com.quangtd.photoeditor.presenter.PresenterCategorySticker;
import com.quangtd.photoeditor.utils.LogUtils;
import com.quangtd.photoeditor.view.activity.ActivityBase;
import com.quangtd.photoeditor.view.fragment.FragmentListSticker;
import com.quangtd.photoeditor.view.iface.IViewCategorySticker;
import com.quangtd.photoeditor.view.iface.listener.AbstractOnViewPagerChangeListener;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_sticker)
public class StickerActivity extends ActivityBase<PresenterCategorySticker> implements IViewCategorySticker, FragmentListSticker.OnReceiveStickerListener {

    @ViewById(R.id.tvTabName)
    TextView mTvTabName;
    @ViewById(R.id.vpListSticker)
    ViewPager mVpListSticker;
    ViewPagerListStickerAdapter mVpListStickerAdapter;
    @ViewById(R.id.rvCategory)
    RecyclerView mRvCategory;
    CategoryStickerAdapter mCategoryAdapter;
    List<CategorySticker> mCategoryStickers;
    public final String TAG = getClass().getSimpleName();
    private FireBaseUtils mFireBaseUtils;

    @Override protected void init() {
        super.init();
        mFireBaseUtils = FireBaseUtils.getInstance();
        mCategoryStickers = new ArrayList<>();
        mCategoryAdapter = new CategoryStickerAdapter(this, mCategoryStickers);
        mRvCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRvCategory.setAdapter(mCategoryAdapter);
        mCategoryAdapter.setListener(position -> {
            updateUiActivity(position);
            mVpListSticker.setCurrentItem(position);
        });
        getPresenter(this).getListCategory();
        //load cache
        mCategoryStickers.addAll(RealmUtils.getInstance().getList(CategorySticker.class));
        if (mCategoryStickers.size() != 0) {
            updateUiActivity(0);
        }
        mCategoryAdapter.notifyDataSetChanged();

        mVpListStickerAdapter = new ViewPagerListStickerAdapter(getSupportFragmentManager(), mCategoryStickers);
        mVpListStickerAdapter.setOnReceiveStickerListener(this);
        mVpListSticker.setAdapter(mVpListStickerAdapter);

        mVpListSticker.addOnPageChangeListener(new AbstractOnViewPagerChangeListener() {
            @Override public void onPageSelected(int position) {
                updateUiActivity(position);
                mRvCategory.scrollToPosition(position);
            }
        });

    }

    private void updateUiActivity(int position) {
        mTvTabName.setText(mCategoryStickers.get(position).getTitle());
        mCategoryAdapter.setSelected(position);
    }

    @Override protected void onStart() {
        super.onStart();
        mFireBaseUtils.addListener(this);
    }

    @Override protected void onStop() {
        super.onStop();
        mFireBaseUtils.removeListener();
    }

    @Override public Context getContext() {
        return this;
    }

    @Override public void showLoading() {
        showProgressDialog();
    }

    @Override public void hideLoading() {
        dismissProgressDialog();
    }

    @Override public void getListCategorySuccess(List<CategorySticker> categoryStickers) {
        mCategoryStickers.clear();
        mCategoryStickers.addAll(categoryStickers);
        RealmUtils.getInstance().saveListData(mCategoryStickers);
        if (mCategoryStickers.size() != 0) {
            updateUiActivity(0);
        }
        mCategoryAdapter.notifyDataSetChanged();
        mVpListStickerAdapter.notifyDataSetChanged();
    }

    @Override public void getListCategoryFail(String message) {
        LogUtils.e(TAG, message);
    }

    @Click(R.id.imgBack)
    public void onClickBack() {
        finish();
    }

    @Override public void receiveSticker(String pathSticker) {
        Intent intent = new Intent();
        intent.putExtra(GlobalDefine.KEY_STICKER, pathSticker);
        setResult(GlobalDefine.MY_RESULT_CODE_GET_STICKER, intent);
        finish();
    }
}
