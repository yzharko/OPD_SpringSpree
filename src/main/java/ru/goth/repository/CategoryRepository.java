package ru.goth.repository;

import ru.goth.domain.dto.CategoryDto;

import java.util.List;

public interface CategoryRepository {

    CategoryDto createCategory(Long id, String name, String hazard, String rarity);

    CategoryDto getCategoryById(Long id);

    List<CategoryDto> getAllCategories();

    CategoryDto updateCategory(Long id, String name, String hazard, String rarity);

    boolean deleteCategory(Long id);

    Long existCategory(String name);
}
