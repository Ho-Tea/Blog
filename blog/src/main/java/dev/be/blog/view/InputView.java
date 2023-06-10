package dev.be.blog.view;


import dev.be.blog.domain.Blog;
import dev.be.blog.domain.ContentType;
import dev.be.blog.dto.CategoryDto;
import dev.be.blog.dto.PostDto;
import dev.be.blog.dto.Rename;
import dev.be.blog.dto.UserDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputView {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    public UserDto enrollUser() throws IOException {
        System.out.println("프로필 등록을 시작합니다");
        System.out.println("이름을 입력해 주세요 : ");
        String name = READER.readLine();
        System.out.println("닉네임을 입력해 주세요 : ");
        String nickname = READER.readLine();
        System.out.println("나이를 입력해 주세요 : ");
        int age = Integer.parseInt(READER.readLine());
        System.out.println("이메일을 입력해 주세요 : ");
        String email = READER.readLine();

        return new UserDto(name, nickname, age, email);
    }

    public PostDto post() throws IOException {
        System.out.println("새로운 게시글을 작성합니다");
        System.out.println("제목을 입력해 주세요 : ");
        String title = READER.readLine();
        System.out.println("내용을 입력해 주세요 : ");
        String text = READER.readLine();

        return new PostDto(title, text);
    }

    public CategoryDto category() throws IOException{
        System.out.println("새로운 카테고리를 추가합니다");
        System.out.println("카테고리 이름을 입력해 주세요 : ");
        String name = READER.readLine();
        return new CategoryDto(name);
    }


    public ContentType selectContentType() throws IOException {
        System.out.println("======================");
        System.out.println("카테고리를 생성하려면 1, 포스팅을 작성하려면 2를 입력해주세요");
        String number = READER.readLine();
        System.out.println("======================");
        return ContentType.valueOf(Integer.parseInt(number));
    }

    public String findPost() throws IOException {
        System.out.println("찾고자 하는 게시물의 title을 입력해 주세요");
        String title = READER.readLine();
        return title;

    }

    public CategoryDto selectCategory() throws IOException {
        System.out.println("카테고리를 선택해주세요");
        String category = READER.readLine();
        return new CategoryDto(category);


    }

    public Rename rename() throws IOException {
        System.out.println("변경을 희망하는 카테고리 혹은 포스팅의 제목을 입력해 주세요");
        String oldName = READER.readLine();
        System.out.println("카테고리 혹은 포스팅의 새로운 제목을 입력해 주세요");
        String newName = READER.readLine();

        return new Rename(oldName, newName);

    }

    public void command() throws IOException {
        System.out.println("실행 하고자 하는 행동의 번호를 입력해주세요");
        String option = READER.readLine();
        Blog.transfer(Integer.parseInt(option));
    }


    public String delete() throws IOException {
        System.out.println("삭제하고자 하는 게시물 혹은 카테고리의 이름을 입력해 주세요");
        String name = READER.readLine();
        return name;

    }

}
