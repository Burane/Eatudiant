package com.cnam.eatudiant.model;

import android.content.Context;
import android.util.Log;
import com.cnam.eatudiant.api.RxApiClient;
import com.cnam.eatudiant.api.RxRecipesApiService;
import com.cnam.eatudiant.api.RxUserApiService;
import com.cnam.eatudiant.data.Response;
import com.cnam.eatudiant.data.recipe.RecipesResponse;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;
import retrofit2.HttpException;

import java.util.Map;

public class RecipeModel {


    private Map<String, Consumer<Object>> consumer;

    private RxUserApiService rxUserApiService;
    private Context context;


    public RecipeModel(Map<String, Consumer<Object>> consumer, Context context) {
        this.consumer = consumer;
        this.context = context;
    }


    public void getRecipes() {

        RxRecipesApiService rxRecipesApiService = RxApiClient.getRxRecipesApiService(context);

        Observable<Response<RecipesResponse>> recipesResponse = rxRecipesApiService.getRecipes();

        recipesResponse
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                res -> {
                    consumer.get("recipesResponse").accept(res.getDatas().getRecipes());
                },
                throwable -> {
                    if (throwable instanceof HttpException) {
                        HttpException err = (HttpException) throwable;
                        consumer.get("login_failed").accept(err.message());
                    }
                });

    }
}
