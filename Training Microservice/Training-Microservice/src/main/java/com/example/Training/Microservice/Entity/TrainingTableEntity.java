package com.example.Training.Microservice.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name="trainingtable")
public class TrainingTableEntity {

   public TrainingTableEntity() {
        Date dateOne = new Date(2323223232L);
       Time time = new Time(123456789999l);
        this.userid = 1;
        this.mentorid = 1;
        this.skillid = 1;
        this.status ="active";
        this.progress = "proposed";
        this.ratingbystudent = -1;
        this.startdate =dateOne ;
        this.enddate = dateOne;
        this.sessionstarttime =time ;
        this.sessionendtime = time;
        this.amountreceived = 0;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="user_id")
    private long userid;

    @Column(name="mentor_id")
    private long mentorid;

    @Column(name="skill_id")
    private long skillid;

    @Column(name="status")
    private String status="active";

    @Column(name="progress")
    private String progress="proposed";

    @Column(name="ratingbystudent")
    private int ratingbystudent;

    @Column(name="startdate")
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private Date startdate;

    @Column(name="enddate")
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private Date enddate;

    @Column(name="sessionstarttime")
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern = "HH:mm:ss")
    private Time sessionstarttime;

    @Column(name="sessionendtime")
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern = "HH:mm:ss")
    private Time sessionendtime;

    @Column(name="amountreceived")
    private double amountreceived=0;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public long getSkillid() {
        return skillid;
    }

    public void setSkillid(long skillid) {
        this.skillid = skillid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public int getRatingbystudent() {
        return ratingbystudent;
    }

    public void setRatingbystudent(int ratingbystudent) {
        this.ratingbystudent = ratingbystudent;
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

    public double getAmountreceived() {
        return amountreceived;
    }

    public void setAmountreceived(double amountreceived) {
        this.amountreceived = amountreceived;
    }

    @Override
    public String toString(){
        return "trainingTableEntity [id="+id+", userId="+userid+", MentorID="+mentorid+
                ", Skill Id="+skillid+", Status="+status+", Progress="+progress+", Rating="+ratingbystudent+", StarrDate="+startdate+", EndDate="+enddate
                +", SessionStartTime="+sessionstarttime+", SessionDurationInHr="+sessionendtime+", AmountReceived="+amountreceived;
    }
}
