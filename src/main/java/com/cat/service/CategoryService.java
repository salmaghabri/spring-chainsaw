package com.cat.service;

import com.cat.model.dao.Category;
import com.cat.model.dto.category.CategoryDTO;
import com.cat.model.dto.category.CreateCategoryDTO;
import com.cat.repository.CategoryRepository;
import com.cat.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper = Mappers.getMapper(CategoryMapper.class);


    public Page<Category> getAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    public CategoryDTO getCategoryById(UUID id) {

        return this.categoryMapper.categoryToDTO(categoryRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found with ID: " + id)));
    }

    public CreateCategoryDTO createCategory(CreateCategoryDTO categoryDTO) {
        Category category = this.categoryMapper.createDTOToCategory(categoryDTO);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.categoryToCreateDTO(savedCategory);
    }

    public CategoryDTO updateCategory(UUID id, CategoryDTO categoryDetails) {
        Category category = this.categoryRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category not found with ID: " + id));
        category.setParentCategory(this.categoryRepository.findByName(categoryDetails.getParentCategoryName()));
        Category updatedCategory = categoryRepository.save(category);

        return categoryMapper.categoryToDTO(updatedCategory);
    }

//    private String[] getNullPropertyNames(Object source) {
//        final BeanWrapper srcWrapper = new BeanWrapperImpl(source);
//        Set<String> emptyNames = new HashSet<>();
//        for (PropertyDescriptor propertyDescriptor : srcWrapper.getPropertyDescriptors()) {
//            Object srcValue = srcWrapper.getPropertyValue(propertyDescriptor.getName());
//            if (srcValue == null) {
//                emptyNames.add(propertyDescriptor.getName());
//            }
//        }
//        return emptyNames.toArray(new String[0]);
//    }

    public void deleteCategoryById(UUID id) {
        Category category = this.categoryRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found with ID: " + id));
        categoryRepository.delete(category);
    }
}