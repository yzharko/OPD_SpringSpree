package ru.goth.repository;

import ru.goth.domain.dto.MythologyDto;

import java.util.List;

public interface MythologyRepository {

    MythologyDto createMythology(Long id, String name);

    MythologyDto getMythologyById(Long id);

    List<MythologyDto> getAllMythologies();

    MythologyDto updateMythology(Long id, String name);

    boolean deleteMythology(Long id);

    Long existMythology(String name);
}
