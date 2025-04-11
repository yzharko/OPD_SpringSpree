package ru.goth.service.impl;

import ru.goth.domain.dto.MythologyDto;
import ru.goth.repository.impl.MythologyRepositoryImpl;
import ru.goth.service.MythologyService;

import java.util.List;

public class MythologyServiceImpl implements MythologyService {

    private final MythologyRepositoryImpl mythologyRepository;

    public MythologyServiceImpl(MythologyRepositoryImpl mythologyRepository) {
        this.mythologyRepository = mythologyRepository;
    }

    @Override
    public MythologyDto createMythology(MythologyDto mythologyDto) {
        MythologyDto newMythologyDto = new MythologyDto(mythologyRepository.createMythology(
                mythologyDto.getId(),
                mythologyDto.getName()));
        mythologyDto.setId(newMythologyDto.getId());
        mythologyDto.setName(newMythologyDto.getName());
        return mythologyDto;
    }

    @Override
    public MythologyDto getMythologyById(Long id) {
        return mythologyRepository.getMythologyById(id);
    }

    @Override
    public List<MythologyDto> getAllMythologies() {
        return mythologyRepository.getAllMythologies();
    }

    @Override
    public MythologyDto updateMythology(Long id, MythologyDto mythologyDto) {
        MythologyDto newMythologyDto = new MythologyDto(mythologyRepository.updateMythology(
                id,
                mythologyDto.getName()));
        mythologyDto.setId(newMythologyDto.getId());
        mythologyDto.setName(newMythologyDto.getName());
        return mythologyDto;
    }

    @Override
    public boolean deleteMythology(Long id) {
        return mythologyRepository.deleteMythology(id);
    }

    @Override
    public Long existMythology(String name) {
        return mythologyRepository.existMythology(name);
    }
}
