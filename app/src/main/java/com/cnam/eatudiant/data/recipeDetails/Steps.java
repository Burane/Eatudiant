package com.cnam.eatudiant.data.recipeDetails;

import com.squareup.moshi.Json;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Steps {

    @Json(name = "result")
    private List<Step> steps;
}
