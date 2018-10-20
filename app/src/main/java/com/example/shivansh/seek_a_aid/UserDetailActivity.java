package com.example.shivansh.seek_a_aid;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String result;
        auth=FirebaseAuth.getInstance();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //user_email.setText(auth.getCurrentUser().getEmail());
        user_name=findViewById(R.id.user_profile_name);
        user_name.setText(name);
        user_email=findViewById(R.id.user_profile_email);
        user_email.setText(email);
        user_type=findViewById(R.id.user_profile_type);
        user_type.setText(type);
        user_credits=findViewById(R.id.user_profile_credits);
        user_credits.setText(credits);

    }
    static class loadDataofuser extends AsyncTask<String, Void, String> {

        public static final String REQUEST_METHOD = "GET";
        public static final int READ_TIMEOUT = 15000;
        public static final int CONNECTION_TIMEOUT = 15000;


        protected String doInBackground(String... userdata) {

            String email = userdata[0];
            String inputLine;
            String result=null;
            //https://prototype-swastik0310.c9users.io/<name>/<email>/<type>
            String base_url = "https://prototype-swastik0310.c9users.io/user/info/"+email;
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
            super.onPostExecute(result);
            try {
                JSONObject jsonObj = new JSONObject(result);
                name = jsonObj.getString("name");
                email = jsonObj.getString("email");
                type = jsonObj.getString("type");
                credits = jsonObj.getString("credits");
                Log.e("log","Data : "+name +email+type+credits);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
