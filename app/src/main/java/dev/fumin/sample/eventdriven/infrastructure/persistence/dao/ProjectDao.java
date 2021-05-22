package dev.fumin.sample.eventdriven.infrastructure.persistence.dao;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;

import java.util.Optional;

import dev.fumin.sample.eventdriven.infrastructure.persistence.entity.ProjectDto;

@Dao
public interface ProjectDao {

    @Insert
    int insert(ProjectDto dto);

    @Update
    int update(ProjectDto dto);

    @Select
    Optional<ProjectDto> selectById(String id);

}
