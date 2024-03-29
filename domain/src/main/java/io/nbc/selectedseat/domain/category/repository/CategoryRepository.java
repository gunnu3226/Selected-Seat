package io.nbc.selectedseat.domain.category.repository;

import io.nbc.selectedseat.domain.category.model.Category;
import java.util.Optional;

public interface CategoryRepository {

    Category save(final Category category);

    Optional<Category> findByName(final String name);

    Optional<Category> findById(final Long categoryId);

    Category update(final Category category);
}
