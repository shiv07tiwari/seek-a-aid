package com.example.shivansh.seek_a_aid;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

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

        C_Id.setText(dataSet.get(listPosition).getComplainId());
        label.setText(dataSet.get(listPosition).getTag());
        complain.setText(dataSet.get(listPosition).getComplain());

        fun=false;
        fun1=false;

        LButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //implement onClick
                if(fun==false && fun1==false) {
                    LButton.setImageResource(R.drawable.upred);
                    fun=true;
                }
                else if(fun==true) {
                    LButton.setImageResource(R.drawable.up);
                    fun=false;
                }
            }
        });

        ULButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //implement onClick
                if(fun1==false && fun==false) {
                    ULButton.setImageResource(R.drawable.downred);
                    fun1=true;
                }
                else if(fun1==true) {
                    ULButton.setImageResource(R.drawable.down);
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

        RLikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "This is Gymkhana rating!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}