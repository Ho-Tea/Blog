package dev.be.blog;

import dev.be.blog.domain.Category;
import dev.be.blog.domain.Content;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

//@SpringBootTest
class BlogApplicationTests {

    @Test
    public void findUserEmailOrElse() {
        String userEmail = "Empty";
        String result = Optional.ofNullable(userEmail)
                .orElse(getUserEmail());
        System.out.println(result);
    }


    @Test
    public void findUserEmailOrElseGet() {
        String userEmail = "Empty";
        String result = Optional.ofNullable(userEmail)
                .orElseGet(this::getUserEmail);

        System.out.println(result);
    }

    private String getUserEmail() {
        System.out.println("getUserEmail() Called");
        return "mangkyu@tistory.com";
    }

    @Test
    void contextLoads() {
    }

}
