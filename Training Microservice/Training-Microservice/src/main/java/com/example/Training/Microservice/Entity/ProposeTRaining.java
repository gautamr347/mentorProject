package com.example.Training.Microservice.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Column;
import java.sql.Time;
import java.sql.Date;

public class ProposeTRaining {

    private long userid;
    private long mentorid;
    private long skillid;

    @JsonInclude
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private Date startdate=new Date(2323223232L);

    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern = "HH:mm:ss")
    private Time sessionstarttime=Time.valueOf("16:00:00");

    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern = "HH:mm:ss")
    private Time sessionendtime;

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public long getMentorid() {
        return mentorid;
    }

    public void setMentorid(long mentorid) {
        this.mentorid = mentorid;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Time getSessionstarttime() {
        return sessionstarttime;
    }

    public void setSessionstarttime(Time sessionstarttime) {
        this.sessionstarttime = sessionstarttime;
    }

    public Time getSessionendtime() {
        return sessionendtime;
    }

    public void setSessionendtime(Time sessionendtime) {
        this.sessionendtime = sessionendtime;
    }

    public long getSkillid() {
        return skillid;
    }

    public void setSkillid(long skillid) {
        this.skillid = skillid;
    }
}
