package io.nbc.selectedseat.domain.category.service;

import io.nbc.selectedseat.domain.category.exception.CategoryExistException;
import io.nbc.selectedseat.domain.category.model.Category;
import io.nbc.selectedseat.domain.category.repository.CategoryRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Long createCategory(final String name) {
        if (categoryRepository.findByName(name).isPresent()) {
            throw new CategoryExistException("카테고리가 이미 존재합니다");
        }

        Category savedcategory = categoryRepository.save(new Category(name));
        return savedcategory.getCategoryId();
    }

    public Long updateCategory(final Long categoryId, final String name) {
        validateCategory(categoryId);

        Category updatedcategory = categoryRepository.update(new Category(categoryId, name));
        return updatedcategory.getCategoryId();
    }

    public void deleteCategory(final Long categoryId) {
        Category category = validateCategory(categoryId);

        categoryRepository.delete(category);
    }

    private Category validateCategory(final Long categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isEmpty()) {
            throw new CategoryExistException("카테고리가 존재하지 않습니다");
        }
        return category.get();
    }
}
