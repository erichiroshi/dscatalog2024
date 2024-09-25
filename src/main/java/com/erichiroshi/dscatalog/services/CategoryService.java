package com.erichiroshi.dscatalog.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.erichiroshi.dscatalog.entities.Category;
import com.erichiroshi.dscatalog.entities.dtos.CategoryDTO;
import com.erichiroshi.dscatalog.mappers.CategoryMapper;
import com.erichiroshi.dscatalog.repositories.CategoryRepository;
import com.erichiroshi.dscatalog.services.exceptions.ResourceNotFoundException;

@Service
public class CategoryService {

    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper mapper) {
        this.repository = categoryRepository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAllPaged(Pageable pageable) {
        Page<Category> categories = repository.findAll(pageable);
        return categories.map(mapper::toDto);
    }
    
    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        Category category = find(id);
        return mapper.toDto(category);
    }

    @Transactional
    public CategoryDTO create(CategoryDTO categoryDto) {
        Category category = mapper.toEntity(categoryDto);
        category = repository.save(category);
        return mapper.toDto(category);
    }

    @Transactional
    public CategoryDTO update(Long id, CategoryDTO dto) {
        Category category = find(id);
        category = mapper.partialUpdate(dto, category);
        category = repository.save(category);
        return mapper.toDto(category);
    }

    @Transactional
    public void deleteById(Long id) {
        find(id);
        repository.deleteById(id);
    }

    private Category find(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Category not found. Id: " + id));
    }
}
