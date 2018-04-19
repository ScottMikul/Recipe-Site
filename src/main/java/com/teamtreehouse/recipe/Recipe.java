package com.teamtreehouse.recipe;


import com.teamtreehouse.UserRecipes.UserRecipesProfile;
import com.teamtreehouse.core.BaseEntity;
import com.teamtreehouse.favorite.Favorite;
import com.teamtreehouse.ingredient.Ingredient;
import com.teamtreehouse.instruction.Instruction;
import com.teamtreehouse.user.User;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * Created by scott on 3/21/2018.
 */
@Entity
public class Recipe extends BaseEntity{
    @ManyToOne
    public UserRecipesProfile mUserRecipesProfile;
    private String name;
    private String description;
    private int prepTime;
    private int cookTime;
    private String category;
    private String photoUrl;
    private boolean addStep;
    private boolean addIngredient;

    @ManyToMany(mappedBy = "RecipesWithIngredient",cascade = CascadeType.ALL)
    public List<Ingredient> mIngredientsList;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    public List<Instruction> instructions;



    //detailctr to see if the current user is amongst the favorites in this list
    @OneToMany(mappedBy= "recipe", cascade = CascadeType.ALL)
    List<Favorite>FavoriteUsers;


    public List<Favorite> getFavoriteUsers() {
        return FavoriteUsers;
    }

    public void setFavoriteUsers(List<Favorite> favoriteUsers) {
        FavoriteUsers = favoriteUsers;
    }

    public boolean isAddStep() {
        return addStep;
    }

    public void setAddStep(boolean addStep) {
        this.addStep = addStep;
    }

    public boolean isAddIngredient() {
        return addIngredient;
    }

    public void setAddIngredient(boolean addIngredient) {
        this.addIngredient = addIngredient;
    }




    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @Override
    public int hashCode() {

        return Objects.hash(mUserRecipesProfile, name, prepTime, cookTime, category, mIngredientsList, instructions);
    }

    @Override
    public boolean equals(Object obj) {

        return super.equals(obj);
    }


    public Recipe() {

    }

    public UserRecipesProfile getUserRecipesProfile() {
        return mUserRecipesProfile;
    }

    public void setUserRecipesProfile(UserRecipesProfile userRecipesProfile) {
        mUserRecipesProfile = userRecipesProfile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }

    public int getCookTime() {
        return cookTime;
    }

    public void setCookTime(int cookTime) {
        this.cookTime = cookTime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Ingredient> getIngredientsList() {
        return mIngredientsList;
    }

    public void setIngredientsList(List<Ingredient> ingredientsList) {
        mIngredientsList = ingredientsList;
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<Instruction> instructions) {
        this.instructions = instructions;
    }


    public UserRecipesProfile getmUserRecipesProfile() {
        return mUserRecipesProfile;
    }

    public void setmUserRecipesProfile(UserRecipesProfile mUserRecipesProfile) {
        this.mUserRecipesProfile = mUserRecipesProfile;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public List<Ingredient> getmIngredientsList() {
        return mIngredientsList;
    }

    public void setmIngredientsList(List<Ingredient> mIngredientsList) {
        this.mIngredientsList = mIngredientsList;
    }

}
