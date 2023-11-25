package com.cst438.domain;

import java.time.LocalDate;

import javax.persistence.*;

@Entity
@Table(name="actors")
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int actorId;

    private String name;
    private LocalDate dob;
    private String portrait;
    private String about;

    public Actor() {
    	super();
    }
    
    public Actor(String name, LocalDate dob, String portrait, String about) {
	this.name = name;
	this.dob = dob;
	this.portrait = portrait;
	this.about = about;
    }

    public int getActorId() {
        return actorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDOB() {
        return dob;
    }

    public void setDOB(LocalDate dob) {
        this.dob = dob;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    @Override
    public String toString() {
        return "Actor [actorId=" + actorId + ", name=" + name + ", dob=" + dob + ", portrait=" + portrait + ", about=" + about + "]";
    }
}
