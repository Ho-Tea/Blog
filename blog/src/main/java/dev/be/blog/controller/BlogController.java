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
        UserDto userDto = INPUT.enrollUser();
        createBlogByUserName(userDto);
    }

    protected void createBlogByUserName(UserDto userDto) {
        user = UserDto.toEntity(userDto);
        contents = Category.create(user.getNickname());
    }

    public void run() {
        while (!blog.isExit()) {
            inputCommand();
            loadCommand();
        }
    }


    public void inputCommand() {
        OUTPUT.commandType();
        blog = blog.match(INPUT.command());
    }



    private void loadCommand() {
        judgeWrite();
        judgeUpdate();
        judgeDelete();
        judgeRead();
        judgeDefault();
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
    private void judgeRead(){
        if (blog.isRead()){
            read();
        }
    }
    private void judgeDefault(){
        if (blog.isDefault()){
            show();
        }
    }

    private ContentDto createContent() {
        ContentType contentType = INPUT.selectContentType();
        CategoryDto parentCategoryDto = INPUT.selectCategory();
        return new ContentDto(contentType, parentCategoryDto);
    }

    private boolean write() {
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



    private PostDto inputPostDetail(){
        PostDto postDto = INPUT.post();
        validateName(postDto.getName());
        return postDto;
    }
    protected void validateName(String name){
        if(contents.isExist(name)){
            throw new DuplicateNameException();
        }
    }

    private CategoryDto inputCategoryDetail(){
        CategoryDto categoryDto = INPUT.category();
        validateName(categoryDto.getName());
        return categoryDto;
    }
    protected  <T> boolean save(T dto, CategoryDto parentCategoryDto) {
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

    private void read() {
        String title = repeatLogic(this::isPostTitle);
        Post found = (Post) contents.find(title);
        PostDto foundDto = PostDto.fromEntity(found);
        OUTPUT.post(foundDto);
    }



    private boolean rename() {
        Rename rename = INPUT.rename();
        return contents.findAndRename(rename);
    }

    private boolean delete() {
        String name = INPUT.delete();
        return contents.findAndRemove(name);
    }

    private String isPostTitle(){
        String title = INPUT.findPost();
        if (contents.find(title).getClass().equals(Category.class)) {
            throw new IllegalContentTypeException();
        }
        return title;
    }

    private  <T> T repeatLogic(Supplier<T> inputSupplier) {
        try {
            return inputSupplier.get();
        } catch (RuntimeException e) {
            OUTPUT.exception(e.getMessage());
            return repeatLogic(inputSupplier);
        }
    }


}
