package io.nbc.selectedseat.db.core.domain.category.repository;

import io.nbc.selectedseat.db.core.domain.category.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryJpaRepository extends JpaRepository<CategoryEntity, Long> {

}
