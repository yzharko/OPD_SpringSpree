package ru.goth.domain.mappers;

import org.mapstruct.Mapper;
import ru.goth.domain.dto.MythologyDto;
import ru.goth.domain.entities.Mythology;

@Mapper
public interface MythologyMapper {

    MythologyDto toMythologyDto(Mythology mythology);

    Mythology toMythology(MythologyDto mythologyDto);
}
