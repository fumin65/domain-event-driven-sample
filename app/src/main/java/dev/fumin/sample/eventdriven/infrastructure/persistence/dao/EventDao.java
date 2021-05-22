package dev.fumin.sample.eventdriven.infrastructure.persistence.dao;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;

import java.util.Optional;

import dev.fumin.sample.eventdriven.infrastructure.persistence.entity.EventDto;

@Dao
public interface EventDao {

    @Insert
    int insert(EventDto dto);

    @Delete
    int delete(EventDto dto);

    @Select
    Optional<EventDto> selectById(long id);

}
