package dev.be.blog.domain;

import dev.be.blog.exception.NotFoundException;

import java.util.Arrays;

public enum Option {
    WAIT("wait",0),
    WRITE("Write",1),
    UPDATE("Update", 2),
    DELETE("Delete", 3),
    LOOKUP("LookUp", 4),
    MAIN("Main", 5),
    EXIT("Exit", 6);

    public static Option status = Option.WAIT;

    private String behavior;
    private int number;
    Option(String behavior, int number) {
        this.behavior = behavior;
        this.number = number;
    }
    public static boolean isClose(){
        return status.equals(Option.EXIT);
    }


    public static void transfer(int number){
        status = Arrays.stream(Option.values()).filter(o -> o.getNumber() == number)
                .findFirst()
                .orElseThrow(() -> new NotFoundException());
    }

    public int getNumber() {
        return number;
    }
}
