package io.nbc.selectedseat.domain.category.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    private Long CategoryId;
    private String name;

    public Category(
        final String name
    ) {
        this.name = name;
    }
}
