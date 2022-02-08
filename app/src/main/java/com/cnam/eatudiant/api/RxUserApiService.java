package com.cnam.eatudiant.api;

import com.cnam.eatudiant.data.*;
import com.cnam.eatudiant.data.auth.User;
import com.cnam.eatudiant.data.auth.UserAuth;
import com.cnam.eatudiant.data.register.RegisterBody;
import com.cnam.eatudiant.data.register.RegisterResponse;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RxUserApiService {

    @POST("authentication/login")
    Observable<Response<User>> login(@Body UserAuth userAuth);

    @POST("authentication/register")
    Observable<RegisterResponse> register(@Body RegisterBody registerBody);

}
