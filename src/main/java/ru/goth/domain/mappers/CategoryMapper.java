package ru.goth.domain.mappers;

import org.mapstruct.Mapper;
import ru.goth.domain.dto.CategoryDto;
import ru.goth.domain.entities.Category;

@Mapper
public interface CategoryMapper {

    CategoryDto toCategoryDto(Category category);

    Category toCategory(CategoryDto categoryDto);
}
