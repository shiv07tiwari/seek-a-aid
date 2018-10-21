package com.example.shivansh.seek_a_aid;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private FirebaseAuth auth;
    public static RecyclerView.Adapter adapter;
    private static RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<complainDetails> data;
    private static complaintArray CA;
    private static Context context;
    public static ArrayList<complainDetails> extradata;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        auth=FirebaseAuth.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        String result=null;
        //create user
        getComplaints senddetails = new getComplaints();
        try {
            result = senddetails.execute(auth.getCurrentUser().getEmail()).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

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
                            finish();
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

                        if(menuItem.getItemId()==R.id.addcomp) {
                            Intent intent = new Intent(HomeActivity.this,Newcomplaint.class);
                            startActivity(intent);
                            return true;
                        }

                        return true;
                    }
                });
        CA = new complaintArray();
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
//        layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//
//
//        adapter = new HomeActivityAdapter(data);
//        recyclerView.setAdapter(adapter);
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


    //    @Override
//    protected void onResume() {
//        super.onResume();
//        adapter.notifyDataSetChanged();
//    }

    static class getComplaints extends AsyncTask<String, Void, String> {

        static final String REQUEST_METHOD = "GET";
        static final int READ_TIMEOUT = 15000;
        static final int CONNECTION_TIMEOUT = 15000;

        protected String doInBackground(String... userdata) {

            String inputLine;
            String result=null;
            //https://prototype-swastik0310.c9users.io/<name>/<email>/<type>
            //https://prototype-swastik0310.c9users.io/all/haha@gmail.com
            String base_url = "https://prototype-swastik0310.c9users.io/all/"+userdata[0];
            Log.e("log","Url : "+base_url);

            try {
                URL myUrl = new URL(base_url);

                //Create a connection
                HttpURLConnection connection =(HttpURLConnection) myUrl.openConnection();

                connection.setRequestMethod(REQUEST_METHOD);
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);

                //Connect to our url
                connection.connect();

                InputStreamReader streamReader = new
                        InputStreamReader(connection.getInputStream());
                //Create a new buffered reader and String Builder
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();
                //Check if the line we are reading is not null
                while((inputLine = reader.readLine()) != null){
                    stringBuilder.append(inputLine);
                }
                //Close our InputStream and Buffered reader
                reader.close();
                streamReader.close();
                //Set our result equal to our stringBuilder
                result = stringBuilder.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        protected void onProgressUpdate(Integer... progress) {

        }

        protected void onPostExecute(String result) {
           // Log.e("lobjujdgg",result);
            JSONArray jsonArray ;
            try {
                jsonArray = new JSONArray(result);

                JSONObject obj = jsonArray.getJSONObject(0);
                Log.e("log", String.valueOf(obj));

                String userid = obj.getString("_id");
                String username = obj.getString("name");
                String useremail = obj.getString("email");
                String usertype = obj.getString("type");
                String credits = obj.getString("credits");
                Log.e("log",userid+" "+username+" "+useremail+" "+usertype+" "+credits);


                UserDetailActivity.name=username;
                UserDetailActivity.email=useremail;
                UserDetailActivity.type=usertype;
                UserDetailActivity.userid=userid;
                UserDetailActivity.credits=credits;

                for(int i=1;i<jsonArray.length();i++) {
                    JSONObject object = jsonArray.getJSONObject(i);

                    String comp_id=object.getString("_id");
                    String comp_tag = object.getString("tag");
                    String comp_email = object.getString("email");
                    String comp_content=object.getString("content");
                    String comp_rating = object.getString("rating");
                    String comp_man1 = object.getString("alotedMember1");
                    String comp_man2=object.getString("alotedMember2");

                    ArrayList<String> comp_status = new ArrayList<>();
                    JSONArray jArray = object.getJSONArray("status");
                    if (jArray != null) {
                        for (int j=0;j<jArray.length();j++){
                            comp_status.add(jArray.getString(j));
                        }
                    }
                    ArrayList<String> comp_status_desc = new ArrayList<>();
                    JSONArray jArray2 = object.getJSONArray("status");
                    if (jArray != null) {
                        for (int j=0;j<jArray2.length();j++){
                            comp_status_desc.add(jArray.getString(j));
                        }
                    }
                    Log.e("log",comp_id+" "+comp_tag+" "+comp_content+" "+comp_email+" "+comp_status+" "+comp_status_desc);
                    complainDetails CD = new complainDetails(comp_id,comp_tag,comp_content,comp_email,comp_status,comp_status_desc,comp_rating,comp_man1,comp_man2);
                    CA.addcomplaint(CD);
                    data = CA.getCdata();
                    recyclerView.setHasFixedSize(true);
                    layoutManager = new LinearLayoutManager(context);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());

                    extradata=data;
                    adapter = new HomeActivityAdapter(data);
                    recyclerView.setAdapter(adapter);
                }
            } catch (JSONException e) {
                Log.e("log","JSON");
               // e.getCause();
               // e.printStackTrace();
            }
            super.onPostExecute(result);
        }
    }

}