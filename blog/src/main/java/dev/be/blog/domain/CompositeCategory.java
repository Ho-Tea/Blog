package dev.be.blog.domain;

import java.util.ArrayList;
import java.util.List;

public class CompositeCategory implements Category{
    private String name;

    public CompositeCategory(String name){
        this.name = name;
    }
    private List<Category> childCategory = new ArrayList<Category>();

    @Override
    public void printChild() {
        printCategory();
        System.out.println("=====================");
        System.out.println("Child Categories");
        for(Category category : childCategory){
            category.printCategory();
        }
    }


    @Override
    public void printCategory(){
        System.out.println("=====================");
        System.out.println("Category Name = " + name);
    }

    @Override
    public void add(Category category) {
        childCategory.add(category);
    }

    @Override
    public void remove(Category category) {
        childCategory.remove(category);
    }


}
