package io.nbc.selectedseat.domain.category.service.query;

import io.nbc.selectedseat.domain.category.dto.GetCategoryResponseDTO;
import io.nbc.selectedseat.domain.category.repository.CategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryReader {

    private final CategoryRepository categoryRepository;

    @Transactional
    public List<GetCategoryResponseDTO> getCategories() {
        return categoryRepository.getCategories().stream()
            .map(GetCategoryResponseDTO::from)
            .toList();
    }
}
