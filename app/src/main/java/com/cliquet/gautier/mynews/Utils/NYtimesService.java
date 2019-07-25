package com.cliquet.gautier.mynews.Utils;

import com.cliquet.gautier.mynews.Models.PojoArticleSearch.PojoArticleSearch;
import com.cliquet.gautier.mynews.Models.PojoMostPopular.PojoMostPopular;
import com.cliquet.gautier.mynews.Models.PojoTopStories.PojoMaster;

import java.io.IOException;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface NYtimesService {
    String apiKey = "WftprIljSPh7y8Le0ZmsFjAZAUA9fkkz";

    //add api-key to every url
    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    HttpUrl httpUrl = original.url();

                    HttpUrl newHttpUrl = httpUrl.newBuilder().addQueryParameter("api-key", apiKey).build();

                    Request.Builder requestBuilder = original.newBuilder().url(newHttpUrl);

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            })
            .build();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/svc/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    //Top Stories call
    @GET("topstories/v2/{section}.json")
    Call<PojoMaster> getTopStories(@Path("section") String section);

    //Search Article API call
    @GET("search/v2/articlesearch.json")
    Call<PojoArticleSearch> getArticleSearch(@QueryMap Map<String, String> searchQueries);

    //Mot Popular call
    @GET("mostpopular/v2/shared/7.json")
    Call<PojoMostPopular> getMostPopular();
}

