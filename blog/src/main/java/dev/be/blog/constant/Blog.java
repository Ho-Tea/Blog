package dev.be.blog.constant;

import dev.be.blog.exception.IllegalCommandException;

import java.util.Arrays;
import java.util.List;

public enum Blog {
    WAIT(0),
    MAIN(1),
    WRITE(2),
    UPDATE(3),
    DELETE(4),
    LOOKUP(5),
    EXIT(6);

    public static Blog status = Blog.WAIT;

    private int statusNumber;

    Blog(int statusNumber) {
        this.statusNumber = statusNumber;
    }


    public static boolean is() {
        return status.equals(Blog.MAIN);
    }

    public static boolean isWrite() {
        return status.equals(Blog.WRITE);
    }

    public static boolean isUpdate() {
        return status.equals(Blog.UPDATE);
    }

    public static boolean isDelete() {
        return status.equals(Blog.DELETE);
    }

    public static boolean isLookUp() {
        return status.equals(Blog.LOOKUP);
    }

    public static boolean isClose() {
        return status.equals(Blog.EXIT);
    }

    public static void transfer(int statusNumber) {
        status = Arrays.stream(Blog.values()).filter(o -> o.statusNumber == statusNumber)
                .findFirst()
                .orElseThrow(() -> new IllegalCommandException());
    }

    public static List<Blog> command(){
        return Arrays.stream(Blog.values()).filter(o -> o.ordinal() > 0).toList();
    }

}
