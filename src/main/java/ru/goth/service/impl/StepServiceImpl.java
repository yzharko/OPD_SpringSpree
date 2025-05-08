package ru.goth.service.impl;

import ru.goth.domain.dto.StepDto;
import ru.goth.repository.StepRepository;
import ru.goth.service.StepService;

import java.util.List;

public class StepServiceImpl implements StepService {

    private final StepRepository stepRepository;

    public StepServiceImpl(StepRepository stepRepository) {this.stepRepository = stepRepository;}

    @Override
    public StepDto createStep(StepDto stepDto) {
        StepDto newStepDto = new StepDto(stepRepository.createStep(
                stepDto.getId(),
                stepDto.getName(),
                stepDto.getDescription()));
        stepDto.setId(newStepDto.getId());
        stepDto.setName(newStepDto.getName());
        stepDto.setDescription(newStepDto.getDescription());
        return stepDto;
    }

    @Override
    public StepDto getStepById(Long id) { return stepRepository.getStepById(id); }

    @Override
    public List<StepDto> getAllSteps() { return stepRepository.getAllSteps(); }

    @Override
    public StepDto updateStep(Long id, StepDto stepDto) {
        StepDto newStepDto = new StepDto(stepRepository.updateStep(
                id,
                stepDto.getName(),
                stepDto.getDescription()));
        stepDto.setId(newStepDto.getId());
        stepDto.setName(newStepDto.getName());
        stepDto.setDescription(newStepDto.getDescription());
        return stepDto;
    }

    @Override
    public boolean deleteStep(Long id) { return stepRepository.deleteStep(id); }

    @Override
    public Long existStep(String name) { return stepRepository.existStep(name); }
}
