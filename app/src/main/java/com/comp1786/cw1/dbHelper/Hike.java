package com.comp1786.cw1.dbHelper;

import com.comp1786.cw1.constant.Difficulty;
import com.comp1786.cw1.constant.TrailType;

import java.util.Date;

public class Hike {
    private long id;
    private String hikeName;
    private String location;
    private String date;
    private boolean parking;
    private long length;
    private Difficulty difficulty;
    private String description;
    private TrailType type;
    private String contact;
    private Date createdAt;
    private Date updatedAt;

    public Hike() {
    }

    public Hike(long id, String hikeName, String location, String date, boolean parking, long length, Difficulty difficulty, String description, TrailType type, String contact, Date createdAt, Date updatedAt) {
        this.id = id;
        this.hikeName = hikeName;
        this.location = location;
        this.date = date;
        this.parking = parking;
        this.length = length;
        this.difficulty = difficulty;
        this.description = description;
        this.type = type;
        this.contact = contact;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHikeName() {
        return hikeName;
    }

    public void setHikeName(String hikeName) {
        this.hikeName = hikeName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isParking() {
        return parking;
    }

    public void setParking(boolean parking) {
        this.parking = parking;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TrailType getType() {
        return type;
    }

    public void setType(TrailType type) {
        this.type = type;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
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
