package com.example.Training.Microservice.Entity;

public class SkillModel {
    private long id;
    private String name;
    private String content;
    private int totalduration_in_hrs;
    private String prerequisites;
    private int price;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getTotalduration_in_hrs() {
        return totalduration_in_hrs;
    }

    public void setTotalduration_in_hrs(int totalduration_in_hrs) {
        this.totalduration_in_hrs = totalduration_in_hrs;
    }

    public String getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(String prerequisites) {
        this.prerequisites = prerequisites;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
