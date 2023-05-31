package dev.be.blog;

import dev.be.blog.domain.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication
public class BlogApplication {

    public static void main(String[] args) throws InterruptedException {
//        SpringApplication.run(BlogApplication.class, args);

        // user를 먼저 생성한다
        Profile user = Profile.builder()
                .name("윤주호")
                .nickname("Ho-Tea")
                .age(25)
                .address("경기도 안양시")
                .email("dbswn990@gmail.com")
                .image(null)
                .build();

        // JAVA 카테고리를 생성한다
        Category java = new CompositeCategory("JAVA");

        // JAVA 카테고리에 속해있는 모든 카테고리들을 출력한다
        // 현재는 java 카테고리 밑에 아무것도 없어 출력이 안되는 것이 맞다
        System.out.println("자바 카테고리 밑에 아무것도 없어 출력이 안되어야 한다");
        java.printChild();

        System.out.println();
        System.out.println("자바 카테고리 밑에 Integer와 String이 출력되어야 한다");
        Category integer = new CompositeCategory("Integer");
        Category string = new CompositeCategory("String");

        java.add(integer);
        java.add(string);
        java.printChild();

        System.out.println();
        System.out.println("자바 카테고리 밑에 String만이 출력되어야 한다");
        java.remove(integer);
        java.printChild();




        System.out.println();

        Post post = Post.register(
                java,
                Context.write(
                        "첫 게시물 제목",
                        "처음 쓰는 게시물에 대한 내용")
                ,user);

        post.print();


        System.out.println();

        Thread.sleep(2000);
        post.editTitle("변경된 게시물 제목");
        post.editText("변경된 게시물에 대한 내용");

        post.print();


        System.out.println();

        Thread.sleep(2000);
        post.edit("최종변경된 제목", "최종변경된 게시물에 대한 내용");
        post.print();


    }

}
