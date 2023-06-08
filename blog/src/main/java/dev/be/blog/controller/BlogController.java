package dev.be.blog.controller;

import ch.qos.logback.classic.joran.action.ContextNameAction;
import dev.be.blog.domain.*;
import dev.be.blog.dto.CategoryDto;
import dev.be.blog.dto.PostDto;
import dev.be.blog.dto.RenameDto;
import dev.be.blog.exception.NotFoundException;
import dev.be.blog.view.InputView;
import dev.be.blog.view.OutputView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BlogController {

    private static final InputView INPUT = new InputView();
    private static final OutputView OUTPUT = new OutputView();
    private User user;
    private Category contents;


    public void run() {
        try {
            enroll();
            contents = Category.create(user.getNickname()); // 유저 이름으로 프로젝트를 생성한다
            while (!Option.isClose()) {
                OUTPUT.printCommand();
                INPUT.inputCommand();
                loading();
            }
            }catch(IOException e){
                e.getMessage();
            }catch(NotFoundException e){
                e.getMessage();
            }
    }

    private void loading() throws IOException, NotFoundException{
        if(Option.WRITE.equals(Option.status)){
            write();
        } else if (Option.UPDATE.equals(Option.status)) {
            rename();
        } else if (Option.DELETE.equals(Option.status)){
            delete();
        } else if (Option.LOOKUP.equals(Option.status)) {
            lookUp();
        } else if (Option.MAIN.equals(Option.status)){
            print();
        }
    }

    public void enroll() throws IOException{ // 회원 등록
        user = INPUT.enroll();
    }

    public void lookUp() throws IOException{
        String title = INPUT.lookUpPost();
        Content found = contents.find(title);
        OUTPUT.printPost((Post) found);
    }


    public void print(){    // 지금까지 작성한 글들의 제목과 카테고리 모두를 볼 수 있다
        showAll(contents);
    }
    private void showAll(Category contents){    // 모든 글과 카테고리 요약을 보여준다
        for(Content content : contents.getChild()){
            if(content.getClass().equals(Post.class)){
                OUTPUT.printContent(content);
            }else{
                OUTPUT.printContent(content);
                showAll((Category) content);
            }
        }
    }
    private void showCategory(Category contents){   // 카테고리만 보여준다
        for(Content content : contents.getChild()){
            OUTPUT.printContent(content);
            showCategory((Category) content);
        }
    }
    public void write() throws IOException{
        PostDto postDto = INPUT.writePost();
        showCategory(contents);
        CategoryDto categoryDto = INPUT.selectCategory();
        contents.findAndAdd(postDto, categoryDto,user);
    }

    public void rename() throws IOException{
        RenameDto renameDto = INPUT.rename();
        contents.findAndRename(renameDto);
    }

    public void delete() throws IOException{
            String name = INPUT.delete();
            contents.findAndRemove(name);
    }
    public void createCategory() throws IOException{

        String name = INPUT.delete();
    }
}
