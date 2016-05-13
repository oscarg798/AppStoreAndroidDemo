package com.rm.appstoreandroid.presentation.activities;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.rm.appstoreandroid.R;
import com.rm.appstoreandroid.controllers.SplashActivityController;

public class SplashActivity extends AppCompatActivity implements Animation.AnimationListener {

    private SplashActivityController splashActivityController;

    private ProgressBar progressBar;

    private CoordinatorLayout coordinatorLayout;

    private LinearLayout lySplash;

    private ImageView ivAppIcon;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isLargeLayout = getResources().getBoolean(R.bool.isLargeLayout);
        if(isLargeLayout) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
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

    @Override
    protected void onStop() {
        super.onStop();
        splashActivityController.tryToCloseDB();


    }

    public void animateActivityOut() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        animation.reset();
        animation.setAnimationListener(this);
        coordinatorLayout.clearAnimation();
        coordinatorLayout.startAnimation(animation);

    }


    @Override
    public void onAnimationStart(Animation animation) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                splashActivityController.goToDashBoard();
            }
        }, 300);
    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
