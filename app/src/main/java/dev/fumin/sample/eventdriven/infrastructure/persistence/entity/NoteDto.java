package dev.fumin.sample.eventdriven.infrastructure.persistence.entity;

import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.jdbc.entity.NamingType;

import lombok.Getter;
import lombok.Setter;

@Entity(naming = NamingType.SNAKE_LOWER_CASE, listener = BaseDtoListener.class)
@Table(name = "note")
@Getter
@Setter
public class NoteDto extends ConcurrencyDto {

    @Id
    private String id;
    private String projectId;
    private String columnId;
    private String description;

}
