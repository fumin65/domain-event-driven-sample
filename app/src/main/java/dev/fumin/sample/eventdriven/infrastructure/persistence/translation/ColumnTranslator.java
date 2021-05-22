package dev.fumin.sample.eventdriven.infrastructure.persistence.translation;

import dev.fumin.sample.eventdriven.domain.model.cloumn.Column;
import dev.fumin.sample.eventdriven.domain.model.cloumn.ColumnId;
import dev.fumin.sample.eventdriven.domain.model.project.ProjectId;
import dev.fumin.sample.eventdriven.infrastructure.persistence.entity.ColumnDto;

public class ColumnTranslator extends ConcurrencyDataTranslator<Column, ColumnDto> {

    @Override
    public Column toModel(ColumnDto dto) {
        Column column = new Column(
                new ColumnId(dto.getId()),
                new ProjectId(dto.getProjectId()),
                dto.getName(),
                dto.isActive()
        );
        attachToModel(dto, column);
        return column;
    }

    @Override
    public ColumnDto toDto(Column column) {
        ColumnDto dto = new ColumnDto();
        dto.setId(column.getId().getValue());
        dto.setProjectId(column.getProjectId().getValue());
        dto.setName(column.getName());
        dto.setActive(column.isActive());
        attachToDto(column, dto);
        return dto;
    }

}
