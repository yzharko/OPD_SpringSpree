package ru.goth.domain.mappers;

import org.mapstruct.Mapper;
import ru.goth.domain.dto.StepDto;
import ru.goth.domain.entities.Step;

@Mapper
public interface StepMapper {

    StepDto toStepDto(Step step);

    Step toStep(StepDto stepDto);
}
