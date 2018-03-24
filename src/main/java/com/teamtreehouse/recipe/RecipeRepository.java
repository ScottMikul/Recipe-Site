package com.teamtreehouse.recipe;


import org.springframework.data.repository.CrudRepository;

/**
 * Created by scott on 3/23/2018.
 */
public interface RecipeRepository  extends CrudRepository<Recipe,Long> {
}
