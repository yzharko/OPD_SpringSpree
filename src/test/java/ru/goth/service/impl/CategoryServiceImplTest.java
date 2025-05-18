package ru.goth.service.impl;

import ru.goth.domain.dto.CategoryDto;
import ru.goth.repository.CategoryRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.goth.repository.impl.CategoryRepositoryImpl;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepositoryImpl categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private static final Long TEST_ID = 1L;
    private static final String TEST_NAME = "Potion";
    private static final String TEST_HAZARD = "Low";
    private static final  String TEST_RARITY = "Common";

    @Test
    void createCategoryTest() {
        CategoryDto inputCategoryDto = new CategoryDto(
                TEST_NAME,
                TEST_HAZARD,
                TEST_RARITY);
        inputCategoryDto.setId(TEST_ID);

        CategoryDto outputRepositoryDto = new CategoryDto(
                TEST_NAME,
                TEST_HAZARD,
                TEST_RARITY);
        outputRepositoryDto.setId(TEST_ID);

        when(categoryRepository.createCategory(
                anyLong(),
                anyString(),
                anyString(),
                anyString())).thenReturn(outputRepositoryDto);

        CategoryDto result = categoryService.createCategory(inputCategoryDto);

        assertNotNull(result);
        assertEquals(TEST_ID, result.getId());
        assertEquals(TEST_NAME, result.getName());
        assertEquals(TEST_HAZARD, result.getHazard());
        assertEquals(TEST_RARITY, result.getRarity());

        verify(categoryRepository).createCategory(
                TEST_ID,
                TEST_NAME,
                TEST_HAZARD,
                TEST_RARITY);
    }

    @Test
    void getCategoryByIdTest() {
        CategoryDto inputCategoryDto = new CategoryDto(
                TEST_NAME,
                TEST_HAZARD,
                TEST_RARITY);
        inputCategoryDto.setId(TEST_ID);

        when(categoryRepository.getCategoryById(TEST_ID)).thenReturn(inputCategoryDto);

        CategoryDto outputRepositoryDto = categoryService.getCategoryById(TEST_ID);

        assertNotNull(outputRepositoryDto);
        assertEquals(TEST_ID, outputRepositoryDto.getId());
        verify(categoryRepository).getCategoryById(TEST_ID);
    }

    @Test
    void getAllCategoriesTest() {
        CategoryDto inputCategoryDto = new CategoryDto(
                TEST_NAME,
                TEST_HAZARD,
                TEST_RARITY);
        inputCategoryDto.setId(TEST_ID);

        when(categoryRepository.getAllCategories()).thenReturn(Collections.singletonList(inputCategoryDto));

        List<CategoryDto> outputRepositoryDtos = categoryService.getAllCategories();

        assertNotNull(outputRepositoryDtos);
        assertEquals(1, outputRepositoryDtos.size());
        assertEquals(TEST_ID, outputRepositoryDtos.get(0).getId());
        verify(categoryRepository).getAllCategories();
    }

    @Test
    void updateCategoryTest() {
        CategoryDto inputCategoryDto = new CategoryDto(TEST_NAME,
                TEST_HAZARD,
                TEST_RARITY);
        inputCategoryDto.setId(TEST_ID);

        CategoryDto outputRepositoryDto = new CategoryDto(
                "Updated_Name",
                "Updated_Hazard",
                "Updated_Rarity");
        outputRepositoryDto.setId(TEST_ID);

        when(categoryRepository.updateCategory(
                anyLong(),
                anyString(),
                anyString(),
                anyString())).thenReturn(outputRepositoryDto);

        CategoryDto result = categoryService.updateCategory(TEST_ID, inputCategoryDto);

        assertNotNull(result);
        assertEquals(TEST_ID, result.getId());
        assertEquals("Updated_Name", result.getName());
        assertEquals("Updated_Hazard", result.getHazard());
        assertEquals("Updated_Rarity", result.getRarity());

        verify(categoryRepository).updateCategory(
                TEST_ID,
                TEST_NAME,
                TEST_HAZARD,
                TEST_RARITY);
    }

    @Test
    void deleteCategoryTest() {
        when(categoryRepository.deleteCategory(anyLong())).thenReturn(true);

        boolean result = categoryService.deleteCategory(TEST_ID);

        assertTrue(result);
        verify(categoryRepository).deleteCategory(TEST_ID);
    }

    @Test
    void existsCategoryTest() {
        when(categoryRepository.existCategory(anyString())).thenReturn(TEST_ID);

        Long result = categoryService.existCategory(TEST_NAME);

        assertEquals(TEST_ID, result);
        verify(categoryRepository).existCategory(TEST_NAME);
    }
}
