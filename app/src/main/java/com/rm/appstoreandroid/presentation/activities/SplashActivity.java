package com.rm.appstoreandroid.presentation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.rm.appstoreandroid.R;
import com.rm.appstoreandroid.controllers.SplashActivityController;
import com.rm.appstoreandroid.model.Image;

public class SplashActivity extends AppCompatActivity {

    private SplashActivityController splashActivityController;

    private ProgressBar progressBar;

    private boolean timerOut = false;

    private ImageView ivIcon;

    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initComponents();

    }

    private void initComponents() {
        ivIcon  = (ImageView) findViewById(R.id.iv_icon);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.splash);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        splashActivityController = new SplashActivityController(this);
        splashActivityController.getCategoriesFromResources();

    }

    public void animate(){
        Animation a = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        a.reset();
        coordinatorLayout.clearAnimation();
        coordinatorLayout.startAnimation(a);
    }

    public boolean isTimerOut() {
        return timerOut;
    }
}
