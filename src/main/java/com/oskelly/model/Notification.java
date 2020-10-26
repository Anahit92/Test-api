package com.oskelly.model;

import java.sql.Timestamp;
import java.util.UUID;

public class Notification {
    private UUID id;
    private UUID comment_id;
    private Timestamp time;
    private Boolean delivered;

    public Notification() {
    }

    public Notification(UUID id, UUID comment_id, Timestamp time, Boolean delivered) {
        this.id = id;
        this.comment_id = comment_id;
        this.time = time;
        this.delivered = delivered;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getComment_id() {
        return comment_id;
    }

    public void setComment_id(UUID comment_id) {
        this.comment_id = comment_id;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Boolean getDelivered() {
        return delivered;
    }

    public void setDelivered(Boolean delivered) {
        this.delivered = delivered;
    }
}
