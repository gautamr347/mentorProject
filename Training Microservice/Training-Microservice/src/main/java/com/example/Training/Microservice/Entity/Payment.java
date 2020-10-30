package com.example.Training.Microservice.Entity;

public class Payment {
    private long trainingid;
    private double amount;
    private String remarks = "none";
    private String txnid = "abcdef1234";


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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTxnid() {
        return txnid;
    }

    public void setTxnid(String txnid) {
        this.txnid = txnid;
    }
}
