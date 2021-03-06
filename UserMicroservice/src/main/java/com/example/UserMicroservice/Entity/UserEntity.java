package com.example.UserMicroservice.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;

@Entity
@Table(name = "userentity")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private long id;

    @NotEmpty(message = "first name is required")
    @Column(name = "firstname")
    private String firstname;

    @NotEmpty(message = "Last name is required")
    @Column(name = "lastname")
    private String lastname;

    @NotEmpty(message = "username is required")
    @Column(name = "username")
    private String username;

    @NotEmpty(message = "password is required")
    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Column(name = "contactnumber")
    private int contactnumber;

    @Column(name = "regdatetime")
    @JsonIgnore
    private Timestamp timestamp;

    @Column(name = "role")
    private String role;

    @JsonIgnore
    @Column(name = "regcode")
    private int regcode = 0000;

    @Column(name = "status")
    private String status = "active";

    @Column(name = "token")
    @JsonIgnore
    private String token;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getContactnumber() {
        return contactnumber;
    }

    public void setContactnumber(int contactnumber) {
        this.contactnumber = contactnumber;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getRegcode() {
        return regcode;
    }

    public void setRegcode(int regcode) {
        this.regcode = regcode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
