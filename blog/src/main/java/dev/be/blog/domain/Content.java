package dev.be.blog.domain;

import java.util.Collections;
import java.util.List;

public interface Content {
    void rename(String name);   // update
    String getName();
    default void add(Content content){};    // 카테고리만 해당 create

    default void remove(String name){}; //카테고리만 해당  delete

    default List<Content> getChild(){
        return Collections.emptyList();
    }



}
