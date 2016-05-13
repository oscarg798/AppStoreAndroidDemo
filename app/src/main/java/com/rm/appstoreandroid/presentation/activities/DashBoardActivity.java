package com.rm.appstoreandroid.presentation.activities;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.rm.appstoreandroid.R;
import com.rm.appstoreandroid.controllers.DashBoardActivityController;
import com.rm.appstoreandroid.model.dto.CategoryDTO;
import com.rm.appstoreandroid.presentation.adapters.CategoriesAdapter;
import com.rm.appstoreandroid.presentation.listeners.RecyclerItemOnClickListener;
import com.rm.appstoreandroid.presentation.listeners.interfaces.OnItemClickListener;

import java.util.List;

public class DashBoardActivity extends AppCompatActivity {

    private DashBoardActivityController dashBoardActivityController;

    private Toolbar toolbar;

    private RecyclerView recyclerView;

    private CategoriesAdapter categoriesAdapter;

    private CoordinatorLayout clDashboard;

    private RelativeLayout rlDashBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isLargeLayout = getResources().getBoolean(R.bool.isLargeLayout);
        if(isLargeLayout) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        setContentView(R.layout.activity_dash_board);
        initViewComponents();
        initComponents();
        animateActivityIn();
    }

    private void initViewComponents() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Categorias");
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        rlDashBoard = (RelativeLayout) findViewById(R.id.rl_dashboard);
        clDashboard = (CoordinatorLayout) findViewById(R.id.cl_dashboard);
    }

    private void initComponents() {
        dashBoardActivityController = new DashBoardActivityController(this);

        Bundle activityBundle = getIntent().getExtras();
        if (activityBundle != null) {
            final List<CategoryDTO> categoryDTOList =
                    (List<CategoryDTO>) activityBundle.getSerializable(getString(R.string.categories_key));


            if (categoryDTOList != null) {

                GridLayoutManager gridLayoutManager = null;
                if (getResources().getConfiguration().orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                    gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
                }else{
                    gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                }


                recyclerView.setLayoutManager(gridLayoutManager);
                recyclerView.setHasFixedSize(true);
                final CategoriesAdapter categoriesAdapter = new CategoriesAdapter(getApplicationContext(),
                        categoryDTOList);
                recyclerView.setAdapter(categoriesAdapter);

                recyclerView.addOnItemTouchListener(new RecyclerItemOnClickListener(getApplicationContext(),
                        new OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                dashBoardActivityController
                                        .getAppsDTOFromCategory(categoryDTOList.get(position).getTerm(),
                                                categoryDTOList.get(position).getLabel());
                            }
                        }));
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dash_board, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void animateActivityIn() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.pull_app);
        if (Build.VERSION.SDK_INT >= 21) {
            animation.setDuration(500);
        }
        animation.reset();
        rlDashBoard.clearAnimation();
        rlDashBoard.startAnimation(animation);
    }


}
