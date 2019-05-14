package com.cliquet.gautier.mynews.Utils;

import android.support.annotation.Nullable;

import com.cliquet.gautier.mynews.Models.NYTopStories;
import com.cliquet.gautier.mynews.Models.Result;

import java.lang.ref.WeakReference;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NYtimesCalls {

    //Create a callback
    public interface Callbacks {
        void onResponse(NYTopStories body);
        void onFailure();
    }

    //Create method start fetching articles
    public static void fetchArticle(Callbacks callbacks, String section) {

        //Create a weak reference to callback and avoid memory leaks
        final WeakReference<Callbacks> callbacksWeakReference = new WeakReference<>(callbacks);

        //Get a Retrofit instance and its related endpoints
        NYtimesService nYTimesService = NYtimesService.retrofit.create(NYtimesService.class);

        //Create the call on the New York Times API
        Call<NYTopStories> call = nYTimesService.getFollowing(section);
        //start the call
        call.enqueue(new Callback<NYTopStories>() {
            @Override
            public void onResponse(Call<NYTopStories> call, Response<NYTopStories> response) {
                if (callbacksWeakReference.get() != null) callbacksWeakReference.get().onResponse(response.body());
            }

            @Override
            public void onFailure(Call<NYTopStories> call, Throwable t) {
                if (callbacksWeakReference.get() != null) callbacksWeakReference.get().onFailure();
                t.printStackTrace();
            }
        });
    }
}
