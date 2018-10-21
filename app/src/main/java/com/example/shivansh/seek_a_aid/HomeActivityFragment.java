package com.example.shivansh.seek_a_aid;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.LinearLayout;

import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

/**
 * A placeholder fragment containing a simple view.
 */
public class HomeActivityFragment extends Fragment {
    private FlowingDrawer mDrawer;
    private boolean fabExpanded = false;
    private FloatingActionButton fabSettings;
    private FloatingActionButton FabSave,FabMess,FabAcad;
    private LinearLayout layoutFabSave;
    private LinearLayout layoutFabEdit;
    private LinearLayout layoutFabPhoto;
    private View mShadowView;
    private Context context;
    public HomeActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview =  inflater.inflate(R.layout.fragment_home, container, false);


        fabSettings = (FloatingActionButton) rootview.findViewById(R.id.fabSetting);
        layoutFabSave=(LinearLayout)rootview.findViewById(R.id.layoutFabSave);
        FabSave = (FloatingActionButton) rootview.findViewById(R.id.fabSave);
        FabMess = (FloatingActionButton) rootview.findViewById(R.id.fabPhoto);
        FabAcad = (FloatingActionButton) rootview.findViewById(R.id.fabEdit);
        layoutFabEdit = (LinearLayout) rootview.findViewById(R.id.layoutFabEdit);
        layoutFabPhoto = (LinearLayout) rootview.findViewById(R.id.layoutFabPhoto);
        mShadowView=rootview.findViewById(R.id.shadowView);

        fabSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fabExpanded == true){
                    mShadowView.setVisibility(View.GONE);
                    closeSubMenusFab();
                } else {
                    mShadowView.setVisibility(View.VISIBLE);
                    openSubMenusFab();
                }
            }
        });


        closeSubMenusFab();
        return rootview;
    }

    //closes FAB submenus
    private void closeSubMenusFab(){
        layoutFabSave.setVisibility(View.INVISIBLE);
        layoutFabEdit.setVisibility(View.INVISIBLE);
        layoutFabPhoto.setVisibility(View.INVISIBLE);
        fabSettings.setRotation(90);
        fabExpanded = false;

    }

    //Opens FAB submenus
    private void openSubMenusFab(){
        layoutFabSave.setVisibility(View.VISIBLE);
        layoutFabEdit.setVisibility(View.VISIBLE);
        layoutFabPhoto.setVisibility(View.VISIBLE);
        fabSettings.setRotation(45);
        //Change settings icon to 'X' icon
        //fabSettings.setImageResource(R.drawable.common_google_signin_btn_text_light_focused);
        fabExpanded = true;
    }


}