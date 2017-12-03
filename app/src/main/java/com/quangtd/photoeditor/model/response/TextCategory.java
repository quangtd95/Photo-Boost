package com.quangtd.photoeditor.model.response;

public class TextCategory {
    private String name;
    private boolean isSelect;

    public TextCategory(String name) {
        this.name = name;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
