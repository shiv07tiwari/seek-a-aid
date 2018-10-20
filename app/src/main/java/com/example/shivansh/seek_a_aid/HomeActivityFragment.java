package com.example.shivansh.seek_a_aid;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        return rootview;
    }

}
