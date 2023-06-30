package dev.be.blog.view;

import dev.be.blog.constant.Blog;
import dev.be.blog.dto.CategoryDto;
import dev.be.blog.dto.PostDto;

public class OutputView {
    // content는 인터페이스이므로 괜찮
    public void content(CategoryDto categoryDto){
        System.out.println("====================");
        System.out.println();
        System.out.println(categoryDto);
        System.out.println();
        System.out.println("====================");
    }
    public void post(PostDto postDto){
        System.out.println(postDto);
    }

    public void commandType(){
        System.out.println("======================");
        System.out.println();
        for(Blog blog : Blog.values()){
            System.out.println(blog.ordinal() + " : " + blog);
        }
        System.out.println();
        System.out.println("======================");
    }

    public void exception(String message){
        System.out.println(message);
    }

}
