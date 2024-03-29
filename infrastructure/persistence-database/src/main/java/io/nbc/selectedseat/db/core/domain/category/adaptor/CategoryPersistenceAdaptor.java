package io.nbc.selectedseat.db.core.domain.category.adaptor;

import io.nbc.selectedseat.db.core.domain.category.entity.CategoryEntity;
import io.nbc.selectedseat.db.core.domain.category.repository.CategoryJpaRepository;
import io.nbc.selectedseat.domain.category.model.Category;
import io.nbc.selectedseat.domain.category.repository.CategoryRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CategoryPersistenceAdaptor implements CategoryRepository {

    private final CategoryJpaRepository categoryJpaRepository;

    @Override
    public Category save(final Category category) {
        return categoryJpaRepository.save(new CategoryEntity(category)).toModel();
    }

    @Override
    public Optional<Category> findByName(final String name) {
        CategoryEntity categoryEntity = categoryJpaRepository.findByName(name);
        if(categoryEntity == null) {
            return Optional.empty();
        }

        return Optional.ofNullable(categoryEntity.toModel());
    }
}
