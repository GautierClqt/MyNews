package com.cliquet.gautier.mynews.controllers.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cliquet.gautier.mynews.R;
import com.cliquet.gautier.mynews.Utils.NetworkAsyncTask;


public class FavoriteFragment extends Fragment implements NetworkAsyncTask.Listeners, View.OnClickListener {

   Button button;
   TextView textView;

    public static FavoriteFragment newInstance() {
        return (new FavoriteFragment());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        textView = view.findViewById(R.id.fragment_favorite_textview);
        button = view.findViewById(R.id.fragment_favorite_button);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle SavedInstanceState) {


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                executeHttpRequest();
            }
        });
    }

//    @OnClick(R.id.fragment_favorite_button)
//    public void submit(View view){
//        this.executeHttpRequest();
//    }

    private void executeHttpRequest() {
        new NetworkAsyncTask(this).execute("https://api.nytimes.com/svc/topstories/v2/science.json?api-key=WftprIljSPh7y8Le0ZmsFjAZAUA9fkkz");
    }

    @Override
    public void onPreExecute() {
        this.updateUIWhenStatingHTTPRequest();
    }

    @Override
    public void doInBackground() {

    }

    @Override
    public void onPostExecute(String json) {
        this.updateUIWhenStopingHTTPRequest(json);
    }

    private void updateUIWhenStatingHTTPRequest() {
        this.textView.setText("Downloading...");
    }

    private void updateUIWhenStopingHTTPRequest(String response) {
        this.textView.setText(response);
    }

    @Override
    public void onClick(View v) {}
}
