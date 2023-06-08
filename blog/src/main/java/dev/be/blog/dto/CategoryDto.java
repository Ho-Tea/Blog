package dev.be.blog.dto;

public class CategoryDto {
    private final String name;

    public CategoryDto(String categoryName) {
        this.name = categoryName;
    }

    public String getName() {
        return this.name;
    }
}
