package com.example.SearchMicroservice.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name="mentorcalendar")
public class MentorCalendarEntity {
 @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="mentorid")
    private long mentorid;

    @Column(name = "trainingid")
    private long trainingid;

    @Column(name = "startdate")
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private Date startdate;

    @Column(name = "enddate")
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private Date enddate;

    @Column(name = "starttime")
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern = "HH:mm:ss")
    private Time starttime;

    @Column(name = "endtime")
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern = "HH:mm:ss")
    private Time endtime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTrainingid() {
        return trainingid;
    }

    public void setTrainingid(long trainingid) {
        this.trainingid = trainingid;
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


    public MentorCalendarEntity() {
        Date dateOne = new Date(2323223232L);
        Time time = new Time(123456789999l);
        this.mentorid = 0;
        this.trainingid = 0;
        this.startdate = dateOne;
        this.enddate = dateOne;
        this.starttime = time;
        this.endtime = time;
    }
}
