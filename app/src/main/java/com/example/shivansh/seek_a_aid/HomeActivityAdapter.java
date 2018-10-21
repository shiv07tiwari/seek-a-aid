package com.example.shivansh.seek_a_aid;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class HomeActivityAdapter extends RecyclerView.Adapter<HomeActivityAdapter.MyViewHolder> {

    private ArrayList<complainDetails> dataSet;
    boolean fun,fun1;
    Context context;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView Complaint_Id;
        TextView Tag;
        TextView BlueLikes, RedLikes;
        TextView complain;
        ImageView likeButton;
        ImageView unlikebutton;
        ImageView report;


        public MyViewHolder(View itemView) {
            super(itemView);
            this.Complaint_Id = (TextView) itemView.findViewById(R.id.complaint_id);
            this.Tag = (TextView) itemView.findViewById(R.id.tag);
            this.complain = (TextView) itemView.findViewById(R.id.complain);
            this.BlueLikes = (TextView) itemView.findViewById(R.id.blueLikes);
            this.RedLikes = (TextView) itemView.findViewById(R.id.redLikes);
            this.likeButton=(ImageView)itemView.findViewById(R.id.like);
            this.unlikebutton=(ImageView)itemView.findViewById(R.id.unlike);
            this.report = itemView.findViewById(R.id.info);
        }
    }

    public HomeActivityAdapter(ArrayList<complainDetails> data) {
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.complaint_card, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        context=parent.getContext();
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView C_Id=holder.Complaint_Id;
        TextView label=holder.Tag;
        TextView BLikes=holder.BlueLikes, RLikes=holder.RedLikes;
        TextView complain=holder.complain;
        final ImageView LButton= holder.likeButton;
        final ImageView ULButton=holder.unlikebutton;
        final ImageView REPButton = holder.report;

        final String complaintid = dataSet.get(listPosition).getComplainId();

        C_Id.setText(dataSet.get(listPosition).getComplainId());
        label.setText(dataSet.get(listPosition).getTag());
        complain.setText(dataSet.get(listPosition).getComplain());

        complain.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i=new Intent(v.getContext(),OldComplain.class);
                i.putExtra("compid",dataSet.get(listPosition).getComplainId());
                v.getContext().startActivity(i);
            }
        });

        fun=false;
        fun1=false;

        REPButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"Click I",Toast.LENGTH_LONG).show();
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"recipient@example.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
                i.putExtra(Intent.EXTRA_TEXT   , "body of email");
                v.getContext().startActivity(i);

            }
        });

        LButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //implement onClick
                if(fun==false && fun1==false) {
                    String result = null;

                    likemanage LM10 = new likemanage();
                    try {
                        result = LM10.execute(complaintid,"increaselike").get();
                        Log.e("log result",result);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    LButton.setImageResource(R.drawable.upred);//like hua
                    fun=true;
                }
                else if(fun==true) {
                    String result = null;

                    likemanage LM10 = new likemanage();
                    try {
                        result = LM10.execute(complaintid,"decreaselike").get();
                        Log.e("log result",result);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    LButton.setImageResource(R.drawable.up);//like hata
                    fun=false;
                }
            }
        });

        ULButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //implement onClick
                if(fun1==false && fun==false) {
                    String result = null;

                    likemanage LM10 = new likemanage();
                    try {
                        result = LM10.execute(complaintid,"increasedislike").get();
                        Log.e("log result",result);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    ULButton.setImageResource(R.drawable.downred);//unlike hua
                    fun1=true;
                }
                else if(fun1==true) {
                    String result = null;

                    likemanage LM10 = new likemanage();
                    try {
                        result = LM10.execute(complaintid,"decreasedislike").get();
                        Log.e("log result",result);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    ULButton.setImageResource(R.drawable.down);//iunlike hata
                    fun1=false;
                }
            }
        });

        BLikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "This is user rating!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private class likemanage extends AsyncTask<String, Void, String> {

        public static final String REQUEST_METHOD = "GET";
        public static final int READ_TIMEOUT = 15000;
        public static final int CONNECTION_TIMEOUT = 15000;

        protected String doInBackground(String... userdata) {

            String caseURL = userdata[1];
            String cID = userdata[0];

            String inputLine;
            String result=null;
            //https://prototype-swastik0310.c9users.io/<name>/<email>/<type>
            String base_url = "https://prototype-swastik0310.c9users.io/"+caseURL+"/"+cID;
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

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}