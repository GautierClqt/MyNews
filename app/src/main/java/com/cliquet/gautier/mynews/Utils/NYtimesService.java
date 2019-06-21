package com.cliquet.gautier.mynews.Utils;

import com.cliquet.gautier.mynews.Models.PojoTopStories;

import java.io.IOException;

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

    @GET("{section}.json")
    Call<PojoTopStories> getTopStories(@Path("section") String section);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/svc/topstories/v2/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

//    @GET("{}.json")
//    Call<PojoTopStories>
}

