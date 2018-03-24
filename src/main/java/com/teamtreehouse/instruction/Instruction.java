package com.teamtreehouse.instruction;

import com.teamtreehouse.core.BaseEntity;
import com.teamtreehouse.recipe.Recipe;

import javax.persistence.*;

/**
 * Created by scott on 3/22/2018.
 */
@Entity
public class Instruction extends BaseEntity{

   @ManyToOne
    Recipe recipe;
    String detail;


    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Instruction() {

    }

    public Instruction(String detail) {
        this.detail = detail;
    }
}
