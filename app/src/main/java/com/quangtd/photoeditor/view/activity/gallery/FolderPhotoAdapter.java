package com.quangtd.photoeditor.view.activity.gallery;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.quangtd.photoeditor.R;
import com.quangtd.photoeditor.model.data.AlbumImage;

import java.io.File;
import java.util.List;


/**
 * QuangTD on 4/29/2017.
 */

public class FolderPhotoAdapter extends RecyclerView.Adapter {
    public interface OnClickItemFolderListener {
        void onClickItem(int position);
    }

    private Context mContext;
    private List<AlbumImage> mLocalImages;
    private OnClickItemFolderListener mOnClickItemFolderListener;

    public void setOnClickItemFolderListener(OnClickItemFolderListener onClickItemFolderListener) {
        this.mOnClickItemFolderListener = onClickItemFolderListener;
    }

    public FolderPhotoAdapter(Context context, List<AlbumImage> localImages) {
        this.mContext = context;
        this.mLocalImages = localImages;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_folder, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ItemViewHolder) holder).setData(mLocalImages.get(position));
    }

    @Override
    public int getItemCount() {
        return mLocalImages != null ? mLocalImages.size() : 0;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNameFolder;
        private TextView tvPathFolder;
        private ImageView imgFolder;

        ItemViewHolder(View itemView) {
            super(itemView);
            tvNameFolder = (TextView) itemView.findViewById(R.id.tvNameFolder);
            tvPathFolder = (TextView) itemView.findViewById(R.id.tvPathFolder);
            imgFolder = (ImageView) itemView.findViewById(R.id.imgFolder);

            itemView.setOnClickListener(v -> {
                if (mOnClickItemFolderListener != null) {
                    mOnClickItemFolderListener.onClickItem(getLayoutPosition());
                }
            });
        }

        private void setData(AlbumImage albumImage) {
            tvNameFolder.setText(albumImage.getName());
            tvPathFolder.setText(albumImage.getPath());
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.centerCrop();
            requestOptions.override(150, 150);
            Glide.with(mContext).setDefaultRequestOptions(requestOptions).load(new File(albumImage.getFirstImage())).into(imgFolder);
        }
    }
}
