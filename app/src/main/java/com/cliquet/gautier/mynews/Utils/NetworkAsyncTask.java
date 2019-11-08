package com.cliquet.gautier.mynews.Utils;

import android.util.Log;

import java.lang.ref.WeakReference;

public class NetworkAsyncTask extends android.os.AsyncTask<String, Void, String> {

    public interface Listeners {
        void onPreExecute();
        void doInBackground();
        void onPostExecute(String succes);
    }

    private final WeakReference<Listeners> callback;

    public NetworkAsyncTask(Listeners callback) {
        this.callback = new WeakReference<>(callback);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.callback.get().onPreExecute();
        Log.e("TAG", "AsyncTask is started.");
    }

    @Override
    protected void onPostExecute(String success) {
        super.onPostExecute(success);
        this.callback.get().onPostExecute(success);
        Log.e("TAG", "AsynTask is finished.");
    }

    @Override
    protected String doInBackground(String... url) {
        this.callback.get().doInBackground();
        Log.e("TAG", "AsyncTask doing work.");
        return HttpUrlConnection.startHttpRequest(url[0]);
    }
}
