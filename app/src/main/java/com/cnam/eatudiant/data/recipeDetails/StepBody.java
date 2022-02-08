package com.cnam.eatudiant.data.recipeDetails;

import com.squareup.moshi.Json;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StepBody {

    @Json(name = "idRecette")
    private int recipeId;
}
