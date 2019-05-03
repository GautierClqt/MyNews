package com.cliquet.gautier.mynews.Utils;

import com.cliquet.gautier.mynews.Models.NYTopStories;
import com.cliquet.gautier.mynews.Models.Result;


import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NYtimesService {
    @GET("{section}.json?api-key=WftprIljSPh7y8Le0ZmsFjAZAUA9fkkz")
    Call<List<Result>> getFollowing(@Path("section") String section);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/svc/topstories/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}

