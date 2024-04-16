package io.nbc.selectedseat.web.domain.category.admin;

import io.nbc.selectedseat.domain.category.service.command.CategoryWriter;
import io.nbc.selectedseat.web.common.dto.ResponseDTO;
import io.nbc.selectedseat.web.domain.category.dto.request.CategoryRequestDTO;
import io.nbc.selectedseat.web.domain.category.dto.response.CategoryResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryAdminController {

    private final CategoryWriter categoryWriter;

    @Secured("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity<ResponseDTO<CategoryResponseDTO>> createCategory(
        @RequestBody @Valid CategoryRequestDTO requestDTO
    ) {
        CategoryResponseDTO responseDTO = CategoryResponseDTO.from(
            categoryWriter.createCategory(requestDTO.name())
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(
            ResponseDTO.<CategoryResponseDTO>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("카테고리가 생성되었습니다")
                .data(responseDTO)
                .build()
        );
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/{categoryId}")
    public ResponseEntity<ResponseDTO<CategoryResponseDTO>> updateCategory(
        @PathVariable Long categoryId,
        @RequestBody @Valid CategoryRequestDTO requestDTO
    ) {
        CategoryResponseDTO responseDTO = CategoryResponseDTO.from(
            categoryWriter.updateCategory(categoryId, requestDTO.name())
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(
            ResponseDTO.<CategoryResponseDTO>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("카테고리가 수정되었습니다")
                .data(responseDTO)
                .build()
        );
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(
        @PathVariable Long categoryId
    ) {
        categoryWriter.deleteCategory(categoryId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
