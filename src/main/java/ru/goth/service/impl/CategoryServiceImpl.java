package ru.goth.service.impl;

import ru.goth.domain.dto.CategoryDto;
import ru.goth.repository.impl.CategoryRepositoryImpl;
import ru.goth.service.CategoryService;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepositoryImpl categoryRepository;

    public CategoryServiceImpl(CategoryRepositoryImpl categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        CategoryDto newCategoryDto = new CategoryDto(categoryRepository.createCategory(
                categoryDto.getId(),
                categoryDto.getName(),
                categoryDto.getHazard(),
                categoryDto.getRarity()));
        categoryDto.setId(newCategoryDto.getId());
        categoryDto.setName(newCategoryDto.getName());
        categoryDto.setHazard(newCategoryDto.getHazard());
        categoryDto.setRarity(newCategoryDto.getRarity());
        return categoryDto;
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        return categoryRepository.getCategoryById(id);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.getAllCategories();
    }

    @Override
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        CategoryDto newCategoryDto = new CategoryDto(categoryRepository.updateCategory(
                id,
                categoryDto.getName(),
                categoryDto.getHazard(),
                categoryDto.getRarity()));
        categoryDto.setId(newCategoryDto.getId());
        categoryDto.setName(newCategoryDto.getName());
        categoryDto.setHazard(newCategoryDto.getHazard());
        categoryDto.setRarity(newCategoryDto.getRarity());
        return categoryDto;
    }

    @Override
    public boolean deleteCategory(Long id) {
        return categoryRepository.deleteCategory(id);
    }

    @Override
    public Long existCategory(String name) {
        return categoryRepository.existCategory(name);
    }
}
