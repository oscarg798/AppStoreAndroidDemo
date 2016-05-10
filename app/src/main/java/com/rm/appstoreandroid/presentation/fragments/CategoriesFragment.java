package com.rm.appstoreandroid.presentation.fragments;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rm.appstoreandroid.R;
import com.rm.appstoreandroid.model.dto.CategoryDTO;
import com.rm.appstoreandroid.presentation.adapters.CategoriesAdapter;

import java.util.List;


public class CategoriesFragment extends Fragment {

    private static Bundle activityBundle;

    private RecyclerView recyclerView;

    private CategoriesAdapter categoriesAdapter;

    public CategoriesFragment() {
    }


    public static CategoriesFragment newInstance(Bundle bundle) {
        CategoriesFragment fragment = new CategoriesFragment();
        activityBundle = bundle;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        if (activityBundle != null) {
            List<CategoryDTO> categoryDTOList =
                    (List<CategoryDTO>) activityBundle.getSerializable(getString(R.string.categories_key));

            if (categoryDTOList != null) {
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity()
                        .getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setHasFixedSize(true);
                CategoriesAdapter categoriesAdapter = new CategoriesAdapter(getActivity()
                        .getApplicationContext(), categoryDTOList);
                recyclerView.setAdapter(categoriesAdapter);
            }
        }

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
