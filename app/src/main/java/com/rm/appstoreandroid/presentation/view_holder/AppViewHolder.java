package com.rm.appstoreandroid.presentation.view_holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rm.appstoreandroid.R;

/**
 * Created by oscargallon on 5/10/16.
 */
public class AppViewHolder extends RecyclerView.ViewHolder {

    private TextView tvAppName;

    private ImageView ivAppIcon;

    public AppViewHolder(View itemView) {
        super(itemView);
        setIvAppIcon((ImageView) itemView.findViewById(R.id.iv_app_icon));
        setTvAppName((TextView) itemView.findViewById(R.id.tv_app_name));
    }

    public TextView getTvAppName() {
        return tvAppName;
    }

    public void setTvAppName(TextView tvAppName) {
        this.tvAppName = tvAppName;
    }

    public ImageView getIvAppIcon() {
        return ivAppIcon;
    }

    public void setIvAppIcon(ImageView ivAppIcon) {
        this.ivAppIcon = ivAppIcon;
    }
}
