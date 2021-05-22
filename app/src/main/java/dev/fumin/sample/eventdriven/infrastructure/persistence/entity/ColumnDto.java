package dev.fumin.sample.eventdriven.infrastructure.persistence.entity;

import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.jdbc.entity.NamingType;

import lombok.Getter;
import lombok.Setter;

@Entity(naming = NamingType.SNAKE_LOWER_CASE, listener = BaseDtoListener.class)
@Table(name = "`column`")
@Getter
@Setter
public class ColumnDto extends ConcurrencyDto {

    @Id
    private String id;
    private String projectId;
    private String name;
    private boolean active;

}
