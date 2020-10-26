package com.example.SearchMicroservice.Entity;

import javax.persistence.*;

@Entity
@Table(name="mentorskills")
public class MentorSkillsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="mentorid")
    private long mentorid;
    @Column(name="skillid")
    private long skillid;

    @Column(name = "rating")
    private int rating;

    @Column(name="yearsofexperience")
    private int yearsofexperience;

    @Column(name="trainingsdelivered")
    private int trainingdelivered;

    @Column(name="facilitiesoffered")
    private String facilitiesoffered;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMentorid() {
        return mentorid;
    }

    public void setMentorid(long mentorid) {
        this.mentorid = mentorid;
    }

    public long getSkillid() {
        return skillid;
    }

    public void setSkillid(long skillid) {
        this.skillid = skillid;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getYearsofexperience() {
        return yearsofexperience;
    }

    public void setYearsofexperience(int yearsofexperience) {
        this.yearsofexperience = yearsofexperience;
    }

    public int getTrainingdelivered() {
        return trainingdelivered;
    }

    public void setTrainingdelivered(int trainingdelivered) {
        this.trainingdelivered = trainingdelivered;
    }

    public String getFacilitiesoffered() {
        return facilitiesoffered;
    }

    public void setFacilitiesoffered(String facilitiesoffered) {
        this.facilitiesoffered = facilitiesoffered;
    }

    @Override
    public String toString(){
        return "ID="+id+", mentorid="+mentorid+", skill id="+skillid+", rating="+
                rating+", years of experience="+yearsofexperience+
                ", training delivered="+trainingdelivered+
                " facilities offered="+facilitiesoffered;
    }
}
