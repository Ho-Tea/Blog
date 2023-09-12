package dev.be.blog.constant;

import dev.be.blog.exception.IllegalCommandException;

import java.util.Arrays;

public enum BlogCommand {
    DEFAULT(1),
    WRITE(2),
    UPDATE(3),
    DELETE(4),
    READ(5),
    EXIT(6);


    private int commandNumber;

    BlogCommand(int commandNumber) {
        this.commandNumber = commandNumber;
    }

    public boolean isExit() {
        return this.equals(EXIT);
    }

    public BlogCommand match(int commandNumber) {
        return Arrays.stream(BlogCommand.values()).filter(o -> o.commandNumber == commandNumber)
                .findFirst()
                .orElseThrow(() -> new IllegalCommandException());
    }

}
