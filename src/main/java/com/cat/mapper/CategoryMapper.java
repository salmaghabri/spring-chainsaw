package com.cat.mapper;

import com.cat.model.dao.Category;
import com.cat.model.dto.category.CategoryDTO;
import com.cat.model.dto.category.CreateCategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {
    CategoryMapper INSTANCE= Mappers.getMapper(CategoryMapper.class);

    CreateCategoryDTO categoryToCreateDTO(Category category);
    Category createDTOToCategory(CreateCategoryDTO categoryDTO);
    @Mapping(target = "parentCategory.name", source = "parentCategoryName")
    Category DTOToCategory(CategoryDTO categoryDTO);
    @Mapping(target = "parentCategoryName", source = "parentCategory.name")
    CategoryDTO categoryToDTO(Category category);
}
