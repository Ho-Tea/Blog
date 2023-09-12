package dev.be.blog.dto;

import dev.be.blog.domain.Category;
import dev.be.blog.domain.Content;

import java.util.ArrayList;
import java.util.List;

public class CategoryDto {
    private final String name;
    private List<Content> contents = new ArrayList<>();

    public CategoryDto(String categoryName) {
        this.name = categoryName;
    }

    private CategoryDto(String categoryName, List<Content> contents) {
        this.name = categoryName;
        this.contents = List.copyOf(contents);  //방어적 복사
    }


    public String getName() {
        return this.name;
    }


    public static CategoryDto fromEntity(Category category) {
        return new CategoryDto(category.getName(), category.getChild());
    }

    public static Category toEntity(CategoryDto categoryDto) {
        return Category.create(categoryDto.getName());
    }

    @Override
    public String toString() {
        return '\n' + "CategoryMain : " +
                '\n' +
                '\t' + "name = '" + name + '\'' +
                '\n' +
                '\t' + "contents = " + contents;
    }
}
