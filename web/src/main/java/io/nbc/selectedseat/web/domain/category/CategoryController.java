package io.nbc.selectedseat.web.domain.category;

import io.nbc.selectedseat.domain.category.service.CategoryService;
import io.nbc.selectedseat.web.common.dto.ResponseDTO;
import io.nbc.selectedseat.web.domain.category.dto.request.CategoryRequestDTO;
import io.nbc.selectedseat.web.domain.category.dto.response.CategoryResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/categorys")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ResponseDTO<CategoryResponseDTO>> createCategory(
        //TODO : @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody @Valid CategoryRequestDTO requestDTO
    ) {
        //TODO : userDetails.getUser(); admin check

        CategoryResponseDTO responseDTO = CategoryResponseDTO.from(
            categoryService.createCategory(requestDTO.name())
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(
            ResponseDTO.<CategoryResponseDTO>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("카테고리가 생성되었습니다")
                .data(responseDTO)
                .build()
        );
    }

    //TODO : update, delete, get, list
    @PutMapping("/{categoryId}")
    public ResponseEntity<ResponseDTO<CategoryResponseDTO>> updateCategory(
        //TODO : @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long categoryId,
        @RequestBody @Valid CategoryRequestDTO requestDTO
    ) {
        //TODO : userDetails.getUser(); admin check

        CategoryResponseDTO responseDTO = CategoryResponseDTO.from(
            categoryService.updateCategory(categoryId, requestDTO.name())
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(
            ResponseDTO.<CategoryResponseDTO>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("카테고리가 수정되었습니다")
                .data(responseDTO)
                .build()
        );
    }

}
