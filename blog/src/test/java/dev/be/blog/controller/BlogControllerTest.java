package dev.be.blog.controller;

import dev.be.blog.domain.ContentType;
import dev.be.blog.dto.CategoryDto;
import dev.be.blog.dto.ContentDto;
import dev.be.blog.dto.PostDto;
import dev.be.blog.dto.UserDto;
import dev.be.blog.exception.DuplicateNameException;
import dev.be.blog.exception.NotFoundException;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class BlogControllerTest {
    static final BlogController blogController = new BlogController();


    @BeforeEach
    void setUp() {
        UserDto userDto = new UserDto("윤주호", "Test", 26, "dbswn990@gmail.com");
        blogController.createBlogByUserName(userDto);
    }

    @Test
    @DisplayName("카테고리 선택시 기존에 있던 카테고리 중 선택해야 한다")
    void chooseCategory() {
        CategoryDto parentCategoryDto = new CategoryDto("Test1");
        PostDto postDto = new PostDto("test-title", "testing");
        assertThatThrownBy(() -> blogController.save(postDto, parentCategoryDto)).isInstanceOf(NotFoundException.class);
    }

    

}