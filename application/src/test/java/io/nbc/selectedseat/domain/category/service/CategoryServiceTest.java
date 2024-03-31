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

    @BeforeEach
    void init() {
        categoryService = new CategoryService(new FakeCategoryRepository());
    }

    @Nested
    class createCategory_Category_추가_테스트 {

        @Test
        void Category_추가_성공() {
            //given
            final String name = "아이돌";

            //when
            Long categoryId = categoryService.createCategory(name);

            //then
            assertThat(categoryId).isEqualTo(1L);
        }

        @Test
        void Category_이름이_중복될_경우_추가_실패() {
            //given
            final String name = "아이돌";
            categoryService.createCategory(name);

            //when-then
            assertThatThrownBy(() -> categoryService.createCategory(name))
                .isInstanceOf(CategoryExistException.class)
                .hasMessage("카테고리가 이미 존재합니다");
        }
    }

    @Nested
    class updateCategory_Category_수정_테스트 {

        @Test
        void Category_수정_성공() {
            //given
            final String name = "아이돌";
            final String updateName = "밴드";
            Long categoryId = categoryService.createCategory(name);

            //when
            Long updateCategoryId = categoryService.updateCategory(categoryId, updateName);

            //then
            assertThat(updateCategoryId).isEqualTo(categoryId);
        }

        @Test
        void Id가_존재하지_않는_경우_수정_실패() {
            //given
            final String updateName = "밴드";

            //when-then
            assertThatThrownBy(() -> categoryService.updateCategory(1L, updateName))
                .isInstanceOf(CategoryExistException.class)
                .hasMessage("카테고리가 존재하지 않습니다");
        }
    }

    @Nested
    class deleteCategory_Category_삭제_테스트 {

        @Test
        void Category_삭제_성공() {
            //given
            final String name = "아이돌";
            Long categoryId = categoryService.createCategory(name);

            //when
            categoryService.deleteCategory(categoryId);

            //then
        }
    }

}
