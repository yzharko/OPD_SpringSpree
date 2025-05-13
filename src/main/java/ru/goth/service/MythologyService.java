package ru.goth.service;

import ru.goth.domain.dto.MythologyDto;

import java.util.List;

public interface MythologyService {

    MythologyDto createMythology(MythologyDto mythologyDto);

    MythologyDto getMythologyById(Long id);

    List<MythologyDto> getAllMythologies();

    MythologyDto updateMythology(Long id, MythologyDto mythologyDto);

    boolean deleteMythology(Long id);

    Long existMythology(String name);
}
