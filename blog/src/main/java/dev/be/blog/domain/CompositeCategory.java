package dev.be.blog.domain;

import dev.be.blog.exception.DuplicateNameException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CompositeCategory implements Category{
    private String name;
    private List<Category> childCategory = new ArrayList<Category>();

    public CompositeCategory(String name){
        this.name = Optional.ofNullable(name)
                .orElseGet(this::createEmptyCategory);  // Optional
    }
    public String createEmptyCategory(){
        return new String("제목없음");
    }

    @Override
    public void add(Category category) {
        if(invalidCategoryName(category.getName())){
            throw new DuplicateNameException();
        }
        childCategory.add(category);
    }

    private boolean invalidCategoryName(String newCategoryName) {
        return this.name.equals(newCategoryName) ||
                childCategory.contains(newCategoryName);
    }


    @Override
    public void remove(Category category) {
        if(category.getName().equals(this.name)){
            childCategory.clear();
            name = null;
            return ;
        }
        childCategory.remove(category);
    }

    @Override
    public void rename(String newName) {
        if(invalidCategoryName(newName)){
            throw new DuplicateNameException();
        }
        this.name.replace(this.name, newName);
    }

    @Override
    public String toString() {
        return "현재 카테고리 = " + name + '\'' +
                ", childCategory=" + childCategory.stream().iterator();
    }

    @Override
    public String getName() {
        return this.name;
    }


}
