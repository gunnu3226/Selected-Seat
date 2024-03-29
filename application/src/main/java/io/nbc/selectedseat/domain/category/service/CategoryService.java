package io.nbc.selectedseat.domain.category.service;

import io.nbc.selectedseat.domain.category.exception.CategoryExistException;
import io.nbc.selectedseat.domain.category.model.Category;
import io.nbc.selectedseat.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Long createCategory(final String name) {
        if(categoryRepository.findByName(name).isPresent()) {
            throw new CategoryExistException("카테고리가 이미 존재합니다");
        }

        Category savedcategory = categoryRepository.save(new Category(name));
        return savedcategory.getCategoryId();
    }
}
