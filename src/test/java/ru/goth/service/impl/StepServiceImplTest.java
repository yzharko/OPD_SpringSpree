package ru.goth.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.goth.domain.dto.StepDto;
import ru.goth.repository.StepRepository;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StepServiceImplTest {

    @Mock
    private StepRepository stepRepository;

    @InjectMocks
    private StepServiceImpl stepService;

    private final Long TEST_ID = 1L;
    private final String TEST_NAME = "Step One";
    private final String TEST_DESCRIPTION = "First step description";

    @Test
    void createStepTest() {
        StepDto inputDto = new StepDto(TEST_NAME, TEST_DESCRIPTION);
        inputDto.setId(TEST_ID);

        StepDto repoResponse = new StepDto(TEST_NAME, TEST_DESCRIPTION);
        repoResponse.setId(TEST_ID);

        when(stepRepository.createStep(anyLong(), anyString(), anyString()))
                .thenReturn(repoResponse);

        StepDto result = stepService.createStep(inputDto);

        assertNotNull(result);
        assertEquals(TEST_ID, result.getId());
        assertEquals(TEST_NAME, result.getName());
        assertEquals(TEST_DESCRIPTION, result.getDescription());

        verify(stepRepository).createStep(TEST_ID, TEST_NAME, TEST_DESCRIPTION);
    }

    @Test
    void getStepByIdTest() {
        StepDto mockDto = new StepDto(TEST_NAME, TEST_DESCRIPTION);
        mockDto.setId(TEST_ID);

        when(stepRepository.getStepById(anyLong())).thenReturn(mockDto);

        StepDto result = stepService.getStepById(TEST_ID);

        assertNotNull(result);
        assertEquals(TEST_ID, result.getId());
        assertEquals(TEST_NAME, result.getName());
        assertEquals(TEST_DESCRIPTION, result.getDescription());

        verify(stepRepository).getStepById(TEST_ID);
    }

    @Test
    void getAllStepsTest() {
        StepDto mockDto = new StepDto(TEST_NAME, TEST_DESCRIPTION);
        mockDto.setId(TEST_ID);

        when(stepRepository.getAllSteps()).thenReturn(Collections.singletonList(mockDto));

        List<StepDto> result = stepService.getAllSteps();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(TEST_ID, result.get(0).getId());
        assertEquals(TEST_NAME, result.get(0).getName());
        assertEquals(TEST_DESCRIPTION, result.get(0).getDescription());

        verify(stepRepository).getAllSteps();
    }

    @Test
    void updateStepTest() {
        StepDto inputDto = new StepDto(TEST_NAME, TEST_DESCRIPTION);
        inputDto.setId(TEST_ID);

        StepDto repoResponse = new StepDto("Updated Step", "Updated description");
        repoResponse.setId(TEST_ID);

        when(stepRepository.updateStep(anyLong(), anyString(), anyString()))
                .thenReturn(repoResponse);

        StepDto result = stepService.updateStep(TEST_ID, inputDto);

        assertNotNull(result);
        assertEquals(TEST_ID, result.getId());
        assertEquals("Updated Step", result.getName());
        assertEquals("Updated description", result.getDescription());

        verify(stepRepository).updateStep(TEST_ID, TEST_NAME, TEST_DESCRIPTION);
    }

    @Test
    void deleteStepTest() {
        when(stepRepository.deleteStep(anyLong())).thenReturn(true);

        boolean result = stepService.deleteStep(TEST_ID);

        assertTrue(result);
        verify(stepRepository).deleteStep(TEST_ID);
    }

    @Test
    void existStepTest() {
        when(stepRepository.existStep(anyString())).thenReturn(TEST_ID);

        Long result = stepService.existStep(TEST_NAME);

        assertEquals(TEST_ID, result);
        verify(stepRepository).existStep(TEST_NAME);
    }
}
