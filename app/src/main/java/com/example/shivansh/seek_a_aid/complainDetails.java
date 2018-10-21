package com.example.shivansh.seek_a_aid;


import java.util.ArrayList;

public class complainDetails {

    private String id,tag,complain,useremail;
    private ArrayList<String> status;
    private ArrayList<String> statusdesc;
    private int redlike,bluelike,rating,totallikes;

    complainDetails(String id, String Tag, String Complain,String useremail,ArrayList<String> status,ArrayList<String> statusdesc) {
        this.status=status;
        this.id = id;
        this.tag = Tag;
        this.complain = Complain;
        this.useremail=useremail;
        this.statusdesc=statusdesc;
    }


    public String getComplainId() {
        return id;
    }

    public String getTag() {
        return tag;
    }

    public String getComplain() {
        return complain;
    }

    public int RedLikes() {
        return redlike;
    }

    void incredlikes() {
        redlike++;
    }

    public int BlueLikes() {
        return bluelike;
    }

    void incbludelikes() {
        bluelike++;
    }

    void inctotalikes() {totallikes++;}

    public int getTotallikes() {
        return totallikes;
    }
}
