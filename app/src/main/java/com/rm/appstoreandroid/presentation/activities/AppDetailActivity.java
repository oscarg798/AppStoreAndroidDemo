package com.rm.appstoreandroid.presentation.activities;

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

    private FloatingActionButton floatingActionButton;

    private FloatingActionButton fabShare;

    private FloatingActionButton fabWatch;

    private boolean isShowingSubFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_detail);
        initViewComponents();
        initComponents();
    }

    public void initViewComponents() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextAppearance(getApplicationContext(), R.style.app_detail_title);
        setSupportActionBar(toolbar);
        tvAppArtist = (TextView) findViewById(R.id.tv_app_artist);
        tvAppLink = (TextView) findViewById(R.id.tv_app_link);
        tvAppPrice = (TextView) findViewById(R.id.tv_app_price);
        tvAppReleaseDate = (TextView) findViewById(R.id.tv_app_release_date);
        tvAppSummary = (TextView) findViewById(R.id.tv_app_summary);
        ivAppIcon = (ImageView) findViewById(R.id.iv_app_icon);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        fabShare = (FloatingActionButton) findViewById(R.id.fab_share);
        fabWatch = (FloatingActionButton) findViewById(R.id.fab_watch);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShowingSubFab) {
                    return;
                }
                showSubFabs();

            }
        });

        fabShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSubFabs();
            }
        });

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

    public void showSubFabs() {
        Animation showFabShare = AnimationUtils.loadAnimation(getApplication(), R.anim.show_fab);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) fabShare.getLayoutParams();
        layoutParams.rightMargin += (int) (fabShare.getWidth() * 1.7);
        layoutParams.bottomMargin += (int) (fabShare.getHeight() * 0.25);
        fabShare.setLayoutParams(layoutParams);
        fabShare.startAnimation(showFabShare);
        fabShare.setVisibility(View.VISIBLE);
        isShowingSubFab = true;
        fabShare.setClickable(true);

        Animation showFabWatch = AnimationUtils.loadAnimation(getApplication(), R.anim.show_fab);
        CoordinatorLayout.LayoutParams layoutParams2 = (CoordinatorLayout.LayoutParams) fabWatch.getLayoutParams();
        layoutParams.rightMargin += (int) (fabWatch.getWidth() * 1.7);
        layoutParams.bottomMargin += (int) (fabWatch.getHeight() * 0.9);
        fabWatch.setLayoutParams(layoutParams);
        fabWatch.startAnimation(showFabWatch);
        fabWatch.setVisibility(View.VISIBLE);
        fabShare.setClickable(true);

        isShowingSubFab = true;
    }

    public void hideSubFabs() {
        Animation hideFabShare = AnimationUtils.loadAnimation(getApplication(), R.anim.hide_fab);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) fabShare.getLayoutParams();
        layoutParams.rightMargin -= (int) (fabShare.getWidth() * 1.7);
        layoutParams.bottomMargin -= (int) (fabShare.getHeight() * 0.25);
        fabShare.setLayoutParams(layoutParams);
        fabShare.startAnimation(hideFabShare);
        fabShare.setClickable(false);

        Animation hideFabWatch = AnimationUtils.loadAnimation(getApplication(), R.anim.hide_fab);
        CoordinatorLayout.LayoutParams layoutParams2 = (CoordinatorLayout.LayoutParams) fabWatch.getLayoutParams();
        layoutParams.rightMargin -= (int) (fabWatch.getWidth() * 1.7);
        layoutParams.bottomMargin -= (int) (fabWatch.getHeight() * 0.9);
        fabWatch.setVisibility(View.INVISIBLE);
        fabWatch.setLayoutParams(layoutParams);
        fabWatch.startAnimation(hideFabWatch);
        fabWatch.setClickable(false);
        isShowingSubFab = false;
    }

}
