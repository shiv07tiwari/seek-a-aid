package com.example.shivansh.seek_a_aid;


import java.util.ArrayList;

public class complainDetails {

    private String id,tag,complain,useremail,rating,member1,member2;
    private ArrayList<String> status;
    private ArrayList<String> statusdesc;
    private int redlike,bluelike,totallikes;

    complainDetails(String id, String Tag, String Complain,String useremail,ArrayList<String> status,ArrayList<String> statusdesc,String rating,String member1,String member2) {
        this.status=status;
        this.id = id;
        this.tag = Tag;
        this.complain = Complain;
        this.useremail=useremail;
        this.statusdesc=statusdesc;
        this.rating=rating;
        this.member1=member1;
        this.member2=member2;
    }
public String getMember1() {return member1;}
public String getMember2() {return member2;}

    public String getComplainId() {
        return id;
    }

    public String getRating() {return rating;}

    public String getTag() {
        return tag;
    }

    public String getComplain() {
        return complain;
    }

    public String getUseremail() {return useremail;}

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
