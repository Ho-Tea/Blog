package dev.be.blog.controller;

import dev.be.blog.constant.Blog;
import dev.be.blog.constant.ContentType;
import dev.be.blog.domain.*;
import dev.be.blog.dto.*;
import dev.be.blog.exception.DuplicateNameException;
import dev.be.blog.exception.IllegalContentTypeException;
import dev.be.blog.exception.NotFoundException;
import dev.be.blog.view.InputView;
import dev.be.blog.view.OutputView;

import java.util.function.Supplier;

public class BlogController {

    private static final InputView INPUT = new InputView();
    private static final OutputView OUTPUT = new OutputView();
    private User user;
    private Category contents;


    public void init() {
        UserDto userDto = INPUT.enrollUser();   // 회원등록하는 과정에서의 예외는 우선 보류한다
        createBlogByUserName(userDto);
    }

    public void run() {
        while (!Blog.ofClose()) {
            inputCommand();
            loading();
        }
    }

    public void inputCommand() {
        OUTPUT.commandType();
        INPUT.command();
    }


    public void createBlogByUserName(UserDto userDto) {
        user = UserDto.toEntity(userDto);
        contents = Category.create(user.getNickname());
    }


    private void loading() {
        if (Blog.ofWrite()) {
            repeatLogic(this::write);
        } else if (Blog.ofUpdate()) {
            repeatLogic(this::rename);
        } else if (Blog.ofDelete()) {
            repeatLogic(this::delete);
        } else if (Blog.ofLookUp()) {
            lookUp();
        } else if (Blog.of()) {
            show();
        }
    }

    public ContentDto createContent() {
        ContentType contentType = INPUT.selectContentType();
        CategoryDto parentCategoryDto = INPUT.selectCategory();
        return new ContentDto(contentType, parentCategoryDto);
    }

    public boolean write() {
        ContentDto contentDto = createContent();
        if (ContentType.isPost(contentDto.getContentType())) {
            PostDto postDto = inputPostDetail();
            return save(postDto, contentDto.getCategoryDto());
        } else if (ContentType.isCategory(contentDto.getContentType())) {
            CategoryDto childCategoryDto = inputCategoryDetail();
            return save(childCategoryDto, contentDto.getCategoryDto());
        }
        throw new IllegalContentTypeException();
    }



    public PostDto inputPostDetail(){
        PostDto postDto = INPUT.post();
        validateName(postDto.getName());
        return postDto;
    }
    public void validateName(String name){
        if(contents.isExist(name)){
            throw new DuplicateNameException();
        }
    }

    public CategoryDto inputCategoryDetail(){
        CategoryDto categoryDto = INPUT.category();
        validateName(categoryDto.getName());
        return categoryDto;
    }
    public <T> boolean save(T dto, CategoryDto parentCategoryDto) {
        String categoryName = parentCategoryDto.getName();
        if (dto.getClass().equals(PostDto.class)) {
            Post content = PostDto.toEntity((PostDto) dto, user);
            return contents.findAndAdd(content, categoryName);
        }
        Category content = CategoryDto.toEntity((CategoryDto)dto);
        return contents.findAndAdd(content, categoryName);
    }


    private void show() {
        CategoryDto categoryDto = CategoryDto.from(contents);
        OUTPUT.content(categoryDto);
    }

    public void lookUp() {
        String title = repeatLogic(this::isPostTitle);
        Post found = (Post) contents.find(title);
        PostDto foundDto = Post.toDto(found);
        OUTPUT.post(foundDto);
    }



    public boolean rename() {
        Rename rename = INPUT.rename();
        return contents.findAndRename(rename);
    }

    public boolean delete() {
        String name = INPUT.delete();
        return contents.findAndRemove(name);
    }

    public String isPostTitle(){
        String title = INPUT.findPost();
        if (contents.find(title).getClass().equals(Category.class)) {
            throw new IllegalContentTypeException();
        }
        return title;
    }

    private <T> T repeatLogic(Supplier<T> inputSupplier) {
        try {
            return inputSupplier.get();
        } catch (NotFoundException e) {
            OUTPUT.exception(e.getMessage());
            return repeatLogic(inputSupplier);
        } catch (IllegalContentTypeException e){
            OUTPUT.exception(e.getMessage());
            return repeatLogic(inputSupplier);
        } catch (DuplicateNameException e){
            OUTPUT.exception(e.getMessage());
            return repeatLogic(inputSupplier);
        }
    }


}
