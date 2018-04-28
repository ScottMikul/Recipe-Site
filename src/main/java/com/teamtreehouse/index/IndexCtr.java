package com.teamtreehouse.index;

import com.teamtreehouse.UserRecipes.UserRecipesProfile;
import com.teamtreehouse.core.BaseEntity;
import com.teamtreehouse.core.BaseUserController;
import com.teamtreehouse.favorite.Favorite;
import com.teamtreehouse.recipe.Recipe;
import com.teamtreehouse.recipe.RecipeRepository;
import com.teamtreehouse.user.User;
import com.teamtreehouse.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Index is a a page that is where the user will land showing:
 * 1) the list of all recipes, which one are favorited
 * 2) a form that allows the user to add a recipe
 * 3) each item in the list has the option to edit
 * Created by scott on 1/27/2018.
 */
@Controller
public class IndexCtr extends BaseUserController {
    @Autowired
    UserRepository mUserRepository;


    @Autowired
    RecipeRepository recipeRepository;

    @RequestMapping("/")
    public String redirectToIndex(){
        return "redirect:/index";
    }

    @RequestMapping("/index")
    public String loginPage(Model model, RedirectAttributes redirectAttributes) {

        //get username from session.
        String username = getSessionUsername();
        model.addAttribute("username", username);

        SearchFormWrapper sfw = new SearchFormWrapper();
        if(model.containsAttribute("searchformFlashAttribute")) {
            sfw = (SearchFormWrapper) model.asMap().get("searchformFlashAttribute");
            model.addAttribute("searchform", sfw);
        }

        //prepare a list of all of the recipes in a wrapper that can tell if owner and if favorited.
        List<AllRecipeWrapper> allRecipes = new ArrayList<>();
        Iterable<Recipe> iterable = recipeRepository.findAll();

        User user = mUserRepository.findByName(username);
        List<Favorite> favorites =new ArrayList<>();
        if(user.getProfile().getFavoriteRecipesList()!=null){
            favorites = user.getProfile().getFavoriteRecipesList();
        }


        for (Recipe a:iterable) {
            if(sfw.getCategory()!=null&& sfw.getSearchstring()!=null){
                //filter by category
                if (!sfw.getCategory().equalsIgnoreCase("All Categories")) {
                    if (!sfw.getCategory().equalsIgnoreCase(a.getCategory())) {
                        continue;
                    }
                }

                //filter by string
                if(!a.getName().contains(sfw.getSearchstring())){
                    continue;
                }
            }
            AllRecipeWrapper temp = new AllRecipeWrapper();
            for(Favorite b: favorites){
                if(a.hashCode()==b.getRecipe().hashCode()){
                    temp.favorite=true;
                }
            }
            //if the user profile list contains the recipe in it's recipes
            if(user.getProfile().getRecipeList().contains(a)){
                temp.setOwner(true);
            }
            temp.id = a.getId();
            temp.name = a.getName();
            allRecipes.add(temp);
        }

        if(!model.containsAttribute("searchformFlashAttribute")) {
            SearchFormWrapper se = new SearchFormWrapper();
            se.setCategory("All Categories");
            model.addAttribute("searchform", se);
        }
        model.addAttribute("allRecipes", allRecipes);

        return "index";
    }


    @RequestMapping(value="/filter", method = RequestMethod.POST)
    public String filterResults(SearchFormWrapper se, BindingResult result, RedirectAttributes redirectAttributes){


        redirectAttributes.addAttribute("searchformAttribute").addFlashAttribute("searchformFlashAttribute", se);
        return "redirect:/index";
    }
}

