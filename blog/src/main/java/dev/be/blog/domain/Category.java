package dev.be.blog.domain;

public interface Category {
    void printChild();
    void add(Category category);
    void remove(Category category);
    void printCategory();

}
