package com.rm.appstoreandroid.presentation.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Transition;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
;
import com.rm.appstoreandroid.R;
import com.rm.appstoreandroid.controllers.SplashActivityController;
import com.rm.appstoreandroid.model.dto.CategoryDTO;

import java.io.Serializable;
import java.util.List;

public class SplashActivity extends AppCompatActivity {

    private SplashActivityController splashActivityController;

    private ProgressBar progressBar;

    private CoordinatorLayout coordinatorLayout;

    private LinearLayout lySplash;

    private ImageView ivAppIcon;

    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initViewComponents();
        initComponents();

    }

    private void initViewComponents() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.cl_splash);
        ivAppIcon = (ImageView) findViewById(R.id.iv_app_icon);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        lySplash = (LinearLayout) findViewById(R.id.ly_splash);

    }

    private void initComponents() {
        splashActivityController = new SplashActivityController(this);
        splashActivityController.getCategoriesFromResources();

    }


    public void animateActivityOut(List<CategoryDTO> categoryDTOList) {
        ActivityOptionsCompat activityOptionsCompat =
                ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(), R.anim.left_right,
                        R.anim.rigth_left);
        Intent intent = new Intent(this,DashBoardActivity.class);
        intent.putExtra(getString(R.string.categories_key), (Serializable) categoryDTOList);
        ActivityCompat.startActivity(this,intent, activityOptionsCompat.toBundle());
    }


}
