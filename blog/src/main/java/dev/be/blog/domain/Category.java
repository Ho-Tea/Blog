package dev.be.blog.domain;

import dev.be.blog.dto.CategoryDto;
import dev.be.blog.dto.PostDto;
import dev.be.blog.dto.RenameDto;
import dev.be.blog.exception.DuplicateNameException;
import dev.be.blog.exception.NotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Category implements Content {
    private String name;
    private List<Content> contents = new ArrayList<>();


    private Category(String name){
        this.name = name;
    }
    public static Category create(String name){
        return new Category(name);
    }

    @Override
    public List<Content> getChild(){
        return Collections.unmodifiableList(contents);
    }

    public Content find(String name){
        for(Content content : contents){
            if(content.getName().equals(name)){
                return content;
            }
            else if(content.getClass().equals(Category.class)){
                return ((Category) content).find(name);
            }
        }
        throw new NotFoundException();
    }

    public void findAndAdd(PostDto postDto, CategoryDto categoryDto, User user){
        try{
            Content found = find(categoryDto.getName());
            found.add(postDto.upload(user));

        }catch (NotFoundException e){
            e.getMessage();
        }
    }

    public void findAndRename(RenameDto renameDto){
        Content found = find(renameDto.getOldName());
        found.rename(renameDto.newName);
    }

    public Content findAndRemove(String name){
        for(Content content : contents){
            if(content.getName().equals(name)){
                contents.remove(name);
            }
            else if(content.getClass().equals(Category.class)){
                return ((Category) content).findAndRemove(name);
            }
        }
        throw new NotFoundException();
    }



    @Override
    public void add(Content content) {
        if(contains(content.getName())){
            throw new DuplicateNameException();
        }
        contents.add(content);
    }


    private boolean contains(String contentName) {
        boolean exists = false;
        for(Content content : contents){
            if(content instanceof Category){
                exists = ((Category) content).contains(contentName);
            }
            if(content.getName().equals(contentName)){
                exists = true;
            }
        }
        return exists;
    }


    @Override
    public void remove(String name) {
        contents.remove(name);
    }

    @Override
    public void rename(String newName) {
        this.name = newName;
    }


    @Override
    public String getName() {
        return this.name;
    }


}
