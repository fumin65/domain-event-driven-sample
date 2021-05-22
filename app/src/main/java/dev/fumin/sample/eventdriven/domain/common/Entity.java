package dev.fumin.sample.eventdriven.domain.common;

import java.util.Date;

import lombok.Setter;

@Setter
public abstract class Entity {

    private Date createdAt = new Date();
    private Date updatedAt = new Date();

    public Date getCreatedAt() {
        return new Date(createdAt.getTime());
    }

    public Date getUpdatedAt() {
        return new Date(updatedAt.getTime());
    }

}
