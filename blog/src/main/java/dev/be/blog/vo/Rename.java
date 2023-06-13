package dev.be.blog.vo;

public class Rename {
    private String oldName;
    private String newName;

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
