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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

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


        Recipe recipe = new Recipe();
        recipe.setName("Cheese broccolli");
        recipe.setCategory(CategoryConstants.DINNER);
        recipe.setCookTime(5);
        recipe.setPrepTime(1);
        recipe.setUserRecipesProfile(user.getProfile());


        List<Recipe> recipeList =new ArrayList<>();
        recipeList.add(recipe);


        Ingredient ingredient = new Ingredient();
        ingredient.setName("Brocolli");
        ingredient.setCondition("cheesy");
        ingredient.setQuantity(1);
        mIngredientRepository.save(ingredient);

        List<Ingredient> ingredientList = new ArrayList<>();
        ingredientList.add(ingredient);
        recipe.setIngredientsList(ingredientList);

        List<Instruction> instructionList = new ArrayList<>();
        Instruction instruction = new Instruction("Cook the broccli for about 5 minutes or until soft");
        Instruction instruction2 = new Instruction("Cook brocoli with cheese over it for about 1 more minute or until cheese melts");
        mInstructionRepository.save(instruction);
        mInstructionRepository.save(instruction2);

        instructionList.add(instruction);
        instructionList.add(instruction2);
        recipe.setInstructions(instructionList);

        mRecipeRepository.save(recipe);

        profile.setRecipeList(recipeList);

        mUserRecipesRepository.save(profile);

        users.save(user);


    }
}