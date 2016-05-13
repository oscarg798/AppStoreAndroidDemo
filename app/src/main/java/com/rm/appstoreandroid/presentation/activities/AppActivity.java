package com.rm.appstoreandroid.presentation.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.rm.androidesentials.model.utils.CoupleParams;
import com.rm.appstoreandroid.R;
import com.rm.appstoreandroid.controllers.AppActivityController;
import com.rm.appstoreandroid.model.App;
import com.rm.appstoreandroid.model.dto.AppDTO;
import com.rm.appstoreandroid.presentation.adapters.AppAdapter;
import com.rm.appstoreandroid.presentation.listeners.RecyclerItemOnClickListener;
import com.rm.appstoreandroid.presentation.listeners.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class AppActivity extends AppCompatActivity {

    private List<AppDTO> appDTOList;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private CoordinatorLayout clApp;
    private RelativeLayout rlApp;
    private AppActivityController appActivityController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        boolean isLargeLayout = getResources().getBoolean(R.bool.isLargeLayout);
        if(isLargeLayout) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        initViewComponents();
        initComponents();
        animateActivityIn();

    }

    public void initViewComponents() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        clApp = (CoordinatorLayout) findViewById(R.id.cl_app);
        rlApp = (RelativeLayout) findViewById(R.id.rl_app);
    }

    public void initComponents() {
        appActivityController = new AppActivityController(this);

        Bundle bundleActivity = getIntent().getExtras();
        if (bundleActivity != null) {
            appDTOList = (List<AppDTO>) bundleActivity
                    .getSerializable(getString(R.string.apps_key));

            String title = bundleActivity.getString(getString(R.string.category_key));
            if (title == null) {
                title = "Aplicaciones por Categoria";
            }

            getSupportActionBar().setTitle(title);

            GridLayoutManager gridLayoutManager = null;
            if (getResources().getConfiguration().orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
            }else{
                gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
            }

            recyclerView.setLayoutManager(gridLayoutManager);

            recyclerView.setHasFixedSize(true);

            AppAdapter appAdapter = new AppAdapter(appDTOList, getApplicationContext());

            recyclerView.setAdapter(appAdapter);

            recyclerView.addOnItemTouchListener(new RecyclerItemOnClickListener(getApplicationContext(),
                    new OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {


                            ActivityOptionsCompat activityOptionsCompat
                                    = ActivityOptionsCompat
                                    .makeSceneTransitionAnimation(AppActivity.this, view, "transition");
                            Intent intent = new Intent(AppActivity.this,
                                    AppDetailActivity.class);

                            intent.putExtra(getString(R.string.app_key),
                                    appDTOList.get(position));

                            ActivityCompat.startActivity(AppActivity.this,
                                    intent, activityOptionsCompat.toBundle());


                        }
                    }));

        }
    }




    @Override
    public void finish() {
        super.finish();

    }

    public void animateActivityIn() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.left_right);
        if (Build.VERSION.SDK_INT >= 21) {
            animation.setDuration(500);
        }
        animation.reset();
        rlApp.clearAnimation();
        rlApp.startAnimation(animation);
    }


}
