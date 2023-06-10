package dev.be.blog.controller;

import dev.be.blog.domain.*;
import dev.be.blog.dto.*;
import dev.be.blog.exception.DuplicateNameException;
import dev.be.blog.exception.NotFoundException;
import dev.be.blog.view.InputView;
import dev.be.blog.view.OutputView;

import java.io.IOException;

public class BlogController {

    private static final InputView INPUT = new InputView();
    private static final OutputView OUTPUT = new OutputView();
    private User user;
    private Category contents;


    public void run() {
        try {
            UserDto userDto = inputUserDetails();
            createBlogByUserName(userDto);
            while (!Blog.ofClose()) {
                OUTPUT.command();
                INPUT.command();
                loading();}
        } catch (IOException e) {
            e.getMessage();
        } catch (NotFoundException e) {
            e.getMessage();
        }
    }
    public UserDto inputUserDetails() throws IOException{
        UserDto userDto = INPUT.enrollUser();
        return userDto;
    }

    public void createBlogByUserName(UserDto userDto){
        user = UserDto.toEntity(userDto);
        contents = Category.create(user.getNickname());
    }


    private void loading() throws IOException, NotFoundException {
        if (Blog.ofWrite()) {
            ContentDto contentDto = createContent();
            write(contentDto);
        } else if (Blog.ofUpdate()) {
            rename();
        } else if (Blog.ofDelete()) {
            delete();
        } else if (Blog.ofLookUp()) {
            lookUp();
        } else if (Blog.of()) {
            show(contents);
        }
    }

    public ContentDto createContent() throws IOException{
        ContentType contentType = INPUT.selectContentType();
        CategoryDto parentCategoryDto = INPUT.selectCategory();
        return new ContentDto(contentType, parentCategoryDto);
    }
    public void write(ContentDto contentDto) throws IOException{
        try {
            if (contentDto.getContentType().equals(ContentType.POST)) {
                PostDto postDto = INPUT.post();
                save(postDto, contentDto.getCategoryDto());
            } else if (contentDto.getContentType().equals(ContentType.CATEGORY)) {
                CategoryDto childCategoryDto = INPUT.category();
                save(childCategoryDto, contentDto.getCategoryDto());
            }
        } catch (NotFoundException e){
            e.printStackTrace();
        }catch (DuplicateNameException e){
            e.printStackTrace();
        }

    }

    public void save(PostDto postDto, CategoryDto parentCategoryDto){
        Post post = PostDto.toEntity(postDto, user);
        Category category = CategoryDto.toEntity(parentCategoryDto);
        contents.findAndAdd(post, category);
    }
    public void save(CategoryDto childCategoryDto, CategoryDto parentCategoryDto){
        Category parentCategory = CategoryDto.toEntity(parentCategoryDto);
        Category childCategory = CategoryDto.toEntity(childCategoryDto);
        contents.findAndAdd(childCategory, parentCategory);
    }



    private void show(Category contents) {
        CategoryDto categoryDto = CategoryDto.from(contents);
        OUTPUT.content(categoryDto);
        for (Content content : contents.getChild()) {
            if (ContentType.valueOf(content).equals(ContentType.CATEGORY)) {
                show((Category) content);
            }
        }
    }

    public void lookUp() throws IOException {
        String title = INPUT.findPost();
        Post found = (Post) contents.find(title);
        PostDto foundDto = Post.toDto(found);
        OUTPUT.post(foundDto);
    }

    public void rename() throws IOException {
        Rename rename = INPUT.rename();
        contents.findAndRename(rename);
    }

    public void delete() throws IOException {
        String name = INPUT.delete();
        contents.findAndRemove(name);
    }

}
