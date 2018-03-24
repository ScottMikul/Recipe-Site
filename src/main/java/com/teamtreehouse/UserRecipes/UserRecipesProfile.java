package com.teamtreehouse.UserRecipes;

import com.teamtreehouse.core.BaseEntity;
import com.teamtreehouse.recipe.Recipe;
import com.teamtreehouse.user.User;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Contains
 * Created by scott on 3/22/2018.
 */
@Entity
public class UserRecipesProfile extends BaseEntity {
    @ManyToOne
    User mUser;

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

    @OneToMany
    List<Recipe> mRecipeList;

    public UserRecipesProfile() {
    }
}
