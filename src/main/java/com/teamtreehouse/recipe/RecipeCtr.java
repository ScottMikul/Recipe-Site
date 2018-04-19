package com.teamtreehouse.recipe;

import com.teamtreehouse.UserRecipes.UserRecipesProfile;
import com.teamtreehouse.core.BaseUserController;
import com.teamtreehouse.ingredient.Ingredient;
import com.teamtreehouse.ingredient.IngredientRepository;
import com.teamtreehouse.instruction.Instruction;
import com.teamtreehouse.instruction.InstructionRepository;
import com.teamtreehouse.recipe.Recipe;
import com.teamtreehouse.recipe.RecipeRepository;
import com.teamtreehouse.user.User;
import com.teamtreehouse.user.UserRepository;
import com.teamtreehouse.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;


@Controller
public class RecipeCtr extends BaseUserController{
    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    UserRepository repository;

    @Autowired
    IngredientRepository ingRepo;

    @Autowired
    InstructionRepository instRepo;


    @RequestMapping(value="/edit/{id}", method= RequestMethod.GET)
    public String editRecipe(@PathVariable Long id,Model model){
        String username = getSessionUsername();
        model.addAttribute("username", username);
        //figure out if the request is coming from an owner of recipe
        User user =repository.findByName(username);
        Recipe recipe = recipeRepository.findOne(id);
        if(!user.getProfile().ownsRecipe(recipe)){
            return "redirect:/index";
        }

        model.addAttribute("recipe",recipe);



        return "edit";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String makeRecipChanges(@RequestParam(value="action",required=false)String  action,@PathVariable long id,@Valid Recipe editRecipe , BindingResult result, RedirectAttributes redirectAttributes, Model model){
        String username = getSessionUsername();
       //figure out if the request is coming from an owner of recipe
        User user =repository.findByName(username);
        Recipe recipe = recipeRepository.findOne(id);
        if(!user.getProfile().ownsRecipe(recipe)|| !(action==null)){
            return "redirect:/index";
        }

        recipe.setPhotoUrl(editRecipe.getPhotoUrl());
        recipe.setName(editRecipe.getName());
        recipe.setDescription(editRecipe.getDescription());
        recipe.setCategory(editRecipe.getCategory());
        recipe.setCookTime(editRecipe.getCookTime());
        recipe.setPrepTime(editRecipe.getPrepTime());


        if(editRecipe.isAddIngredient()){
            if(editRecipe.getIngredientsList()==null){
                editRecipe.setIngredientsList(new ArrayList<>());
            }
            editRecipe.mIngredientsList.add(new Ingredient());
            recipe.setIngredientsList(editRecipe.getIngredientsList());
            recipe.setInstructions(editRecipe.getInstructions());

            model.addAttribute("recipe", recipe);

            recipe.setAddIngredient(false);
            return "edit";
        }
        if(editRecipe.isAddStep()){
            if(editRecipe.getInstructions()==null){
                editRecipe.setInstructions(new ArrayList<>());
            }

            editRecipe.instructions.add(new Instruction());
            recipe.setIngredientsList(editRecipe.getIngredientsList());
            recipe.setInstructions(editRecipe.getInstructions());

            recipe.setAddStep(false);
            model.addAttribute("recipe", recipe);

            return "edit";
        }

        //make a list of the added ingredients for saving (if any)
        ArrayList<Ingredient> ingredientsToSave = new ArrayList<>();
        if(recipe.getIngredientsList()!=null&& editRecipe.getIngredientsList()!=null)
        for (int i=recipe.getIngredientsList().size();i<editRecipe.getIngredientsList().size();i++ ){
            Ingredient ingredient = editRecipe.getIngredientsList().get(i);
            ArrayList<Recipe> list = new ArrayList<>();
            list.add(recipe);
            ingredient.setRecipesWithIngredient(list);
            ingredientsToSave.add(ingredient);
        }

        //make a list of the added instructions for saving (if any)
        ArrayList<Instruction> instructionsToSave = new ArrayList<>();
        if(recipe.getInstructions()!=null&& editRecipe.getInstructions()!=null)
        for (int i=recipe.getInstructions().size();i<editRecipe.getInstructions().size();i++ ){
            Instruction instruction = editRecipe.getInstructions().get(i);
            instruction.setRecipe(recipe);
            instructionsToSave.add(instruction);

        }

        recipe.setIngredientsList(editRecipe.getIngredientsList());
        recipe.setInstructions(editRecipe.getInstructions());

        //save ingredients
        if(ingredientsToSave!=null)
            ingredientsToSave.forEach(a -> ingRepo.save(a));

        //save instructions
        if(instructionsToSave!=null)
            instructionsToSave.forEach(a -> instRepo.save(a));

        recipeRepository.save(recipe);

        return "redirect:/profile";
    }

    @RequestMapping(value = "/add", method= RequestMethod.GET)
    public String addRecipe(){
        String username = getSessionUsername();
        User user = repository.findByName(username);
        UserRecipesProfile profile = user.getProfile();


        Recipe recipe = new Recipe();
        recipe.setmUserRecipesProfile(profile);
        recipeRepository.save(recipe);

        profile.getRecipeList().add(recipe);
        repository.save(user);

        return "redirect:/edit/"+recipe.getId();
    }


    @RequestMapping(value="/delete/{id}",method=RequestMethod.GET)
    public String deleteRecipe(@PathVariable Long id){
        String username = getSessionUsername();
        User user = repository.findByName(username);
        if(user.getProfile().ownsRecipe(recipeRepository.findOne(id)))
        recipeRepository.delete(id);
        return "redirect:/index";
    }

}
