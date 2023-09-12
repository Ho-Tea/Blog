package dev.be.blog.domain;

import dev.be.blog.exception.NotFoundException;
import dev.be.blog.vo.Rename;

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

    public Content find(String contentName) {
        for (Content content : contents) {
            if (content.getName().equals(contentName)) {
                return content;
            } else if (content.getClass().equals(Category.class)) {
                return ((Category) content).find(contentName);
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

    public boolean add(Post post, String categoryName) {
        if (categoryName.equals(this.name)) {
            return add(post);
        }
        Category found = (Category) find(categoryName);
        return found.add(post);
    }

    public boolean add(Category childCategory, String categoryName) {
        if (categoryName.equals(this.name)) {
            return add(childCategory);
        }
        Category found = (Category) find(categoryName);
        return found.add(childCategory);

    }

    public boolean rename(Rename rename) {
        if (rename.getOldName().equals(this.name)) {
            return rename(rename.getNewName());
        }
        Content found = find(rename.getOldName());
        return found.rename(rename.getNewName());
    }

    public boolean remove(String name) {
        for (Content content : contents) {
            if (content.getName().equals(name)) {
                return remove(content);
            } else if (content.getClass().equals(Category.class)) {
                return ((Category) content).remove(name);
            }
        }
        throw new NotFoundException();
    }


    protected boolean add(Content content) {
        return contents.add(content);
    }


    public boolean isExist(String contentName) {
        boolean exists = false;
        if (this.name.equals(contentName)) {
            return true;
        }
        for (Content content : contents) {
            if (content.getName().equals(contentName) || name.equals(contentName)) {
                exists = true;
                break;
            } else if (content.getClass().equals(Category.class)) {
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
