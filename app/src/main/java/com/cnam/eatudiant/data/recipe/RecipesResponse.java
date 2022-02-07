package com.cnam.eatudiant.data.recipe;

import com.squareup.moshi.Json;
import lombok.Data;

import java.util.List;

@Data
public class RecipesResponse {

    @Json(name = "nbRecette")
    private int nbRecipes;

    @Json(name = "result")
    private List<Recipe> recipes;
}
