package io.nbc.selectedseat.domain.category.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.nbc.selectedseat.domain.category.exception.CategoryExistException;
import io.nbc.selectedseat.domain.category.mock.FakeCategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class CategoryServiceTest {

    private CategoryService categoryService;

    private static final String CREATE_CATEGORY_NAME = "아이돌";
    private static final String UPDATE_CATEGORY_NAME = "밴드";
    private static final Long CATEGORY_ID = 1L;
    private static final Integer CATEGORY_SIZE = 1;

    @BeforeEach
    void init() {
        categoryService = new CategoryService(new FakeCategoryRepository());
    }

    @Nested
    class createCategory_Category_추가_테스트 {

        @Test
        void Category_추가_성공() {
            //given-when
            Long categoryId = categoryService.createCategory(CREATE_CATEGORY_NAME);

            //then
            assertThat(categoryId).isEqualTo(CATEGORY_ID);
        }

        @Test
        void Category_이름이_중복될_경우_추가_실패() {
            //given
            categoryService.createCategory(CREATE_CATEGORY_NAME);

            //when-then
            assertThatThrownBy(() -> categoryService.createCategory(CREATE_CATEGORY_NAME))
                .isInstanceOf(CategoryExistException.class)
                .hasMessage("카테고리가 이미 존재합니다");
        }
    }

    @Nested
    class updateCategory_Category_수정_테스트 {

        @Test
        void Category_수정_성공() {
            //given
            Long categoryId = categoryService.createCategory(CREATE_CATEGORY_NAME);

            //when
            Long updateCategoryId = categoryService.updateCategory(categoryId,
                UPDATE_CATEGORY_NAME);

            //then
            assertThat(updateCategoryId).isEqualTo(categoryId);
        }

        @Test
        void Id가_존재하지_않는_경우_수정_실패() {
            //given-when-then
            assertThatThrownBy(
                () -> categoryService.updateCategory(CATEGORY_ID, UPDATE_CATEGORY_NAME))
                .isInstanceOf(CategoryExistException.class)
                .hasMessage("카테고리가 존재하지 않습니다");
        }
    }

    @Nested
    class deleteCategory_Category_삭제_테스트 {

        @Test
        void Category_삭제_성공() {
            //given
            Long categoryId = categoryService.createCategory(CREATE_CATEGORY_NAME);

            //when
            categoryService.deleteCategory(categoryId);

            //then
        }
    }

    @Nested
    class getCategories_Category_전체_조회_테스트 {

        @Test
        void Category_전체_조회_성공() {
            //given
            categoryService.createCategory(CREATE_CATEGORY_NAME);

            //when
            var categories = categoryService.getCategories();

            //then
            assertThat(categories).hasSize(CATEGORY_SIZE);
        }
    }

}
