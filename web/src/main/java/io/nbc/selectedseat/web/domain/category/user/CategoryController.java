package io.nbc.selectedseat.web.domain.category.user;

import io.nbc.selectedseat.domain.category.dto.GetCategoryResponseDTO;
import io.nbc.selectedseat.domain.category.service.query.CategoryReader;
import io.nbc.selectedseat.web.common.dto.ResponseDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryReader categoryReader;

    @GetMapping
    public ResponseEntity<ResponseDTO<List<GetCategoryResponseDTO>>> getCategories(
    ) {
        return ResponseEntity.ok().body(
            ResponseDTO.<List<GetCategoryResponseDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("카테고리 조회가 완료되었습니다")
                .data(categoryReader.getCategories())
                .build()
        );
    }

}
