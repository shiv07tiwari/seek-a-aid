package com.example.shivansh.seek_a_aid;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.ExecutionException;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private FirebaseAuth auth;
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<complainDetails> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        auth=FirebaseAuth.getInstance();
        // Log.e("LOG","User : "+auth.getCurrentUser().getEmail());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        String result=null;
        UserDetailActivity.loadDataofuser loadData = new UserDetailActivity.loadDataofuser();
        try {
            result = loadData.execute(auth.getCurrentUser().getEmail()).get();
            Log.e("log","JSON : "+result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight

                        mDrawerLayout.closeDrawers();
                        if(menuItem.getItemId()==R.id.logout)
                        {
                            auth.signOut();
                            Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
                            startActivity(intent);
                            return true;
                        }
                        if(menuItem.getItemId()==R.id.profie) {
                            Intent intent = new Intent(HomeActivity.this,UserDetailActivity.class);
                            startActivity(intent);
                            return true;
                        }
                        if(menuItem.getItemId()==R.id.nav_tabs) {
                            Intent intent = new Intent(HomeActivity.this,TextTabActivity.class);
                            startActivity(intent);
                            return true;
                        }

                        return true;
                    }
                });

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        data = new ArrayList<complainDetails>();
        for (int i = 0; i < complains.complainsArray.length; i++) {
            data.add(new complainDetails(
                    complains.id_[i],
                    complains.tag[i],
                    complains.complainsArray[i]
            ));
        }

        adapter = new HomeActivityAdapter(data);
        recyclerView.setAdapter(adapter);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.e("log","Kuch select hu");
        switch (item.getItemId()) {
            case android.R.id.home:
                Log.e("log","home hu");
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


}