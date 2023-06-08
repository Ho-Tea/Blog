package dev.be.blog.view;

import dev.be.blog.domain.Category;
import dev.be.blog.domain.Content;
import dev.be.blog.domain.Option;
import dev.be.blog.domain.Post;

import java.util.List;

public class OutputView {
    public void printContent(Content content){
        System.out.println("====================");
        System.out.println();
        System.out.println(content.getClass().getName() + " = " + content.getName());
        System.out.println();
        System.out.println("====================");
    }
    public void printPost(Post post){
        System.out.println(post);
    }

    public void printCommand(){
        System.out.println("======================");
        System.out.println();
        for(Option option : Option.values()){
            System.out.println(option.getNumber() + " : " + option );
        }
        System.out.println();
        System.out.println("======================");
    }

}
