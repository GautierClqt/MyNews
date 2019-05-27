package com.cliquet.gautier.mynews.controllers.Fragments;

import android.content.Context;
import android.content.Intent;
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
import com.cliquet.gautier.mynews.Utils.Utils;
import com.cliquet.gautier.mynews.controllers.Activities.ArticlesDisplayActivity;

import java.text.ParseException;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private Context mContext;
    private List<Result> mResults;
    Utils util = new Utils();

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
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {

        String mSection = mResults.get(i).getSection();
        String mSubsection = mResults.get(i).getSubsection();
        if(!mSubsection.isEmpty()) {
            mSection = mSection + " > " + mSubsection;
        }

        String mDate = mResults.get(i).getUpdatedDate();
        try {
            mDate = util.simplifyDateFormat(mDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String mTitle = mResults.get(i).getTitle();

        List<Multimedium> mMultimedium = mResults.get(i).getMultimedia();
        String multimedium;
        if(mMultimedium.size() != 0) {
            multimedium = mMultimedium.get(0).getUrl();
            viewHolder.urlImage.loadUrl(multimedium);
        }
        else {
            viewHolder.urlImage.setVisibility(View.GONE);
        }
        
        viewHolder.section.setText(mSection);
        viewHolder.date.setText(mDate);
        viewHolder.title.setText(mTitle);

        //When User click on an article a WebView centered activity is called with the article url sent to it
        final String strUrlArticle = mResults.get(i).getUrl();
        viewHolder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent articleDisplayIntent = new Intent(viewHolder.mainLayout.getContext(), ArticlesDisplayActivity.class);
                articleDisplayIntent.putExtra("Url_Article", strUrlArticle);
                mContext.startActivity(articleDisplayIntent);
            }
        });
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
        WebView urlImage;
        WebView urlArticle;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            mainLayout = itemView.findViewById(R.id.layout_item_parentLayout_relativeLayout);
            section = itemView.findViewById(R.id.layout_item_section_textview);
            date = itemView.findViewById(R.id.layout_item_date_textview);
            title = itemView.findViewById(R.id.layout_item_title_textview);
            urlImage = itemView.findViewById(R.id.layout_item_image_webview);
            urlArticle = itemView.findViewById(R.id.layout_item_article_webview);
        }
    }
}
