package com.cnam.eatudiant.data.recipeDetails;

import com.squareup.moshi.Json;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class Fullrecipe {

    private String name;

    private int time;

    private int difficulty;

    @Json(name = "price_range")
    private int priceRange;

    @Json(name = "date_created")
    private Date creationDate;

    @Json(name = "verified")
    private int verifiedState;

    private String picture;

    private String creator;

    @Json(name = "listeUstensiles")
    private List<Requirement> cookware;

    @Json(name = "listeIngredients")
    private List<Requirement> ingredients;
}
