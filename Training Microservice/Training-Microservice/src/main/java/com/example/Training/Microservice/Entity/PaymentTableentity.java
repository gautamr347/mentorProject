package com.example.Training.Microservice.Entity;


import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "paymenttable")
public class PaymentTableentity {
    public PaymentTableentity() {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        this.trainingid = 0;
        this.amount = 0;
        this.transnid = "none";
        this.timestamp = ts;
        this.remark = "none";
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "trainingid")
    private long trainingid;

    @Column(name = "amount")
    private double amount;

    @Column(name = "transactionid")
    private String transnid;

    @Column(name = "timestamp")
    private Timestamp timestamp;

    @Column(name = "remarks")
    private String remark = "no any";

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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTransnid() {
        return transnid;
    }

    public void setTransnid(String transnid) {
        this.transnid = transnid;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
