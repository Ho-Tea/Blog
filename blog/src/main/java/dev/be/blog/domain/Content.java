package dev.be.blog.domain;

public interface Content {
    boolean rename(String name);

    String getName();

    default boolean is(Object o) {
        return this.getClass().equals(o);
    }

}
