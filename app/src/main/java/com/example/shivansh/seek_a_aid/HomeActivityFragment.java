package com.example.shivansh.seek_a_aid;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

/**
 * A placeholder fragment containing a simple view.
 */
public class HomeActivityFragment extends Fragment {
    private FlowingDrawer mDrawer;

    public HomeActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview =  inflater.inflate(R.layout.fragment_home, container, false);
        Button newCom = rootview.findViewById(R.id.newcom);
        Button oldCom = rootview.findViewById(R.id.oldcom);
        newCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),Newcomplaint.class);
                startActivity(i);
            }
        });
        oldCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),OldComplain.class);
                startActivity(i);

            }
        });
        return rootview;
    }

}
