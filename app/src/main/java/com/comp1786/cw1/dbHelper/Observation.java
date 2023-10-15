package com.comp1786.cw1.dbHelper;

import java.util.Date;

public class Observation {
    private long id;
    private long hike_id;
    private String observation;
    private String comment;
    private Date created_at;
    private Date updated_at;

    public Observation(long id, long hike_id, String observation, String comment, Date created_at, Date updated_at) {
        this.id = id;
        this.hike_id = hike_id;
        this.observation = observation;
        this.comment = comment;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public void setHike_id(long hike_id) {
        this.hike_id = hike_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}
