package dev.fumin.sample.eventdriven.infrastructure.persistence.entity;

import org.seasar.doma.Entity;
import org.seasar.doma.Version;
import org.seasar.doma.jdbc.entity.NamingType;

import lombok.Getter;
import lombok.Setter;

@Entity(naming = NamingType.SNAKE_LOWER_CASE)
@Getter
@Setter
public abstract class ConcurrencyDto extends BaseDto {

    @Version
    private long version;

}
