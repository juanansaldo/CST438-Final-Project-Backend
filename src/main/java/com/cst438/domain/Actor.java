package com.cst438.domain;

import javax.persistence.*;

@Entity
@Table(name="actors")
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int actorId;

    private String name;
    private int age;
    private String portrait;
    private String about;

    public Actor() {
    	super();
    }
    
    public Actor(String name, int age, String portrait, String about) {
	this.name = name;
	this.age = age;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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
        return "Actor [actorId=" + actorId + ", name=" + name + ", age=" + age + ", portrait=" + portrait + ", about=" + about + "]";
    }
}
