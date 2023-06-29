package dev.be.blog.constant;

import dev.be.blog.exception.IllegalCommandException;

import java.util.Arrays;
import java.util.List;

public enum Blog {
    MAIN(1),
    WRITE(2),
    UPDATE(3),
    DELETE(4),
    LOOKUP(5),
    EXIT(6);


    private int commandNumber;

    Blog(int commandNumber) {
        this.commandNumber = commandNumber;
    }


    public static boolean isMain(Blog blog) {
        return blog.equals(MAIN);
    }

    public static boolean isWrite(Blog blog) {
        return blog.equals(WRITE);
    }

    public static boolean isUpdate(Blog blog) {
        return blog.equals(UPDATE);
    }

    public static boolean isDelete(Blog blog) {
        return blog.equals(DELETE);
    }

    public static boolean isLookUp(Blog blog) {
        return blog.equals(LOOKUP);
    }

    public static boolean isClose(Blog blog) {
        return blog.equals(EXIT);
    }

    public static Blog match(int commandNumber) {
        return Arrays.stream(Blog.values()).filter(o -> o.commandNumber == commandNumber)
                .findFirst()
                .orElseThrow(() -> new IllegalCommandException());
    }


}
