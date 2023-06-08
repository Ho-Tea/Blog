package dev.be.blog;

import dev.be.blog.controller.BlogController;
import dev.be.blog.domain.*;
import dev.be.blog.view.InputView;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

//@SpringBootApplication
public class BlogApplication {

    public static void main(String[] args) throws InterruptedException, IOException {
//        SpringApplication.run(BlogApplication.class, args);
        BlogController blog = new BlogController();
        System.out.println(Option.WAIT.equals(Option.status));
        blog.run();
    }
}
