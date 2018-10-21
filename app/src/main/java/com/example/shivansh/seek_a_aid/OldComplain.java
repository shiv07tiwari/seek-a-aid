package com.example.shivansh.seek_a_aid;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class OldComplain extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    MyAdapter mAdapter;
    TextView complainView;
    TextView alloted1,alloted2;
    static String comp_id;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_complain);
        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();

        if(actionbar!=null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
        }

        String[] values  = new String[] { "Android", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2" };


        recyclerView = (RecyclerView) findViewById(R.id.statusList);
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }

        alloted1=findViewById(R.id.alloted1);
        alloted2=findViewById(R.id.alloted2);
        complainView=findViewById(R.id.complain1);
        complainView.setMovementMethod(new ScrollingMovementMethod());

        ImageView image = findViewById(R.id.type_image);
        String current_type = UserDetailActivity.type;


        TextView allot_blink = findViewById(R.id.text_please_allot);
        ObjectAnimator anim = ObjectAnimator.ofInt(allot_blink, "backgroundColor", Color.WHITE, Color.RED,
                Color.WHITE);
        anim.setDuration(1500);
        anim.setEvaluator(new ArgbEvaluator());
        anim.setRepeatMode(ValueAnimator.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        anim.start();
        final TextView id = findViewById(R.id.ID);
        TextView rate = findViewById(R.id.blue_per);
        TextView man1 = findViewById(R.id.alloted1);
        TextView man2 = findViewById(R.id.alloted2);
        Intent intent = getIntent();
        final String compid = intent.getStringExtra("compid");

        ArrayList<complainDetails> al = HomeActivity.extradata;
        for(int i=0;i<al.size();i++) {
            if(al.get(i).getComplainId().equals(compid)) {
                comp_id = al.get(i).getComplainId();
                rate.setText(al.get(i).getRating());
                man1.setText(al.get(i).getMember1());
                man2.setText(al.get(i).getMember2());
                id.setText("ID : "+al.get(i).getComplainId());
                complainView.setText(al.get(i).getComplain());
                break;
            }
        }

        allot_blink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(OldComplain.this);
                dialog.setContentView(R.layout.allot_dialog);
                dialog.setTitle("Please Allot Members");
                final Button dialogButton2 = dialog.findViewById(R.id.submit_allotment);
                // if button is clicked, close the custom dialog
                dialogButton2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText name1_t = dialog.findViewById(R.id.member_1_roll);
                        EditText name2_t = dialog.findViewById(R.id.member_2_roll);
                        EditText name1_set = dialog.findViewById(R.id.member_1_name);
                        EditText name2_set = dialog.findViewById(R.id.member_2_name);

                        String realname1 = name1_set.getText().toString();
                        String realname2 = name2_set.getText().toString();

                        String name1 = name1_t.getText().toString();
                        String name2 = name2_t.getText().toString();
                        Log.e("loghidgigis",name1+""+name2);
                        String result;
                        setGymkhana verify = new setGymkhana();
                        try {
                            result = verify.execute(name1,name2).get();
                            Log.e("log",result);
                            Toast.makeText(OldComplain.this,result,Toast.LENGTH_LONG).show();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        TextView update_status = findViewById(R.id.text_update_status);
        update_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final Dialog dialog = new Dialog(OldComplain.this);
                dialog.setContentView(R.layout.update_dialog);
                dialog.setTitle("Update");
                Button dialogButton = dialog.findViewById(R.id.status_submit);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText status_desc = dialog.findViewById(R.id.status_desc_edit);
                        EditText status = dialog.findViewById(R.id.status_edit);
                        String name1 = status_desc.getText().toString();
                        String name2 = status.getText().toString();

                        String result;
                        updatestatus update = new updatestatus();
                        try {
                            result = update.execute(compid,name1,name2).get();
                            Log.e("log",result);
                            Toast.makeText(OldComplain.this,result,Toast.LENGTH_LONG).show();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new MyAdapter(list);
        recyclerView.setAdapter(mAdapter);

    }
    private static class verifyGymkhana extends AsyncTask<String, Void, String> {

        static final String REQUEST_METHOD = "GET";
        public static final int READ_TIMEOUT = 15000;
        public static final int CONNECTION_TIMEOUT = 15000;

        protected String doInBackground(String... userdata) {

            String name1 = userdata[0];
            String name2 = userdata[1];
            String inputLine;
            String result=null;
            //https://prototype-swastik0310.c9users.io/<name>/<email>/<type>
            String base_url = "https://prototype-swastik0310.c9users.io/alot/volunteer/"+name1+"/"+name2;
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
        }
    }
    private static class setGymkhana extends AsyncTask<String, Void, String> {

        static final String REQUEST_METHOD = "GET";
        public static final int READ_TIMEOUT = 15000;
        public static final int CONNECTION_TIMEOUT = 15000;

        protected String doInBackground(String... userdata) {

            String name1 = userdata[0];
            String name2 = userdata[1];
            String inputLine;
            String result=null;
            //https://prototype-swastik0310.c9users.io/<name>/<email>/<type>
            String base_url = "https://prototype-swastik0310.c9users.io/alot/volunteer/"+comp_id+"/"+name1+"/"+name2;
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
        }
    }


    private static class updatestatus extends AsyncTask<String, Void, String> {

        public static final String REQUEST_METHOD = "GET";
        public static final int READ_TIMEOUT = 15000;
        public static final int CONNECTION_TIMEOUT = 15000;

        protected String doInBackground(String... userdata) {

            String id = userdata[0];
            String status_text= userdata[1];
            String status = userdata[2];
            String inputLine;
            String result=null;
            status_text=status_text.replace(" ", "%20");
            status=status.replace(" ", "%20");
            //https://prototype-swastik0310.c9users.io/<name>/<email>/<type>
            String base_url = "https://prototype-swastik0310.c9users.io/updatecomplaint/"+id+"/"+status_text+"/"+status ;
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
                //result = stringBuilder.toString();
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
        }
    }
}
