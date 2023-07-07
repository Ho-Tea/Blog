package dev.be.blog.constant;

import dev.be.blog.exception.IllegalCommandException;

import java.util.Arrays;

public enum ContentType {
    CATEGORY(1),
    POST(2);

    private int contentNumber;

    ContentType(int contentNumber) {
        this.contentNumber = contentNumber;
    }


    public static ContentType match(int contentNumber) {
        return Arrays.stream(ContentType.values())
                .filter(o -> o.contentNumber == contentNumber)
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
