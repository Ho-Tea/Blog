package dev.be.blog.domain;

import dev.be.blog.controller.BlogController;
import dev.be.blog.dto.CategoryDto;
import dev.be.blog.dto.PostDto;
import dev.be.blog.dto.Rename;
import dev.be.blog.dto.UserDto;
import dev.be.blog.exception.DuplicateNameException;
import dev.be.blog.exception.NotFoundException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class CategoryTest {
    private User user;
    private Category parentCategory;
    private Category childCategory;
    private Post post;


    @BeforeEach
    void setUp() {
        User user = User.create("윤주호", "Test", 26, "dbswn990@gmail.com");
        parentCategory = Category.create("Test");
        childCategory = Category.create("Inner-Test");
        post = Post.builder().title("test-title").text("testing").user(user).build();
        parentCategory.add(childCategory);
        parentCategory.findAndAdd(post, childCategory);

    }   // 간단한 폴더구조 생성

    @Test
    @DisplayName("카테고리 선택시 기존에 있던 카테고리 중 선택해야 한다")
    void chooseCategory() {
        Category newCategory = Category.create("NotExistCategory");
        assertThatThrownBy(() -> parentCategory.find(newCategory.getName())).isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("카테고리 생성시 중복된 이름의 카테고리를 생성 할 수 없다")
    void createCategory(){
        Category newCategory = Category.create("Inner-Test");
        assertThatThrownBy(() -> parentCategory.findAndAdd(newCategory, parentCategory)).isInstanceOf(DuplicateNameException.class);
    }

    @Test
    @DisplayName("포스트 생성 시 중복된 이름의 포스트를 생성 할 수 없다")
    void createPost(){
        Post newPost = Post.builder().title("test-title").text("test").user(user).build();
        assertThatThrownBy(() -> parentCategory.findAndAdd(newPost, parentCategory)).isInstanceOf(DuplicateNameException.class);
    }

    @Test
    @DisplayName("카테고리의 이름을 변경할 수 있어야 한다")
    void updateCategory(){
        Rename rename = new Rename("Inner-Test", "New-Inner-Test");
        parentCategory.findAndRename(rename);
        assertThat(childCategory.getName()).isEqualTo(rename.getNewName());
    }

    @Test
    @DisplayName("포스트의 이름을 변경할 수 있어야 한다")
    void updatePost(){
        Rename rename = new Rename("test-title", "New-test-title");
        parentCategory.findAndRename(rename);
        assertThat(post.getName()).isEqualTo(rename.getNewName());
    }

    @Test
    @DisplayName("하위 카테고리를 삭제할 수 있다")
    void deleteCategory(){
        String categoryName = childCategory.getName();
        parentCategory.findAndRemove(categoryName);
        assertThatThrownBy(() -> parentCategory.find(childCategory.getName())).isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("하위 포스트를 삭제할 수 있다")
    void deletePost(){
        String postName = post.getName();
        parentCategory.findAndRemove(postName);
        assertThatThrownBy(() -> parentCategory.find(post.getName())).isInstanceOf(NotFoundException.class);
    }



}