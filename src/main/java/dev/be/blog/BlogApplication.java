package dev.be.blog;

import dev.be.blog.controller.BlogController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class BlogApplication {

    public static void main(String[] args) throws InterruptedException, IOException {
        SpringApplication.run(BlogApplication.class, args);
//        BlogController blog = new BlogController();
//        blog.run();

    }
}
