package com.rm.appstoreandroid.presentation.view_holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rm.appstoreandroid.R;

/**
 * Created by oscargallon on 5/9/16.
 */
public class CategoryViewHolder extends RecyclerView.ViewHolder {

    private TextView tvCategoryLabel;

    private ImageView ivCategoryIcon;


    public CategoryViewHolder(View itemView) {
        super(itemView);
        setTvCategoryLabel((TextView) itemView.findViewById(R.id.tv_category_label));
        setIvCategoryIcon((ImageView) itemView.findViewById(R.id.iv_category));
    }

    public TextView getTvCategoryLabel() {
        return tvCategoryLabel;
    }

    public void setTvCategoryLabel(TextView tvCategoryLabel) {
        this.tvCategoryLabel = tvCategoryLabel;
    }

    public ImageView getIvCategoryIcon() {
        return ivCategoryIcon;
    }

    public void setIvCategoryIcon(ImageView ivCategoryIcon) {
        this.ivCategoryIcon = ivCategoryIcon;
    }
}
