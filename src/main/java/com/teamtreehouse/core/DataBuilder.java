package com.teamtreehouse.core;


import com.teamtreehouse.UserRecipes.UserRecipesProfile;
import com.teamtreehouse.UserRecipes.UserRecipesRepository;
import com.teamtreehouse.ingredient.Ingredient;
import com.teamtreehouse.ingredient.IngredientRepository;
import com.teamtreehouse.instruction.Instruction;
import com.teamtreehouse.instruction.InstructionRepository;
import com.teamtreehouse.recipe.CategoryConstants;
import com.teamtreehouse.recipe.Recipe;
import com.teamtreehouse.recipe.RecipeRepository;
import com.teamtreehouse.user.User;
import com.teamtreehouse.user.UserRepository;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by scott on 6/19/2017.
 */
@Component
public class DataBuilder implements ApplicationRunner {

    @Autowired
    UserRepository users;

    @Autowired
    UserRecipesRepository mUserRecipesRepository;

    @Autowired
    RecipeRepository mRecipeRepository;

    @Autowired
    IngredientRepository mIngredientRepository;

    @Autowired
    InstructionRepository mInstructionRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {


        //creating some usedrs

        User user = new User("lel",new String[]{"ROLE_USER"} , "password");
        User user2 = new User("blaah",new String[]{"ROLE_USER","ROLE_ADMIN"} , "password");
        UserRecipesProfile profile = new UserRecipesProfile();
        user.setProfile(profile);
        users.save(user2);

        users.save(user);

        profile.setRecipeList(new ArrayList<>());

        Recipe recipe = new Recipe();
        recipe.setCookTime(1);
        recipe.setName("broccoli cheese");
        recipe.setPrepTime(5);
        recipe.setCategory(CategoryConstants.DINNER);
        recipe.setUserRecipesProfile(profile);
        mRecipeRepository.save(recipe);

        Ingredient ingredient = new Ingredient();
        ingredient.setQuantity(1);
        ingredient.setCondition("good");
        ingredient.setName("broccili");

        ArrayList<Recipe> recipes = new ArrayList<>();
        recipes.add(recipe);
        ingredient.setRecipesWithIngredient(recipes);
        mIngredientRepository.save(ingredient);

        Instruction instruction = new Instruction();
        instruction.setRecipe(recipe);
        instruction.setDetail("cook it good, cook it real good");
        Instruction instruction2 = new Instruction();
        instruction2.setDetail("cook it really really good.");
        instruction2.setRecipe(recipe);
        mInstructionRepository.save(instruction);
        mInstructionRepository.save(instruction2);

        profile.setRecipeList(recipes);
        mUserRecipesRepository.save(profile);

    }
}