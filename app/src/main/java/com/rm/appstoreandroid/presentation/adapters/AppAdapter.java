package com.rm.appstoreandroid.presentation.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rm.appstoreandroid.R;
import com.rm.appstoreandroid.Utils.Utils;
import com.rm.appstoreandroid.model.dto.AppDTO;
import com.rm.appstoreandroid.presentation.view_holder.AppViewHolder;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by oscargallon on 5/10/16.
 */
public class AppAdapter extends RecyclerView.Adapter<AppViewHolder> {

    private List<AppDTO> appDTOList;

    private Context context;

    public AppAdapter(List<AppDTO> appDTOList, Context context) {
        this.appDTOList = appDTOList;
        this.context = context;
    }

    @Override
    public AppViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.app_item, parent, false);
        return new AppViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AppViewHolder holder, int position) {
        final AppDTO appDTO = this.appDTOList.get(position);
        holder.getTvAppName().setText(appDTO.getName());
        if (appDTO.getImages() != null && appDTO.getImages().get(0) != null) {
            Picasso.with(context).load(appDTO.getImages().get(0).getLink())
                    .placeholder(context.getResources().getDrawable(Utils.getCategoryDrawable(appDTO.getCategory(),
                            context)))
                    .into(holder.getIvAppIcon(), new Callback() {
                        @Override
                        public void onSuccess() {
                            holder.getIvAppIcon()
                                    .setColorFilter(Color.TRANSPARENT);
                        }

                        @Override
                        public void onError() {
                            holder.getIvAppIcon().setImageDrawable(context
                                    .getResources().getDrawable(Utils.getCategoryDrawable(appDTO.getCategory(),
                                            context)));
                        }
                    });
        } else {
            holder.getIvAppIcon().setImageDrawable(context
                    .getResources().getDrawable(Utils.getCategoryDrawable(appDTO.getCategory(),
                            context)));
        }

    }

    @Override
    public int getItemCount() {
        return this.appDTOList.size();
    }
}
