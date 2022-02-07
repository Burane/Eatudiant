package com.cnam.eatudiant.api;

import com.cnam.eatudiant.data.Response;
import com.cnam.eatudiant.data.recipe.RecipesRequest;
import com.cnam.eatudiant.data.recipe.RecipesResponse;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RxRecipesApiService {

    @POST("recette/getListRecette")
    Observable<Response<RecipesResponse>> getRecipes();


}
