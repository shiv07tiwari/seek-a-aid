package com.example.shivansh.seek_a_aid;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class UserDetailActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    static TextView user_name;
    static TextView user_email;
    static TextView user_type;
    static TextView user_credits;
    static String name;
    static String email;
    static String type;
    static String credits;
    private static RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    static String userid;
    private static RecyclerView.Adapter adapter;
    private static ArrayList<complainDetails> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        auth=FirebaseAuth.getInstance();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ArrayList<complainDetails> al = HomeActivity.extradata;
        ArrayList<complainDetails> all = new ArrayList<complainDetails>();
        for(int i=0;i<al.size();i++) {
            if(al.get(i).getUseremail().equals(email)) {
                all.add(al.get(i));
            }
        }

        //user_email.setText(auth.getCurrentUser().getEmail());
        user_name=findViewById(R.id.user_profile_name);
        user_name.setText(name);
        user_email=findViewById(R.id.user_profile_email);
        user_email.setText(email);
        user_type=findViewById(R.id.user_profile_type);
        user_type.setText(type);
        user_credits=findViewById(R.id.user_profile_credits);
        user_credits.setText(credits);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view_user_profile);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new HomeActivityAdapter(all);
        recyclerView.setAdapter(adapter);

    }

}
