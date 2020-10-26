package com.example.Training.Microservice.Entity;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class MentorCalendarModel {
    private long mentorid;
    private long trainingid;
    private Date startdate;
    private Date enddate;
    private Time starttime;
    private Time endtime;


    public MentorCalendarModel() {
        Date dateOne = new Date(2323223232L);
        Time time = new Time(123456789999l);
        this.mentorid = 0;
        this.trainingid = 0;
        this.startdate = dateOne;
        this.enddate = dateOne;
        this.starttime = time;
        this.endtime = time;
    }

    public long getMentorid() {
        return mentorid;
    }

    public void setMentorid(long mentorid) {
        this.mentorid = mentorid;
    }

    public long getTrainingid() {
        return trainingid;
    }

    public void setTrainingid(long trainingid) {
        this.trainingid = trainingid;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public Time getStarttime() {
        return starttime;
    }

    public void setStarttime(Time starttime) {
        this.starttime = starttime;
    }

    public Time getEndtime() {
        return endtime;
    }

    public void setEndtime(Time endtime) {
        this.endtime = endtime;
    }

}
