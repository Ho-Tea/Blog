package dev.be.blog.dto;

public class Rename {
    public String oldName;
    public String newName;

    public Rename(String oldName, String newName) {
        this.oldName = oldName;
        this.newName = newName;
    }

    public String getOldName() {
        return oldName;
    }

    public String getNewName() {
        return newName;
    }
}
