package dev.be.blog.controller;

import dev.be.blog.constant.Blog;
import dev.be.blog.constant.ContentType;
import dev.be.blog.domain.*;
import dev.be.blog.dto.*;
import dev.be.blog.exception.DuplicateNameException;
import dev.be.blog.exception.IllegalContentTypeException;
import dev.be.blog.view.InputView;
import dev.be.blog.view.OutputView;
import dev.be.blog.dto.ContentDto;
import dev.be.blog.vo.Rename;

import java.util.function.Supplier;

public class BlogController {

    private static final InputView INPUT = new InputView();
    private static final OutputView OUTPUT = new OutputView();
    private User user;
    private Category contents;
    private Blog blog;

    public BlogController(){
        UserDto userDto = INPUT.enrollUser();   // 회원등록하는 과정에서의 예외는 우선 보류한다
        createBlogByUserName(userDto);
    }

    public void createBlogByUserName(UserDto userDto) {
        user = UserDto.toEntity(userDto);
        contents = Category.create(user.getNickname());
    }

    public void run() {
        while (!blog.isExit()) {
            inputCommand();
            loading();
        }
    }


    public void inputCommand() {
        OUTPUT.commandType();
        blog = blog.match(INPUT.command());
    }



    private void loading() {
        judgeWrite();
        judgeUpdate();
        judgeDelete();
        judgeLookUp();
        judgeMain();
    }

    private void judgeWrite(){
        if (blog.isWrite()){
            repeatLogic(this::write);
        }
    }
    private void judgeUpdate(){
        if (blog.isUpdate()){
            repeatLogic(this::rename);
        }
    }
    private void judgeDelete(){
        if (blog.isDelete()){
            repeatLogic(this::delete);
        }
    }
    private void judgeLookUp(){
        if (blog.isRead()){
            lookUp();
        }
    }
    private void judgeMain(){
        if (blog.isDefault()){
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
        ContentType contentType = contentDto.getContentType();
        if (contentType.isPost()) {
            PostDto postDto = inputPostDetail();
            return save(postDto, contentDto.getCategoryDto());
        } else if (contentType.isCategory()){
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
        CategoryDto categoryDto = CategoryDto.fromEntity(contents);
        OUTPUT.content(categoryDto);
    }

    public void lookUp() {
        String title = repeatLogic(this::isPostTitle);
        Post found = (Post) contents.find(title);
        PostDto foundDto = PostDto.fromEntity(found);
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

    public  <T> T repeatLogic(Supplier<T> inputSupplier) {
        try {
            return inputSupplier.get();
        } catch (RuntimeException e) {
            OUTPUT.exception(e.getMessage());
            return repeatLogic(inputSupplier);
        }
    }


}
