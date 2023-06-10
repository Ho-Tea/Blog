package dev.be.blog.domain;

import dev.be.blog.exception.NotFoundException;

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
                .orElseThrow(() -> new NotFoundException());

    }

    public static ContentType valueOf(Content content) {
        return Arrays.stream(ContentType.values())
                .filter(o -> o.name().equals(content.getClass().getName()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException());

    }

}
