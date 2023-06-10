package dev.be.blog.domain;

import dev.be.blog.dto.Rename;
import dev.be.blog.exception.DuplicateNameException;
import dev.be.blog.exception.NotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Category implements Content {
    private String name;
    private List<Content> contents = new ArrayList<>();

    @Override
    public void rename(String newName) {
        this.name = newName;
    }

    @Override
    public String getName() {
        return this.name;
    }

    private Category(String name) {
        this.name = name;
    }

    public static Category create(String name) {
        return new Category(name);
    }


    public List<Content> getChild() {
        return Collections.unmodifiableList(contents);
    }

    public Content find(String name) {
        for (Content content : contents) {
            if (content.getName().equals(name)) {
                return content;
            } else if (content.getClass().equals(Category.class)) {
                return ((Category) content).find(name);
            }
        }
        throw new NotFoundException();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, contents);
    }

    public void findAndAdd(Post post, Category category) {
        if(category.equals(this)){
            throw new DuplicateNameException();
        }
        Category found = (Category) find(category.getName());
        found.add(post);
    }

    public void findAndAdd(Category childCategory, Category parentCategory) {
        if(parentCategory.equals(this)){
            throw new DuplicateNameException();
        }
        Category found = (Category) find(parentCategory.getName());
        found.add(childCategory);
    }

    public void findAndRename(Rename rename) {
        if(rename.getNewName().equals(name)){
            rename(rename.getNewName());
        }else {
            Content found = find(rename.getOldName());
            found.rename(rename.getNewName());
        }
    }

    public Content findAndRemove(String name) {
        for (Content content : contents) {
            if (content.getName().equals(name)) {
                remove(name);
            } else if (content.getClass().equals(Category.class)) {
                return ((Category) content).findAndRemove(name);
            }
        }
        throw new NotFoundException();
    }


    private void add(Content content) {
        if (isExist(content.getName())) {
            throw new DuplicateNameException();
        }
        contents.add(content);
    }


    private boolean isExist(String contentName) {
        boolean exists = false;
        for (Content content : contents) {
            if (content.getName().equals(contentName) || name.equals(contentName)) {
                exists = true;
                break;
            }
            else if (content.getClass().equals(Category.class)) {
                exists = ((Category) content).isExist(contentName);
            }
        }
        return exists;

    }

    private void remove(String name) {
        contents.remove(name);
    }

}
