package com.cliquet.gautier.mynews.Utils;

import com.cliquet.gautier.mynews.Models.PojoArticleSearch;
import com.cliquet.gautier.mynews.Models.PojoTopStories;

import java.lang.ref.WeakReference;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NYtimesCalls {

    //Create a callback
    public interface Callbacks {
        void onResponse(PojoTopStories body);
        void onFailure();
    }

    public interface Callbacks2 {
        void onResponse(PojoArticleSearch body);
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


    public static void getSearchedArticles(Callbacks2 callbacks, Map<String, String> searchQueries) {

        final WeakReference<Callbacks2> callbacksWeakReference = new WeakReference<>(callbacks);
        NYtimesService mNYtimesService = NYtimesService.retrofit.create(NYtimesService.class);
        Call<PojoArticleSearch> call = mNYtimesService.getArticleSearch(searchQueries);

        call.enqueue(new Callback<PojoArticleSearch>() {
            @Override
            public void onResponse(Call<PojoArticleSearch> call, Response<PojoArticleSearch> response) {
                if (callbacksWeakReference.get() != null) callbacksWeakReference.get().onResponse(response.body());
            }

            @Override
            public void onFailure(Call<PojoArticleSearch> call, Throwable t) {
                if (callbacksWeakReference.get() != null) callbacksWeakReference.get().onFailure();
                t.printStackTrace();
            }
        });


    }
}