package io.nbc.selectedseat.domain.category.service;

import io.nbc.selectedseat.domain.category.model.Category;
import io.nbc.selectedseat.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Long createCategory(final String name) {
        Category savedcategory = categoryRepository.save(new Category(name));
        return savedcategory.getCategoryId();
    }
}
