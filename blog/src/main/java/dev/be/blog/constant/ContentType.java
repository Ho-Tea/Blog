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


    public static ContentType valueOf(int number) {
        return Arrays.stream(ContentType.values())
                .filter(o -> o.number == number)
                .findFirst()
                .orElseThrow(() -> new IllegalCommandException());

    }

    public static boolean isCategory(ContentType contentType){
        return contentType.equals(ContentType.CATEGORY);
    }

    public static boolean isPost(ContentType contentType){
        return contentType.equals(ContentType.POST);
    }


}
