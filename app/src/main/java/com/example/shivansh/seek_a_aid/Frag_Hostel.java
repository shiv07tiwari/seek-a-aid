package com.example.shivansh.seek_a_aid;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Frag_Hostel extends Fragment {
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<complainDetails> data;


    public Frag_Hostel() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview=inflater.inflate(R.layout.fragment_frag__hostel, container, false);
        recyclerView = (RecyclerView) rootview.findViewById(R.id.hostelRecycle);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        ArrayList<complainDetails> al = HomeActivity.extradata;
        ArrayList<complainDetails> all = new ArrayList<complainDetails>();
        for(int i=0;i<al.size();i++) {
            if(al.get(i).getTag().equals("Hostel Maintainance")) {
                all.add(al.get(i));
            }
        }

//        data = new ArrayList<complainDetails>();
//        for (int i = 0; i < complains.complainsArray.length; i++) {
//            data.add(new complainDetails(
//                    complains.id_[i],
//                    complains.tag[i],
//                    complains.complainsArray[i]
//            ));
//        }

        adapter = new HomeActivityAdapter(all);
        recyclerView.setAdapter(adapter);
        return rootview;
    }

}
