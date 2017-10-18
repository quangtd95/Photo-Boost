package com.quangtd.photoeditor.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.quangtd.photoeditor.model.response.CategorySticker;
import com.quangtd.photoeditor.view.fragment.FragmentListSticker;

import java.util.List;

/**
 * QuangTD on 10/17/2017.
 */

public class ViewPagerListStickerAdapter extends FragmentStatePagerAdapter {
    private List<CategorySticker> mCategoryStickers;

    public ViewPagerListStickerAdapter(FragmentManager fm, List<CategorySticker> categoryStickers) {
        super(fm);
        this.mCategoryStickers = categoryStickers;
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentListSticker.initFragment(mCategoryStickers.get(position));
    }

    @Override
    public int getCount() {
        Log.e("TAGG",mCategoryStickers.size()+"");
        return mCategoryStickers.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mCategoryStickers.get(position).getTitle();
    }
}