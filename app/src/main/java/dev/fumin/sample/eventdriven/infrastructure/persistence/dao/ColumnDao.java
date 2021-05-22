package dev.fumin.sample.eventdriven.infrastructure.persistence.dao;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;

import java.util.Optional;

import dev.fumin.sample.eventdriven.infrastructure.persistence.entity.ColumnDto;

@Dao
public interface ColumnDao {

    @Insert
    int insert(ColumnDto dto);

    @Update
    int update(ColumnDto dto);

    @Select
    Optional<ColumnDto> selectById(String id, String projectId);

}
