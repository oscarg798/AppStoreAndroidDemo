package com.rm.appstoreandroid.presentation.activities;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.rm.appstoreandroid.R;
import com.rm.appstoreandroid.controllers.AppDetailActivityController;
import com.rm.appstoreandroid.model.dto.AppDTO;
import com.squareup.picasso.Picasso;

public class AppDetailActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private AppDetailActivityController appDetailActivityController;

    private AppDTO appDTO;

    private TextView tvAppArtist;

    private TextView tvAppPrice;

    private TextView tvAppLink;

    private TextView tvAppSummary;

    private TextView tvAppReleaseDate;

    private ImageView ivAppIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isLargeLayout = getResources().getBoolean(R.bool.isLargeLayout);
        if (isLargeLayout) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        setContentView(R.layout.activity_app_detail);
        initViewComponents();
        initComponents();
    }

    public void initViewComponents() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextAppearance(getApplicationContext(), R.style.app_detail_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tvAppArtist = (TextView) findViewById(R.id.tv_app_artist);
        tvAppLink = (TextView) findViewById(R.id.tv_app_link);
        tvAppPrice = (TextView) findViewById(R.id.tv_app_price);
        tvAppReleaseDate = (TextView) findViewById(R.id.tv_app_release_date);
        tvAppSummary = (TextView) findViewById(R.id.tv_app_summary);
        ivAppIcon = (ImageView) findViewById(R.id.iv_app_icon);

    }

    public void initComponents() {
        appDetailActivityController = new AppDetailActivityController(this);
        if (getIntent() != null && getIntent().getExtras() != null) {
            appDTO = (AppDTO) getIntent().getExtras().getSerializable(getString(R.string.app_key));
            if (appDTO != null) {
                fillViewsWithData(appDTO);
            }
        }
    }

    public void fillViewsWithData(AppDTO appDTO) {

        if (appDTO.getImages() != null) {
            Picasso.with(getApplicationContext())
                    .load(appDTO.getImages().get(2).getLink())
                    .into(ivAppIcon);
        }
        getSupportActionBar().setTitle(appDTO.getName());
        tvAppArtist.setText(appDTO.getArtist());
        tvAppLink.setText(appDTO.getAppLink());
        tvAppSummary.setText(appDTO.getSumary());
        tvAppReleaseDate.setText(appDTO.getReleaseDate());
        tvAppPrice.setText("$ " + appDTO.getPrice() + appDTO.getPriceCurrency());
    }


}
