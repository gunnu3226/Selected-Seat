package io.nbc.selectedseat.web.domain.category;

import io.nbc.selectedseat.domain.category.dto.GetCategoryResponseDTO;
import io.nbc.selectedseat.domain.category.service.command.CategoryWriter;
import io.nbc.selectedseat.domain.category.service.query.CategoryReader;
import io.nbc.selectedseat.web.common.dto.ResponseDTO;
import io.nbc.selectedseat.web.domain.category.dto.request.CategoryRequestDTO;
import io.nbc.selectedseat.web.domain.category.dto.response.CategoryResponseDTO;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryWriter categoryWriter;
    private final CategoryReader categoryReader;

    @PostMapping
    public ResponseEntity<ResponseDTO<CategoryResponseDTO>> createCategory(
        //TODO : @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody @Valid CategoryRequestDTO requestDTO
    ) {
        //TODO : userDetails.getUser(); admin check

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

    @PutMapping("/{categoryId}")
    public ResponseEntity<ResponseDTO<CategoryResponseDTO>> updateCategory(
        //TODO : @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long categoryId,
        @RequestBody @Valid CategoryRequestDTO requestDTO
    ) {
        //TODO : userDetails.getUser(); admin check

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

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(
        //TODO : @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long categoryId
    ) {
        //TODO : userDetails.getUser(); admin check

        categoryWriter.deleteCategory(categoryId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

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
