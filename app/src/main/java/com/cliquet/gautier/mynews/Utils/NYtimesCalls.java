package com.cliquet.gautier.mynews.Utils;

import com.cliquet.gautier.mynews.Models.PojoCommon.PojoMaster;

import java.lang.ref.WeakReference;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NYtimesCalls {

    //Create a callback
    public interface Callbacks {
        void onResponse(PojoMaster body);
        void onFailure();
    }

    //Create method start fetching articles
    public static void fetchTopStoriesArticles(Callbacks callbacks, String section, int position) {

        //Create a weak reference to callback and avoid memory leaks
        final WeakReference<Callbacks> callbacksWeakReference = new WeakReference<>(callbacks);

        //Get a Retrofit instance and its related endpoints
        NYtimesService nYTimesService = NYtimesService.retrofit.create(NYtimesService.class);

        Call<PojoMaster> call = null;
        switch (position) {
            //Create the call on the New York Times API
            case 0: call = nYTimesService.getTopStories(section);
            break;
            case 1: call = nYTimesService.getMostPopular();
//            case 1: call = nYTimesService.getTopStories(section);
            break;
            case 2: call = nYTimesService.getTopStories(section);
            break;
            case 3: call = nYTimesService.getArticleSearch(searchQueries);
            default:
            break;
        }

        //start the call
        call.enqueue(new Callback<PojoMaster>() {
            @Override
            public void onResponse(Call<PojoMaster> call, Response<PojoMaster> response) {
                if (callbacksWeakReference.get() != null) callbacksWeakReference.get().onResponse(response.body());
            }

            @Override
            public void onFailure(Call<PojoMaster> call, Throwable t) {
                if (callbacksWeakReference.get() != null) callbacksWeakReference.get().onFailure();
                t.printStackTrace();
            }
        });
    }
}

