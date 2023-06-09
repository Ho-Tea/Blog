package dev.be.blog.controller;

import dev.be.blog.dto.CategoryDto;
import dev.be.blog.dto.PostDto;
import dev.be.blog.dto.UserDto;
import dev.be.blog.exception.DuplicateNameException;
import dev.be.blog.exception.NotFoundException;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class BlogControllerTest {
    private static final BlogController blogController = new BlogController();


    @BeforeAll
    static void setUp() {
        UserDto userDto = new UserDto("윤주호", "Test", 26, "dbswn990@gmail.com");
        blogController.createBlogByUserName(userDto);
    }

    @Test
    @DisplayName("카테고리 선택시 기존에 있던 카테고리 중 선택해야 한다 - in Controller")
    void chooseCategory() {
        CategoryDto parentCategoryDto = new CategoryDto("Test1");
        PostDto postDto = new PostDto("test-title", "testing");
        assertThatThrownBy(() -> blogController.save(postDto, parentCategoryDto)).isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("카테고리 생성시 중복된 이름의 카테고리를 생성 할 수 없다 - in Controller")
    void createCategory(){
        CategoryDto childCategoryDto = new CategoryDto("Test");
        assertThatThrownBy(() -> blogController.validateName(childCategoryDto.getName())).isInstanceOf(DuplicateNameException.class);
    }

    @Test
    @DisplayName("포스트 생성 시 중복된 이름의 포스트를 생성 할 수 없다 - in Controller")
    void createPost(){
        PostDto postDto = new PostDto("Test", "text");
        assertThatThrownBy(() -> blogController.validateName(postDto.getName())).isInstanceOf(DuplicateNameException.class);
    }



}