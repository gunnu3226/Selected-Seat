package io.nbc.selectedseat.domain.category.repository;

import io.nbc.selectedseat.domain.category.model.Category;
import java.util.Optional;

public interface CategoryRepository {

    Category save(Category category);

    Optional<Category> findByName(String name);
}
