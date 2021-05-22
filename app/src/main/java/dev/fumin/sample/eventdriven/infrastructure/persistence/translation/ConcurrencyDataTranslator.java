package dev.fumin.sample.eventdriven.infrastructure.persistence.translation;

import dev.fumin.sample.eventdriven.domain.common.ConcurrencyEntity;
import dev.fumin.sample.eventdriven.infrastructure.persistence.entity.ConcurrencyDto;

public abstract class ConcurrencyDataTranslator<M extends ConcurrencyEntity, D extends ConcurrencyDto>
        extends BaseTranslator<M, D> {

    @Override
    protected void attachToDto(M model, D dto) {
        super.attachToDto(model, dto);
        dto.setVersion(model.getVersion());
    }

    @Override
    protected void attachToModel(D dto, M model) {
        super.attachToModel(dto, model);
        model.setVersion(dto.getVersion());
    }

}
