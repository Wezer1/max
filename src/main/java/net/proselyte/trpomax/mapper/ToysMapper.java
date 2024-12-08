package net.proselyte.trpomax.mapper;

import net.proselyte.trpomax.dto.ToysDTO;
import net.proselyte.trpomax.entity.Toys;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ToysMapper {

    public abstract Toys toEntity(ToysDTO toysDTO);

    public abstract ToysDTO toDto(Toys toys);
}
