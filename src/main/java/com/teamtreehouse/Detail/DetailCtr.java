package com.teamtreehouse.Detail;

import com.teamtreehouse.core.BaseUserController;
import com.teamtreehouse.favorite.Favorite;
import com.teamtreehouse.ingredient.Ingredient;
import com.teamtreehouse.instruction.Instruction;
import com.teamtreehouse.recipe.Recipe;
import com.teamtreehouse.recipe.RecipeRepository;
import com.teamtreehouse.user.User;
import com.teamtreehouse.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
public class DetailCtr extends BaseUserController{
    @Autowired
    RecipeRepository recipeRepository;


    @Autowired
    UserRepository userRepository;
    @RequestMapping("/detail/{id}")
    public String getRecipeDetails(@PathVariable Long id,  Model model){
        String username = this.getSessionUsername();
        model.addAttribute("username", username);
        //get recipe with the provided id
        Recipe recipe = recipeRepository.findOne(id);
        model.addAttribute("recipe", recipe);

        ArrayList<Ingredient> ingredients = new ArrayList<>(recipe.getIngredientsList());

        model.addAttribute("ingredients" , ingredients);

        ArrayList<Instruction> instructions = new ArrayList<>(recipe.getInstructions());
        model.addAttribute("instructions",instructions);

        boolean favorite = false;


        List<Favorite> favorites = recipe.getFavoriteUsers();
        User user = userRepository.findByName(username);
        Long userId = user.getId();

        for(Favorite a : favorites){
            if(a.getProfile().getUser().getId()==userId){
                favorite=true;
                break;
            }
        }

        model.addAttribute("favorite", favorite);

        return "detail";
    }
}
