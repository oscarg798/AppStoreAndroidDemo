package com.rm.appstoreandroid.presentation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.rm.appstoreandroid.R;
import com.rm.appstoreandroid.controllers.SplashActivityController;

public class SplashActivity extends AppCompatActivity {

    private SplashActivityController splashActivityController;

    private ProgressBar progressBar;

    private boolean timerOut = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initComponents();

    }

    private void initComponents() {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        splashActivityController = new SplashActivityController(this);
        splashActivityController.getCategoriesFromResources();

    }

    public boolean isTimerOut() {
        return timerOut;
    }
}
