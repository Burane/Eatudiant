package com.cnam.eatudiant.model;

import android.content.Context;
import com.cnam.eatudiant.api.RxApiClient;
import com.cnam.eatudiant.api.RxRecipesApiService;
import com.cnam.eatudiant.data.Response;
import com.cnam.eatudiant.data.recipeDetails.FullRecipe;
import com.cnam.eatudiant.data.recipeDetails.StepBody;
import com.cnam.eatudiant.data.recipeDetails.Steps;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;
import retrofit2.HttpException;

import java.util.Map;

public class RecipeDetailsModel {


    private Map<String, Consumer<Object>> consumer;

    private Context context;


    public RecipeDetailsModel(Map<String, Consumer<Object>> consumer, Context context) {
        this.consumer = consumer;
        this.context = context;
    }

    public void getFullRecipe(int id) {

        RxRecipesApiService rxRecipesApiService = RxApiClient.getRxRecipesApiService(context);

        Observable<Response<FullRecipe>> fullRecipeResponse = rxRecipesApiService.getFullRecipe(new StepBody(id));

        fullRecipeResponse
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        res -> {
                            consumer.get("fullRecipeResponse").accept(res.getDatas());
                        },
                        throwable -> {
                            if (throwable instanceof HttpException) {
                                HttpException err = (HttpException) throwable;
                                consumer.get("error").accept(err.message());
                            }
                        });
    }

    public void getRecipeSteps(int id) {
        RxRecipesApiService rxRecipesApiService = RxApiClient.getRxRecipesApiService(context);

        Observable<Response<Steps>> RecipeStepsResponse = rxRecipesApiService.getRecipeSteps(new StepBody(id));

        RecipeStepsResponse
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        res -> {
                            consumer.get("recipeStepsResponse").accept(res.getDatas());
                        },
                        throwable -> {
                            if (throwable instanceof HttpException) {
                                HttpException err = (HttpException) throwable;
                                consumer.get("error").accept(err.message());
                            }
                        });
    }
}
