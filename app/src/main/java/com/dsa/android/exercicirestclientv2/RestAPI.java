package com.dsa.android.exercicirestclientv2;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RestAPI {

    @GET("tracks")
    Call<LlistaTracks> obtenerListaTracks();
}
