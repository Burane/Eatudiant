package com.cnam.eatudiant.data.recipeDetails;

import com.squareup.moshi.Json;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Step {
    private int id;

    @Json(name = "id_recipe")
    private int recipeId;

    @Json(name = "step_number")
    private int stepNumber;

    @Json(name = "text")
    private String instructions;
}
