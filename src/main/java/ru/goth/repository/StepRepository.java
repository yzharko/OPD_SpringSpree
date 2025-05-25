package ru.goth.repository;

import ru.goth.domain.dto.StepDto;

import java.util.List;

public interface StepRepository {

    StepDto createStep(Long id, String name, String description);

    StepDto getStepById(Long id);

    List<StepDto> getAllSteps();

    StepDto updateStep(Long id, String name, String description);

    boolean deleteStep(Long id);

    Long existStep(String name);
}
