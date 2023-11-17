package com.comp1786.cw1.object;

import com.comp1786.cw1.constant.ObservationType;

import java.util.Date;

public class Observation {
    private long id;
    private long hikeId;
    private ObservationType type;
    private String name;
    private String date;
    private String time;
    private String comment;
    private String location;
    private Date createdAt;
    private Date updatedAt;

    public Observation() {
    }
    public Observation(long id, long hikeId, ObservationType type, String name, String date, String time, String comment, String location, Date createdAt, Date updatedAt) {
        this.id = id;
        this.hikeId = hikeId;
        this.type = type;
        this.name = name;
        this.date = date;
        this.time = time;
        this.comment = comment;
        this.location = location;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ObservationType getType() {
        return type;
    }

    public void setType(ObservationType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getHikeId() {
        return hikeId;
    }

    public void setHikeId(long hikeId) {
        this.hikeId = hikeId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
