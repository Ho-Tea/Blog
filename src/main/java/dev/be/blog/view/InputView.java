package dev.be.blog.view;


import dev.be.blog.constant.ContentType;
import dev.be.blog.dto.CategoryDto;
import dev.be.blog.dto.PostDto;
import dev.be.blog.dto.UserDto;
import dev.be.blog.exception.IllegalCommandException;
import dev.be.blog.vo.Rename;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputView {
    private static final String CONTOUR = '\n' + "==================================================" + '\n';
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    public UserDto enrollUser() {
        try {
            System.out.println(CONTOUR);
            System.out.println("프로필 등록을 시작합니다");
            System.out.println("이름을 입력해 주세요 : ");
            String name = READER.readLine();
            System.out.println("닉네임을 입력해 주세요 : ");
            String nickname = READER.readLine();
            System.out.println("나이를 입력해 주세요 : ");
            int age = Integer.parseInt(READER.readLine());
            System.out.println("이메일을 입력해 주세요 : ");
            String email = READER.readLine();
            System.out.println(CONTOUR);
            return new UserDto(name, nickname, age, email);
        } catch (IOException e) {
            e.printStackTrace();
            return enrollUser();
        }
    }

    public PostDto post() {
        try {
            System.out.println(CONTOUR);
            System.out.println("새로운 게시글을 작성합니다");
            System.out.println("제목을 입력해 주세요 : ");
            String title = READER.readLine();
            System.out.println("내용을 입력해 주세요 : ");
            String text = READER.readLine();
            System.out.println(CONTOUR);
            return new PostDto(title, text);
        } catch (IOException e) {
            e.printStackTrace();
            return post();
        }

    }

    public CategoryDto category() {
        try {
            System.out.println(CONTOUR);
            System.out.println("새로운 카테고리를 추가합니다");
            System.out.println("카테고리 이름을 입력해 주세요 : ");
            String name = READER.readLine();
            System.out.println(CONTOUR);
            return new CategoryDto(name);
        } catch (IOException e) {
            e.printStackTrace();
            return category();
        }
    }


    public ContentType selectContentType() {
        try {
            System.out.println(CONTOUR);
            System.out.println("카테고리를 생성하려면 1, 포스팅을 작성하려면 2를 입력해주세요");
            String number = READER.readLine();
            System.out.println(CONTOUR);
            return ContentType.match(Integer.parseInt(number));
        } catch (IOException e) {
            e.printStackTrace();
            return selectContentType();
        } catch (IllegalCommandException e) { //이거는 여기서 처리
            System.out.println(e.getMessage());
            return selectContentType();
        }
    }

    public String findPost() {
        try {
            System.out.println(CONTOUR);
            System.out.println("찾고자 하는 게시물의 title을 입력해 주세요");
            String title = READER.readLine();
            System.out.println(CONTOUR);
            return title;
        } catch (IOException e) {
            e.printStackTrace();
            return findPost();
        }


    }

    public String selectParentCategory() {
        try {
            System.out.println(CONTOUR);
            System.out.println("카테고리를 선택해주세요");
            String category = READER.readLine();
            System.out.println(CONTOUR);
            return category;
        } catch (IOException e) {
            e.printStackTrace();
            return selectParentCategory();
        }
    }

    public Rename rename() {
        try {
            System.out.println(CONTOUR);
            System.out.println("변경을 희망하는 카테고리 혹은 포스팅의 제목을 입력해 주세요");
            String oldName = READER.readLine();
            System.out.println("카테고리 혹은 포스팅의 새로운 제목을 입력해 주세요");
            String newName = READER.readLine();
            System.out.println(CONTOUR);
            return new Rename(oldName, newName);
        } catch (IOException e) {
            e.printStackTrace();
            return rename();
        }
    }

    public int command() {
        try {
            System.out.println("실행 하고자 하는 행동의 번호를 입력해주세요");
            String option = READER.readLine();
            System.out.println(CONTOUR);
            return Integer.parseInt(option);
        } catch (IOException e) {
            e.printStackTrace();
            command();
        }
        throw new IllegalCommandException();
    }


    public String delete() {
        try {
            System.out.println(CONTOUR);
            System.out.println("삭제하고자 하는 게시물 혹은 카테고리의 이름을 입력해 주세요");
            String name = READER.readLine();
            System.out.println(CONTOUR);
            return name;
        } catch (IOException e) {
            e.printStackTrace();
            return delete();
        }


    }

}
