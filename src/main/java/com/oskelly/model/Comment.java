package com.oskelly.model;

import java.sql.Timestamp;
import java.util.UUID;

public class Comment {
    private UUID id;
    private String comment;
    private Timestamp time;

    public Comment() {
    }

    public Comment(UUID id, String comment, Timestamp time) {
        this.id = id;
        this.comment = comment;
        this.time = time;
    }

    public Comment(UUID id, String comment) {
        this.id = id;
        this.comment = comment;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
