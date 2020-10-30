package com.example.skill_microservice.SkillMicroservice.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import org.springframework.validation.annotation.Validated;

import static com.example.skill_microservice.SkillMicroservice.SkillMicroserviceApplication.logger;
import javax.persistence.*;


@Entity
@Table(name="skilldetails")
public class SkillEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="name")
    private String name;
    @Column(name="content")
    private String content;
    @Column(name="totalduration_in_hrs")
    private int totalduration_in_hrs;

    @Column(name="prerequisites")
    private String prerequisites;

    @Column(name = "minimumprice")
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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

    @Override
    public String toString(){
        return "Id="+id+", Skill Name="+name+", content="+content+", totalduration_in_hrs="+totalduration_in_hrs+
                ", prerequisites="+prerequisites;
    }
}
