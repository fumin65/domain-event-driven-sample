package dev.fumin.sample.eventdriven.infrastructure.persistence.dao;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;

import dev.fumin.sample.eventdriven.infrastructure.persistence.entity.ConsumedEventDto;

@Dao
public interface ConsumedEventDao {

    @Select
    boolean exists(long id, String receiver);

    @Insert
    int insert(ConsumedEventDto dto);

}
