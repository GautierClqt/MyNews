package com.cliquet.gautier.mynews.Utils;

import com.cliquet.gautier.mynews.Models.PojoTopStories;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NYtimesCalls {

    //Create a callback
    public interface Callbacks {
        void onResponse(PojoTopStories body);
        void onFailure();
    }

    //Create method start fetching articles
    public static void fetchArticle(Callbacks callbacks, String section) {

        //Create a weak reference to callback and avoid memory leaks
        final WeakReference<Callbacks> callbacksWeakReference = new WeakReference<>(callbacks);

        //Get a Retrofit instance and its related endpoints
        NYtimesService nYTimesService = NYtimesService.retrofit.create(NYtimesService.class);

        //Create the call on the New York Times API
        Call<PojoTopStories> call = nYTimesService.getTopStories(section);
        //start the call
        call.enqueue(new Callback<PojoTopStories>() {
            @Override
            public void onResponse(Call<PojoTopStories> call, Response<PojoTopStories> response) {
                if (callbacksWeakReference.get() != null) callbacksWeakReference.get().onResponse(response.body());
            }

            @Override
            public void onFailure(Call<PojoTopStories> call, Throwable t) {
                if (callbacksWeakReference.get() != null) callbacksWeakReference.get().onFailure();
                t.printStackTrace();
            }
        });
    }

//    public static void getSearchedArticles(Callback callbacks, Map<String, String> searchQueries) {
//
//        final WeakReference<Callbacks> callbacksWeakReference = new WeakReference<>(callbacks);
//        NYtimesService mNYtimesService = NYtimesService.retrofit.create(NYtimesService.class);
//        Call<PojoTopStories> call = mNYtimesService.getTopStories(searchQueries);
//
//
//    }
}
