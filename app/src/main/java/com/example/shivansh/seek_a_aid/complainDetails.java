package com.example.shivansh.seek_a_aid;


public class complainDetails {

    String id,tag,complain,redlike,bluelike;
    String version;
    int id_;

    public complainDetails(String id, String Tag, String Complain) {
        this.id = id;
        this.tag = Tag;
        this.complain = Complain;
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

    public String RedLikes() {
        return redlike;
    }

    public String BlueLikes() {
        return bluelike;
    }
}
