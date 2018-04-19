package com.teamtreehouse.favorite;

import com.teamtreehouse.UserRecipes.UserRecipesProfile;
import com.teamtreehouse.core.BaseEntity;
import com.teamtreehouse.recipe.Recipe;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Favorite extends BaseEntity{
    @ManyToOne
    Recipe recipe;

    @ManyToOne
    UserRecipesProfile profile;

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public UserRecipesProfile getProfile() {
        return profile;
    }

    public void setProfile(UserRecipesProfile profile) {
        this.profile = profile;
    }



}
