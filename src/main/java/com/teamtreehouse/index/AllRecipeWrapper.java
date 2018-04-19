package com.teamtreehouse.index;

public class AllRecipeWrapper{
    boolean favorite;
    boolean owner;
    long id;
    String name;

    public boolean isOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

    public AllRecipeWrapper() {
        this.favorite=  false;

    }

    public AllRecipeWrapper(boolean isFavorite, long id, String name) {
        this.favorite = isFavorite;
        this.id = id;
        this.name = name;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public boolean getFavorite(){
        return favorite;
    }
    public void setFavorite(boolean mfavorite) {
        favorite = mfavorite;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}