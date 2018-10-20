package com.example.shivansh.seek_a_aid;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class Newcomplaint extends AppCompatActivity {
    LayoutInflater inflator;
    String complain;
    String tagString=null;
    FirebaseAuth firebaseAuth;
    String emailID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newcomplaint);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();

        if(actionbar!=null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
        }
        TextView textView =(TextView)findViewById(R.id.label);
        textView.setText("Enter your Complain :");
        final EditText editText = (EditText)findViewById(R.id.complain_text);



        Spinner spinner = (Spinner)findViewById(R.id.whichTag);
        spinner.setAdapter(new NewAdapter());
        //id number get as extras
        
        spinner.setSelection(1);
        inflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView tag = view.findViewById(R.id.nameOfTag);
                tagString=tag.getText().toString();
                //Toast.makeText(getApplicationContext(),tagString,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(),"Select a Tag",Toast.LENGTH_SHORT).show();

            }
        });
        emailID = firebaseAuth.getInstance().getCurrentUser().getEmail();
        Button sub =(Button)findViewById(R.id.submit);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tagString!=null) {
                    complain = editText.getText().toString();
                    Log.e("log",complain);
                    String result = null;

                    sendComplainDetails senddetails = new sendComplainDetails();
                    try {
                        result = senddetails.execute(emailID,complain,tagString).get();
                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }

    class NewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Object getItem(int arg0) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflator.inflate(R.layout.icon_and_text, null);
            }

            TextView textView = convertView.findViewById(R.id.nameOfTag);
            if (position==0) {
                textView.setText("Health Center");
            }
            else if (position==1) {
                textView.setText("Mess commitee");
            }
            return convertView;
        }


    }

    private class sendComplainDetails extends AsyncTask<String, Void, String> {

        public static final String REQUEST_METHOD = "GET";
        public static final int READ_TIMEOUT = 15000;
        public static final int CONNECTION_TIMEOUT = 15000;

        protected String doInBackground(String... userdata) {

            String emailID = userdata[0];
            String complain = userdata[1];
            String tag = userdata[2];
            String inputLine;
            String result=null;
            complain=complain.replace(" ", "%20");
            tag=tag.replace(" ", "%20");
            //https://prototype-swastik0310.c9users.io/<name>/<email>/<type>
            String base_url = "https://prototype-swastik0310.c9users.io/newcomplaint/"+emailID+"/"+complain+"/"+tag;
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
                result = stringBuilder.toString();;
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
