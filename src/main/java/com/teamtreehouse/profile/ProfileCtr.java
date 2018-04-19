package com.teamtreehouse.profile;

import com.teamtreehouse.core.BaseUserController;
import com.teamtreehouse.favorite.Favorite;
import com.teamtreehouse.index.AllRecipeWrapper;
import com.teamtreehouse.recipe.Recipe;
import com.teamtreehouse.user.User;
import com.teamtreehouse.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProfileCtr extends BaseUserController{

    @Autowired
    UserRepository repository;
    @RequestMapping(value="/profile", method= RequestMethod.GET)
    public String getProfile(Model model){
        String username = getSessionUsername();
        model.addAttribute("username", username);

        //prepare a list of all of the recipes in a wrapper that can tell if owner and if favorited.
        List<AllRecipeWrapper> allRecipes = new ArrayList<>();


        User user = repository.findByName(username);
        List<Favorite> favorites = user.getProfile().getFavoriteRecipesList();

        List<Recipe> recipes = user.getProfile().getRecipeList();

        for (Recipe a:recipes) {
            AllRecipeWrapper temp = new AllRecipeWrapper();
            for(Favorite b: favorites){
                if(a.hashCode()==b.getRecipe().hashCode()){
                    temp.setFavorite(true);
                }
            }
            //if the user profile list contains the recipe in it's recipes
            if(user.getProfile().getRecipeList().contains(a)){
                temp.setOwner(true);
            }
            temp.setId(a.getId());
            temp.setName(a.getName());
            allRecipes.add(temp);
        }

        for(Favorite a : favorites){
            AllRecipeWrapper temp = new AllRecipeWrapper();
            boolean alreadyOwned = false;
            for(Recipe b: recipes){
                if(a.getRecipe().hashCode()==b.hashCode()){
                    alreadyOwned = true;
                    break;
                }
            }
            if(alreadyOwned) {
                continue;
            }
            temp.setFavorite(true);
            temp.setId(a.getRecipe().getId());
            temp.setName(a.getRecipe().getName());
            allRecipes.add(temp);
        }
        model.addAttribute("allRecipes", allRecipes);
        return "profile";
    }

}
