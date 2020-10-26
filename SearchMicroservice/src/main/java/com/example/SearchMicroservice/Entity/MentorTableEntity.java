package com.example.SearchMicroservice.Entity;


import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "mentortablesearch")
public class MentorTableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "mentorname")
    private String mentorname;

    @Column(name = "username")
    private String username;

    @Column(name = "linkedurl")
    private String linkedurl;

    @Column(name = "yearsofexp")
    private int yearsofexperience;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMentorname() {
        return mentorname;
    }

    public void setMentorname(String mentorname) {
        this.mentorname = mentorname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLinkedurl() {
        return linkedurl;
    }

    public void setLinkedurl(String linkedurl) {
        this.linkedurl = linkedurl;
    }

    public int getYearsofexperience() {
        return yearsofexperience;
    }

    public void setYearsofexperience(int yearsofexperience) {
        this.yearsofexperience = yearsofexperience;
    }
}
