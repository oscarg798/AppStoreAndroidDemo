package com.rm.appstoreandroid.presentation.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.rm.appstoreandroid.R;
import com.rm.appstoreandroid.model.dto.AppDTO;
import com.rm.appstoreandroid.presentation.adapters.AppAdapter;

import java.util.List;

public class AppActivity extends AppCompatActivity {

    private List<AppDTO> appDTOList;
    private Toolbar toolbar;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        initViewComponents();
        initComponents();

    }

    public void initViewComponents() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    }

    public void initComponents() {
        Bundle bundleActivity = getIntent().getExtras();
        if (bundleActivity != null) {
            appDTOList = (List<AppDTO>) bundleActivity
                    .getSerializable(getString(R.string.apps_key));

            String title = bundleActivity.getString(getString(R.string.category_key));
            if (title == null) {
                title = "Aplicaciones por Categoria";
            }

            getSupportActionBar().setTitle(title);

            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),
                    LinearLayoutManager.VERTICAL, false);

            recyclerView.setLayoutManager(mLayoutManager);

            recyclerView.setHasFixedSize(true);

            AppAdapter appAdapter = new AppAdapter(appDTOList, getApplicationContext());

            recyclerView.setAdapter(appAdapter);

        }
    }

}
