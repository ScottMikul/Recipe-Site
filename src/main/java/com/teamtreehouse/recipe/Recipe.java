package com.teamtreehouse.recipe;


import com.teamtreehouse.UserRecipes.UserRecipesProfile;
import com.teamtreehouse.core.BaseEntity;
import com.teamtreehouse.ingredient.Ingredient;
import com.teamtreehouse.instruction.Instruction;

import javax.persistence.*;
import java.util.List;

/**
 * Created by scott on 3/21/2018.
 */
@Entity
public class Recipe extends BaseEntity{
    @ManyToOne
    public UserRecipesProfile mUserRecipesProfile;
    private String name;
    private int prepTime;
    private int cookTime;
    private String category;
    @ManyToMany(mappedBy = "RecipesWithIngredient",cascade = CascadeType.ALL)
    public List<Ingredient> mIngredientsList;
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    public List<Instruction> instructions;

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
}
