package dev.be.blog.constant;

import dev.be.blog.exception.IllegalCommandException;

import java.util.Arrays;
import java.util.List;

public enum Blog {
    DEFAULT(1),
    WRITE(2),
    UPDATE(3),
    DELETE(4),
    READ(5),
    EXIT(6);


    private int commandNumber;

    Blog(int commandNumber) {
        this.commandNumber = commandNumber;
    }


    public boolean isDefault() {
        return this.equals(DEFAULT);
    }

    public boolean isWrite() {
        return this.equals(WRITE);
    }

    public boolean isUpdate() {
        return this.equals(UPDATE);
    }

    public boolean isDelete() {
        return this.equals(DELETE);
    }

    public boolean isRead() {
        return this.equals(READ);
    }

    public boolean isExit() {
        return this.equals(EXIT);
    }

    public Blog match(int commandNumber) {
        return Arrays.stream(Blog.values()).filter(o -> o.commandNumber == commandNumber)
                .findFirst()
                .orElseThrow(() -> new IllegalCommandException());
    }


}
