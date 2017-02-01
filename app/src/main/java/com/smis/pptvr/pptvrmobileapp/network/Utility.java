package com.smis.pptvr.pptvrmobileapp.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Vikas Kumar on 01-02-2017.
 */

public class Utility {

    public static WebService createAppWebService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Endpoints.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WebService service = retrofit.create(WebService.class);
        return service;
    }
}
