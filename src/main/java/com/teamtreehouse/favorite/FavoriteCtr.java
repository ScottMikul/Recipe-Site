package com.teamtreehouse.favorite;


import com.teamtreehouse.UserRecipes.UserRecipesProfile;
import com.teamtreehouse.core.BaseUserController;
import com.teamtreehouse.recipe.Recipe;
import com.teamtreehouse.recipe.RecipeRepository;
import com.teamtreehouse.user.User;
import com.teamtreehouse.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class FavoriteCtr extends BaseUserController{
    @Autowired
    FavoriteRepository favoriteRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RecipeRepository recipeRepository;

    @RequestMapping(value="/favorite/{id}", method = RequestMethod.GET)
    public String toggleFavorite(@PathVariable Long id)
    {
        String username = getSessionUsername();
        User user = userRepository.findByName(username);
        UserRecipesProfile urp = user.getProfile();

        Recipe recipe = recipeRepository.findOne(id);

        boolean favorited = false;
        long favoritedId = 0;
        for(Favorite a: urp.getFavoriteRecipesList()){
            if(a.getRecipe().getId()==id){
                favorited = true;
                favoritedId = a.getId();
            }
        }

        //if not favorited
        if(!favorited) {
            Favorite favorite = new Favorite();
            favorite.setProfile(urp);
            favorite.setRecipe(recipe);
            favoriteRepository.save(favorite);
        }
        //if favorited already
        else{
            favoriteRepository.delete(favoritedId);
        }


        return "redirect:/detail/"+id;
    }
}
