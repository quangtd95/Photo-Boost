package com.quangtd.photoeditor.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.storage.StorageReference;
import com.quangtd.photoeditor.R;
import com.quangtd.photoeditor.global.GlobalDefine;
import com.quangtd.photoeditor.model.response.CategorySticker;
import com.quangtd.photoeditor.presenter.PresenterListSticker;
import com.quangtd.photoeditor.utils.LogUtils;
import com.quangtd.photoeditor.view.adapter.ListStickerAdapter;
import com.quangtd.photoeditor.view.iface.IViewListSticker;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * QuangTD on 10/17/2017.
 */

public class FragmentListSticker extends FragmentBase<PresenterListSticker> implements IViewListSticker {
    RecyclerView mRvSticker;
    private GridLayoutManager mGridLayoutManager;
    private ListStickerAdapter mAdapter;
    private List<StorageReference> mStorageReferences;

    CategorySticker mCategorySticker;

    @Accessors(prefix = "m")
    @Setter
    private ListStickerAdapter.OnClickStickerListener mListener;

    public static FragmentListSticker initFragment(CategorySticker categorySticker) {
        FragmentListSticker stickerFragment = new FragmentListSticker();
        Bundle bundle = new Bundle();
        bundle.putParcelable(GlobalDefine.KEY_CATEGORY, categorySticker);
        stickerFragment.setArguments(bundle);
        return stickerFragment;
    }

    @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_sticker, container, false);
    }

   @Override
   public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRvSticker = (RecyclerView) view.findViewById(R.id.rvSticker);
       mCategorySticker = getArguments().getParcelable(GlobalDefine.KEY_CATEGORY);
       mStorageReferences = new ArrayList<>();
       mGridLayoutManager = new GridLayoutManager(getContext(), 3);
       mRvSticker.setLayoutManager(mGridLayoutManager);
       mAdapter = new ListStickerAdapter(getContext(), mStorageReferences, mListener);
       mRvSticker.setAdapter(mAdapter);
       getPresenter(this).getListSticker(mCategorySticker);
    }


    @Override public void showLoading() {

    }

    @Override public void hideLoading() {

    }

    @Override public void getListStickerSuccess(List<StorageReference> storageReferences) {
        mStorageReferences.clear();
        mStorageReferences.addAll(storageReferences);
        mAdapter.notifyDataSetChanged();
    }

    @Override public void getListStickerFailure(String message) {
        LogUtils.e(TAG, message);
    }
}
