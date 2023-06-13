package dev.be.blog.domain;

import dev.be.blog.vo.Rename;
import dev.be.blog.exception.NotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Category implements Content {
    private String name;
    private List<Content> contents = new ArrayList<>();

    @Override
    public boolean rename(String newName) {
        this.name = newName;
        return true;
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

    public Content find(String categoryName) {
        for (Content content : contents) {
            if (content.getName().equals(categoryName)) {
                return content;
            } else if (content.getClass().equals(Category.class)) {
                return ((Category) content).find(categoryName);
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

    public boolean findAndAdd(Post post, String categoryName) {
        if(categoryName.equals(this.name)){
            return add(post);
        }
        Category found = (Category) find(categoryName);
        return found.add(post);
    }

    public boolean findAndAdd(Category childCategory, String categoryName) {
        if(categoryName.equals(this.name)){
            return add(childCategory);
        }
        Category found = (Category) find(categoryName);
        return found.add(childCategory);

    }

    public boolean findAndRename(Rename rename) {
        if(rename.getOldName().equals(this.name)){
            return rename(rename.getNewName());
        }
        Content found = find(rename.getOldName());
        return found.rename(rename.getNewName());
    }

    public boolean findAndRemove(String name) {
        for (Content content : contents) {
            if (content.getName().equals(name)) {
                return remove(content);
            } else if (content.getClass().equals(Category.class)) {
                return ((Category) content).findAndRemove(name);
            }
        }
        throw new NotFoundException();
    }


    public boolean add(Content content) {
        return contents.add(content);
    }


    public boolean isExist(String contentName) {
        boolean exists = false;
        if(this.name.equals(contentName)){
            return true;
        }
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

    private boolean remove(Content content) {
        return contents.remove(content);
    }

    @Override
    public String toString() {
        return '\n' + "  Category : " +
                '\n' +
                '\t' + "name='" + name + '\'' +
                '\n' +
                '\t' + "contents=" + contents +
                '\n';
    }
}
