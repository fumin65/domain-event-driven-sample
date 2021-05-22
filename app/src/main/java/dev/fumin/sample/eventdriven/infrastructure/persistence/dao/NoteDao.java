package dev.fumin.sample.eventdriven.infrastructure.persistence.dao;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;

import java.util.Optional;

import dev.fumin.sample.eventdriven.infrastructure.persistence.entity.NoteDto;

@Dao
public interface NoteDao {

    @Insert
    int insert(NoteDto dto);

    @Update
    int update(NoteDto dto);

    @Select
    Optional<NoteDto> selectById(String id, String projectId);

}
