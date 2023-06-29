package dev.be.blog.constant;

import dev.be.blog.exception.IllegalCommandException;

import java.util.Arrays;

public enum ContentType {
    CATEGORY(1),
    POST(2);

    private int number;

    ContentType(int number) {
        this.number = number;
    }


    public static ContentType match(int number) {
        return Arrays.stream(ContentType.values())
                .filter(o -> o.number == number)
                .findFirst()
                .orElseThrow(() -> new IllegalCommandException());

    }

    public boolean isCategory(){
        return this.equals(ContentType.CATEGORY);
    }

    public boolean isPost(){
        return this.equals(ContentType.POST);
    }


}
