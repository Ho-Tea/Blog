package dev.be.blog.view;

import dev.be.blog.constant.BlogCommand;
import dev.be.blog.dto.CategoryDto;
import dev.be.blog.dto.PostDto;

public class OutputView {
    public void content(CategoryDto categoryDto) {
        System.out.println("====================");
        System.out.println();
        System.out.println(categoryDto);
        System.out.println();
        System.out.println("====================");
    }

    public void post(PostDto postDto) {
        System.out.println(postDto);
    }

    public void commandType() {
        System.out.println("======================");
        System.out.println();
        for (BlogCommand blog : BlogCommand.values()) {
            System.out.println(blog.ordinal()+1 + " : " + blog);
        }
        System.out.println();
        System.out.println("======================");
    }

    public void exception(String message) {
        System.out.println(message);
    }

}
