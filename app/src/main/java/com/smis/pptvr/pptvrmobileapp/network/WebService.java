package com.smis.pptvr.pptvrmobileapp.network;


import com.smis.pptvr.pptvrmobileapp.pojo.Projects;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by gomchik on 25/12/16.
 */

public interface WebService {

    @GET("/RetrofitExample/books.json")
    public void getProjects(Call<List<Projects>> response);

}
