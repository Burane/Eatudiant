package com.cnam.eatudiant.data.recipe;

import com.squareup.moshi.Json;
import lombok.Data;

import java.util.Date;

@Data
public class Recipe {

    private int id;

    private String name;

    @Json(name = "time")
    private int duration;

    private int difficulty;

    @Json(name = "creePar")
    private int author;

    @Json(name = "date_created")
    private Date creationDate;

    @Json(name = "verified")
    private int verifiedState;

    @Json(name = "price_range")
    private int price;

    private int quantity;
}
