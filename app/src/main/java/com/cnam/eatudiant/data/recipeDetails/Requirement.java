package com.cnam.eatudiant.data.recipeDetails;

import com.squareup.moshi.Json;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class Requirement {

    @Json(name = "nom")
    private String name;

    @Json(name = "qte")
    private double quantity;
}
