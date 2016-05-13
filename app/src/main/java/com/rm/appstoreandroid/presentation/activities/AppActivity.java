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

/**
 * Actividad para mostrar una lista de aplicaciones
 * 
 */
public class AppActivity extends AppCompatActivity {

    /**
     * Lista de aplicaciones
     */
    private List<AppDTO> appDTOList;

    /**
     * Toolbar
     */
    private Toolbar toolbar;

    /**
     * RecyclerView para mostrar las actividades
     */
    private RecyclerView recyclerView;

    /**
     * Coordinator layout de la actividad
     */
    private CoordinatorLayout clApp;

    /**
     * Relative layout de la actividad
     */
    private RelativeLayout rlApp;

    /**
     * Controlador de la actividad
     */
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

    /**
     * Metodo que inicializa los componentes visuales de la actividad
     */
    public void initViewComponents() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        clApp = (CoordinatorLayout) findViewById(R.id.cl_app);
        rlApp = (RelativeLayout) findViewById(R.id.rl_app);
    }

    /**
     * MEtodo que inicia la logica de la actividad
     */
    public void initComponents() {
        appActivityController = new AppActivityController(this);

        /**
         * Tratamos de obtener las aplicaciones que nos paso la
         * actividad que nos llamo
         */
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

            /**
             * Agregamos evento de click para una actividad de la lista
             */
            recyclerView.addOnItemTouchListener(new RecyclerItemOnClickListener(getApplicationContext(),
                    new OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {

                            /**
                             * Definimos animacion personalizada
                             * con elemento compartido
                             */
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

    /**
     * Metodo que anima la entrada de la actividad
     */
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
