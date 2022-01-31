package com.cnam.eatudiant.data.recipe;

import lombok.Data;
import lombok.NonNull;

@Data
public class RecipesRequest {
    @NonNull
    private int index;
}
