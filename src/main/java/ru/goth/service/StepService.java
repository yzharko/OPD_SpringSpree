package ru.goth.service;

import ru.goth.domain.dto.StepDto;

import java.util.List;

public interface StepService {

    StepDto createStep(StepDto stepDto);

    StepDto getStepById(Long id);

    List<StepDto> getAllSteps();

    StepDto updateStep(Long id, StepDto stepDto);

    boolean deleteStep(Long id);

    Long existStep(String name);
}
