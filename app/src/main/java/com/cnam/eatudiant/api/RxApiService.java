package com.cnam.eatudiant.api;

import com.cnam.eatudiant.data.User;
import com.cnam.eatudiant.data.UserAuth;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RxApiService {

    @POST("login")
    Observable<User> login(@Body UserAuth userAuth);

}
