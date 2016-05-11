package com.rm.appstoreandroid.presentation.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        initViewComponents();
        initComponents();
    }

    private void initViewComponents() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    }

    private void initComponents() {
        dashBoardActivityController = new DashBoardActivityController(this);

        Bundle activityBundle = getIntent().getExtras();
        if (activityBundle != null) {
            final List<CategoryDTO> categoryDTOList =
                    (List<CategoryDTO>) activityBundle.getSerializable(getString(R.string.categories_key));



            if (categoryDTOList != null) {
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),
                        LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(mLayoutManager);
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
    public void onBackPressed() {

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

}
