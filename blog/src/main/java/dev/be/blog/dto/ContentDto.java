package dev.be.blog.dto;

import dev.be.blog.constant.ContentType;
import dev.be.blog.dto.CategoryDto;

public class ContentDto {
    private ContentType contentType;
    private CategoryDto parentCategory;

    public ContentDto(ContentType contentType, CategoryDto parentCategory) {
        this.contentType = contentType;
        this.parentCategory = parentCategory;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public CategoryDto getCategoryDto() {
        return parentCategory;
    }
}
