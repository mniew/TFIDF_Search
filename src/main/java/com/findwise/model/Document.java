package com.findwise.model;


import com.findwise.IndexEntry;
import java.util.List;

public class Document implements IndexEntry {
    private String Id;
    private List<String> description;
    private double score;

    public Document (String Id, List<String> description){
        this.Id = Id;
        this.description = description;
}

    @Override
    public void setId(String id) {
        this.Id = id;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }

    @Override
    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String getId() {
        return Id;
    }

    public List<String> getDescription() {
        return description;
    }

    @Override
    public double getScore() {
        return score;
    }
}
