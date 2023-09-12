package dev.be.blog.controller;

import dev.be.blog.constant.BlogCommand;
import dev.be.blog.constant.ContentType;
import dev.be.blog.domain.Category;
import dev.be.blog.domain.Post;
import dev.be.blog.domain.User;
import dev.be.blog.dto.CategoryDto;
import dev.be.blog.dto.PostDto;
import dev.be.blog.dto.UserDto;
import dev.be.blog.exception.DuplicateNameException;
import dev.be.blog.view.InputView;
import dev.be.blog.view.OutputView;
import dev.be.blog.vo.Rename;

import java.util.Map;
import java.util.function.Supplier;

public class BlogController {

    private static final InputView INPUT = new InputView();
    private static final OutputView OUTPUT = new OutputView();
    private User user;
    private Category rootContent;
    private BlogCommand blogCommand = BlogCommand.DEFAULT;
    private Map<BlogCommand, Supplier> commandResolveMap;

    public BlogController() {
        UserDto userDto = INPUT.enrollUser();
        user = UserDto.toEntity(userDto);
        rootContent = Category.create(user.getNickname());
        saveCommandList();
    }
    //-> 테스트가 주가 되어서는 안된다
    // 테스트용 생성자를 하나 더 만드는 것도 가능하지만 굳이 그렇게해서 테스트를 진행하지는 않겠다


    private void saveCommandList() {
        commandResolveMap = Map.of(
                BlogCommand.DEFAULT, this::show,
                BlogCommand.WRITE, this::write,
                BlogCommand.UPDATE, this::rename,
                BlogCommand.DELETE, this::delete,
                BlogCommand.READ, this::read
        );
    }

    public void run() {
        while (!(inputCommand().isExit())) {
            loadCommand();
        }
    }


    private BlogCommand inputCommand() {
        OUTPUT.commandType(); // 가능한 모든 블로그 관련 행동 출력
        blogCommand = blogCommand.match(INPUT.command()); //블로그 관련 행동 입력 받아 지정
        return blogCommand;
    }


    private <T> void loadCommand() {
        Supplier<T> commandResolve = commandResolveMap.get(blogCommand);
        repeatLogic(commandResolve);
    }


    private boolean write() {
        ContentType contentType = INPUT.selectContentType();
        String parentCategoryName = INPUT.selectParentCategory();
        if (contentType.is(ContentType.POST)) {
            PostDto postDto = inputPostDetail();
            return save(postDto, parentCategoryName);
        }
        CategoryDto categoryDto = inputCategoryDetail();
        return save(categoryDto, parentCategoryName);
    }


    private PostDto inputPostDetail() {
        PostDto postDto = INPUT.post();
        validateName(postDto.getName());
        return postDto;
    }

    private CategoryDto inputCategoryDetail() {
        CategoryDto categoryDto = INPUT.category();
        validateName(categoryDto.getName());
        return categoryDto;
    }

    protected void validateName(String name) {
        if (rootContent.isExist(name)) {
            throw new DuplicateNameException();
        }
    }


    protected boolean save(PostDto dto, String parentCategoryName) {
        Post content = PostDto.toEntity(dto, user);
        return rootContent.add(content, parentCategoryName);
    }

    protected boolean save(CategoryDto dto, String parentCategoryName) {
        Category content = CategoryDto.toEntity(dto);
        return rootContent.add(content, parentCategoryName);
    }


    private boolean show() {
        CategoryDto categoryDto = CategoryDto.fromEntity(rootContent);
        OUTPUT.content(categoryDto);
        return true;
    }

    private boolean read() {
        String title = INPUT.findPost();
        Post found = (Post) rootContent.find(title);
        PostDto foundDto = PostDto.fromEntity(found);
        OUTPUT.post(foundDto);
        return true;
    }


    private boolean rename() {
        Rename rename = INPUT.rename();
        return rootContent.rename(rename);
    }

    private boolean delete() {
        String name = INPUT.delete();
        return rootContent.remove(name);
    }


    private <T> T repeatLogic(Supplier<T> inputSupplier) {
        try {
            return inputSupplier.get();
        } catch (RuntimeException e) {
            OUTPUT.exception(e.getMessage());
            return repeatLogic(inputSupplier);
        }
    }


}
