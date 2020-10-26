package com.example.SearchMicroservice.Entity;

public class SearchResult {
    private String skillname;
  private String trainername;
  private int numberoftrainingdelivered;
  private int rating;
  private String facilitiesoffered;


    public SearchResult() {
        String fortest="fortest";
        int it=0;
        this.skillname = fortest;
        this.trainername = fortest;
        this.numberoftrainingdelivered = it;
        this.rating = it;
        this.facilitiesoffered=fortest;

    }

    public String getFacilitiesoffered() {
        return facilitiesoffered;
    }

    public void setFacilitiesoffered(String facilitiesoffered) {
        this.facilitiesoffered = facilitiesoffered;
    }

    public String getSkillname() {
        return skillname;
    }

    public void setSkillname(String skillname) {
        this.skillname = skillname;
    }

    public String getTrainername() {
        return trainername;
    }

    public void setTrainername(String trainername) {
        this.trainername = trainername;
    }

    public int getNumberoftrainingdelivered() {
        return numberoftrainingdelivered;
    }

    public void setNumberoftrainingdelivered(int numberoftrainingdelivered) {
        this.numberoftrainingdelivered = numberoftrainingdelivered;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
