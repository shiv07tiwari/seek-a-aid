package com.example.shivansh.seek_a_aid;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class Newcomplaint extends AppCompatActivity {
    LayoutInflater inflator;


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

        Spinner spinner = (Spinner)findViewById(R.id.whichTag);
        spinner.setAdapter(new NewAdapter());
        inflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

      //  spinner.OnItemSelectedListener(new )
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
            textView.setText("Health Center");
            return convertView;
        }


    }
}
