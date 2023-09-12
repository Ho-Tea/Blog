package dev.be.blog.vo;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rename rename = (Rename) o;
        return Objects.equals(oldName, rename.oldName) && Objects.equals(newName, rename.newName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(oldName, newName);
    }
}
