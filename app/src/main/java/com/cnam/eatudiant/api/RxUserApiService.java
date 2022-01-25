package com.cnam.eatudiant.api;

import com.cnam.eatudiant.data.FakeUser;
import com.cnam.eatudiant.data.User;
import com.cnam.eatudiant.data.UserAuth;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

public interface RxUserApiService {

    @POST("authentication/login")
    Observable<User> login(@Body UserAuth userAuth);

    @GET("users")
    Observable<List<FakeUser>> getUser();

}
