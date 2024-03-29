package io.nbc.selectedseat.db.core.domain.category.entity;

import io.nbc.selectedseat.db.core.domain.common.BaseEntity;
import io.nbc.selectedseat.domain.category.model.Category;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "categorys")
@SQLRestriction(value = "deleted_at is NULL")
@SQLDelete(sql = "update categorys set deleted_at = NOW() where category_id = ?")
public class CategoryEntity extends BaseEntity {

    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(nullable = false)
    private String name;

    public CategoryEntity(final String name) {
        this.name = name;
    }
    public CategoryEntity(final Category category) {
        this.categoryId = category.getCategoryId();
        this.name = category.getName();
    }

    public Category toModel() {
        return new Category(
            categoryId,
            name
        );
    }

}
