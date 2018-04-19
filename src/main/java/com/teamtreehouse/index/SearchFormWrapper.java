package com.teamtreehouse.index;

public class SearchFormWrapper {
    String category;
    String searchstring;
    boolean typed;

    public boolean isTyped() {
        return typed;
    }

    public void setTyped(boolean typed) {
        this.typed = typed;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSearchstring() {
        return searchstring;
    }

    public void setSearchstring(String searchstring) {
        this.searchstring = searchstring;
    }
}
