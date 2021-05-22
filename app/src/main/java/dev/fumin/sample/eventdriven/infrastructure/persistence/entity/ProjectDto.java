package dev.fumin.sample.eventdriven.infrastructure.persistence.entity;

import org.seasar.doma.Entity;
import org.seasar.doma.Table;
import org.seasar.doma.jdbc.entity.NamingType;

import lombok.Getter;
import lombok.Setter;

@Entity(naming = NamingType.SNAKE_LOWER_CASE, listener = BaseDtoListener.class)
@Table(name = "project")
@Getter
@Setter
public class ProjectDto extends ConcurrencyDto {

    private String id;
    private String name;
    private boolean active;

}
