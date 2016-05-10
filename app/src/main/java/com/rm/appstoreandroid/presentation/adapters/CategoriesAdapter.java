package com.rm.appstoreandroid.presentation.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rm.appstoreandroid.R;
import com.rm.appstoreandroid.model.dto.CategoryDTO;
import com.rm.appstoreandroid.presentation.view_holder.CategoryViewHolder;

import java.util.List;

/**
 * Created by oscargallon on 5/9/16.
 */
public class CategoriesAdapter extends RecyclerView.Adapter<CategoryViewHolder> {

    public CategoriesAdapter(Context context, List<CategoryDTO> categoryDTOList) {
        this.context = context;
        this.categoryDTOList = categoryDTOList;
    }

    private List<CategoryDTO> categoryDTOList;
    private final Context context;


    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_item, parent, false);
        return new CategoryViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        CategoryDTO categoryDTO = this.categoryDTOList.get(position);
        holder.getTvCategoryLabel().setText(categoryDTO.getLabel());
        holder.getIvCategoryIcon().setImageDrawable(context
                .getResources().getDrawable(categoryDTO.getImage()));
    }

    @Override
    public int getItemCount() {
        return this.categoryDTOList.size();
    }
}
