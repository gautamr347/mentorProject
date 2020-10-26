package com.example.SearchMicroservice.Entity;

import java.sql.Date;
import java.sql.Time;

public class MentorCalendarModel {
    private long mentorid;
    private long trainingid;
    private Date startdate;
    private Date enddate;
    private Time starttime;
    private Time endtime;

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
