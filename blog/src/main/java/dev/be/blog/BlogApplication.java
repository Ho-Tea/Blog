package dev.be.blog;

import dev.be.blog.controller.BlogController;
import dev.be.blog.domain.*;

import java.io.IOException;

//@SpringBootApplication
public class BlogApplication {

    public static void main(String[] args) throws InterruptedException, IOException {
//        SpringApplication.run(BlogApplication.class, args);
        BlogController blog = new BlogController();
        blog.init();
        blog.run();

    }
}
