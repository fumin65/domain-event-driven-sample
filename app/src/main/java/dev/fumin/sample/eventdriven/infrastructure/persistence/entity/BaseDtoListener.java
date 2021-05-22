package dev.fumin.sample.eventdriven.infrastructure.persistence.entity;

import org.seasar.doma.jdbc.entity.EntityListener;
import org.seasar.doma.jdbc.entity.PreInsertContext;
import org.seasar.doma.jdbc.entity.PreUpdateContext;

import java.sql.Timestamp;

public class BaseDtoListener<T extends BaseDto> implements EntityListener<T> {

    @Override
    public void preInsert(T t, PreInsertContext<T> context) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        t.setCreatedAt(now);
        t.setUpdatedAt(now);
    }

    @Override
    public void preUpdate(T t, PreUpdateContext<T> context) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        t.setUpdatedAt(now);
    }

}
