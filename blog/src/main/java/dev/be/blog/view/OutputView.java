package dev.be.blog.view;

import dev.be.blog.domain.Content;
import dev.be.blog.domain.Blog;
import dev.be.blog.domain.ContentType;
import dev.be.blog.domain.Post;
import dev.be.blog.dto.CategoryDto;
import dev.be.blog.dto.PostDto;

import java.util.Arrays;

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

    public void command(){
        System.out.println("======================");
        System.out.println();
        for(Blog blog : Blog.command()){
            System.out.println(blog.ordinal() + " : " + blog);
        }
        System.out.println();
        System.out.println("======================");
    }

}
