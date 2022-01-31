package com.cnam.eatudiant.model;

import android.content.Context;
import android.util.Log;
import com.cnam.eatudiant.api.RxApiClient;
import com.cnam.eatudiant.api.RxRecipesApiService;
import com.cnam.eatudiant.api.RxUserApiService;
import com.cnam.eatudiant.data.Response;
import com.cnam.eatudiant.data.User;
import com.cnam.eatudiant.data.UserAuth;
import com.cnam.eatudiant.data.recipe.RecipesRequest;
import com.cnam.eatudiant.data.recipe.RecipesResponse;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;
import lombok.Setter;
import lombok.SneakyThrows;
import retrofit2.HttpException;

import java.util.Map;

public class HomeModel {


    private Map<String, Consumer<Object>> consumer;

    private RxUserApiService rxUserApiService;
    private Context context;


    public HomeModel(Map<String, Consumer<Object>> consumer, Context context) {
        this.consumer = consumer;
        this.context = context;
    }


    public void getRecipes() {

        RxRecipesApiService rxRecipesApiService = RxApiClient.getRxRecipesApiService(context);

        Observable<Response<RecipesResponse>> recipesResponse = rxRecipesApiService.getRecipes(new RecipesRequest(0));

        recipesResponse.subscribe(
                res -> {
                    Log.i("eatudiant_debug", res.getDatas().toString());
                    consumer.get("recipesResponse").accept(res.getDatas().getRecipes());
                },
                throwable -> {
                    if (throwable instanceof HttpException) {
                        HttpException err = (HttpException) throwable;
                        Log.i("eatudiant_debug", err.message());
                        consumer.get("login_failed").accept(err.message());
                    }
                });

    }
}
