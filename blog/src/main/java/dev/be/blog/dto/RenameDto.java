package dev.be.blog.dto;

public class RenameDto {
    public String oldName;
    public String newName;

    public RenameDto(String oldName, String newName) {
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
