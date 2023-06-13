package dev.be.blog.vo;

import dev.be.blog.constant.ContentType;
import dev.be.blog.dto.CategoryDto;

public class ContentVo {
    private ContentType contentType;
    private CategoryDto parentCategory;

    public ContentVo(ContentType contentType, CategoryDto parentCategory) {
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
