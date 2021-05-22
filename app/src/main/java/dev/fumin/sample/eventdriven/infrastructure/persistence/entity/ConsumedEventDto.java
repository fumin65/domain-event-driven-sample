package dev.fumin.sample.eventdriven.infrastructure.persistence.entity;

import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.jdbc.entity.NamingType;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Entity(naming = NamingType.SNAKE_LOWER_CASE)
@Table(name = "consumed_event")
@Getter
@Setter
public class ConsumedEventDto {

    @Id
    long id;

    @Id
    String receiver;

    Timestamp receivedAt;

}
