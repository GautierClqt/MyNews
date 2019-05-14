package com.cliquet.gautier.mynews.controllers.Fragments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cliquet.gautier.mynews.Models.Multimedium;
import com.cliquet.gautier.mynews.Models.Result;
import com.cliquet.gautier.mynews.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private Context mContext;
    private List<Result> mResults;

    RecyclerViewAdapter(Context context, List<Result> results) {
        this.mContext = context;
        this.mResults = results;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_top_stories_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        String mSection = mResults.get(i).getSection();
        String mSubsection = mResults.get(i).getSubsection();
        String mDate = mResults.get(i).getPublishedDate();
        String mTitle = mResults.get(i).getTitle();
        List<Multimedium> mMultimedium = mResults.get(i).getMultimedia();
        String multimedium = mMultimedium.get(0).getUrl();

        if(!mSubsection.isEmpty()) {
            mSection = mSection + " > " + mSubsection;
        }
        viewHolder.section.setText(mSection);
        viewHolder.date.setText(mDate);
        viewHolder.title.setText(mTitle);
        viewHolder.url.loadUrl(multimedium);
    }

    @Override
    public int getItemCount() {
        return mResults.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout mainLayout;
        TextView section;
        TextView date;
        TextView title;
        WebView url;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            mainLayout = itemView.findViewById(R.id.layout_item_parentLayout_relativeLayout);
            section = itemView.findViewById(R.id.layout_item_section_textview);
            date = itemView.findViewById(R.id.layout_item_date_textview);
            title = itemView.findViewById(R.id.layout_item_title_textview);
            url = itemView.findViewById(R.id.layout_item_image_webview);
        }
    }
}
