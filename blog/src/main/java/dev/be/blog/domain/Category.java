package dev.be.blog.domain;

import java.util.List;
import java.util.Optional;

public interface Category {
    void add(Category category);
    void remove(Category category);
    void rename(String name);
    String getName();

}
