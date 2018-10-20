package com.example.shivansh.seek_a_aid;

import java.util.ArrayList;

public class complaintArray {

    public ArrayList<complainDetails> Cdata = new ArrayList<>();

    public void addcomplaint(complainDetails c) {
        Cdata.add(c);
    }
    ArrayList<complainDetails> getCdata () {
        return Cdata;
    }

}
