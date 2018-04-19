package com.teamtreehouse.core;


import com.teamtreehouse.UserRecipes.UserRecipesProfile;
import com.teamtreehouse.UserRecipes.UserRecipesRepository;
import com.teamtreehouse.favorite.Favorite;
import com.teamtreehouse.favorite.FavoriteRepository;
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

    @Autowired
    FavoriteRepository favoriteRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {


        //creating some usedrs

        User user = new User("scotty",new String[]{"ROLE_USER"} , "");
        User user2 = new User("bob",new String[]{"ROLE_USER","ROLE_ADMIN"} , "password");
        UserRecipesProfile profile = new UserRecipesProfile();
        user.setProfile(profile);
        users.save(user2);

        users.save(user);

        profile.setRecipeList(new ArrayList<>());

        Recipe recipe = new Recipe();
        recipe.setCookTime(1);
        recipe.setName("broccoli cheese");
        recipe.setDescription("it's some broccoli with cheese. ");
        recipe.setPrepTime(5);
        recipe.setCategory(CategoryConstants.DINNER);
        recipe.setUserRecipesProfile(profile);
        recipe.setPhotoUrl("https://img.sndimg.com/food/image/upload/w_707,h_398,c_fill,fl_progressive,q_80/v1/img/recipes/26/96/46/pic3qHZVN.jpg");
        mRecipeRepository.save(recipe);

        Recipe recipe2 = new Recipe();
        recipe2.setCookTime(2);
        recipe2.setName("ramen");
        recipe2.setDescription("a type of noodles ");
        recipe2.setPrepTime(5);
        recipe2.setCategory(CategoryConstants.DINNER);
        recipe2.setUserRecipesProfile(profile);
        mRecipeRepository.save(recipe2);

        Recipe recipe3 = new Recipe();
        recipe3.setCookTime(2);
        recipe3.setName("tofu");
        recipe3.setDescription("vegetarian gunk stuff ");
        recipe3.setPrepTime(5);
        recipe3.setCategory(CategoryConstants.DINNER);
        mRecipeRepository.save(recipe3);

        Recipe recipe4 = new Recipe();
        recipe4.setCookTime(2);
        recipe4.setName("chocolate");
        recipe4.setDescription("chocolate!!!!!!! ");
        recipe4.setPrepTime(5);
        recipe4.setCategory(CategoryConstants.DINNER);
        mRecipeRepository.save(recipe4);

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
        Favorite favorite = new Favorite();
        favorite.setRecipe(recipe);
        favorite.setProfile(profile);
        favoriteRepository.save(favorite);


        Favorite favorite2 = new Favorite();
        favorite2.setRecipe(recipe4);
        favorite2.setProfile(profile);
        favoriteRepository.save(favorite2);

        List<Favorite> favorites = new ArrayList<>();
        favorites.add(favorite);
        profile.setFavoriteRecipesList(favorites);
        profile.setUser(user);
        mUserRecipesRepository.save(profile);

    }
}