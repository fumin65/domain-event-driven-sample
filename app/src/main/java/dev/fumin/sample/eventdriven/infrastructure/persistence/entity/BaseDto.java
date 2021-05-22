package dev.fumin.sample.eventdriven.infrastructure.persistence.entity;

import org.seasar.doma.Entity;
import org.seasar.doma.jdbc.entity.NamingType;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Entity(naming = NamingType.SNAKE_LOWER_CASE)
@Getter
@Setter
public abstract class BaseDto {

    private Timestamp createdAt;

    private Timestamp updatedAt;

}
