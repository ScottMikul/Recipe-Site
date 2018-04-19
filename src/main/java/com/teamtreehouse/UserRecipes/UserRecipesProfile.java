package com.teamtreehouse.UserRecipes;

import com.teamtreehouse.core.BaseEntity;
import com.teamtreehouse.favorite.Favorite;
import com.teamtreehouse.recipe.Recipe;
import com.teamtreehouse.user.User;

import javax.persistence.*;
import java.util.List;

/**
 * Contains
 * Created by scott on 3/22/2018.
 */
@Entity
public class UserRecipesProfile extends BaseEntity {
    @OneToOne(mappedBy = "mProfile")
    User mUser;
    @OneToMany(mappedBy = "mUserRecipesProfile")
    List<Recipe> mRecipeList;
    @OneToMany(mappedBy= "profile")
    List<Favorite> favorites;

    public List<Favorite> getFavoriteRecipesList() {
        return favorites;
    }

    public void setFavoriteRecipesList(List<Favorite> FavoriteRecipesList) {
        this.favorites = FavoriteRecipesList;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public List<Recipe> getRecipeList() {
        return mRecipeList;
    }

    public void setRecipeList(List<Recipe> recipeList) {
        mRecipeList = recipeList;
    }



    public UserRecipesProfile() {
    }

    public Boolean ownsRecipe(Recipe recipe){
        return mRecipeList.contains(recipe);
    }
}
