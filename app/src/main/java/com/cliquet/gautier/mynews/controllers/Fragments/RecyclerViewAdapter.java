package com.cliquet.gautier.mynews.controllers.Fragments;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cliquet.gautier.mynews.Models.Articles;
import com.cliquet.gautier.mynews.R;
import com.cliquet.gautier.mynews.Utils.OnBottomReachedListener;
import com.cliquet.gautier.mynews.Utils.Utils;
import com.cliquet.gautier.mynews.controllers.Activities.DisplaySelectedArticleActivity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private Context mContext;

    private List<Articles> articles;

    private Utils util = new Utils();

    private OnBottomReachedListener onBottomReachedListener;

    public RecyclerViewAdapter(Context context, List<Articles> articles) {
        this.mContext = context;
        this.articles = articles;
    }

    public void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener) {
        this.onBottomReachedListener = onBottomReachedListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_top_stories_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {

        viewHolder.title.setText(articles.get(i).getTitle());

        String mSection = articles.get(i).getSection();
        String mSubsection = articles.get(i).getSubsection();
        if(!(mSubsection == null || mSubsection.equals(""))){
            mSection = mSection + " > " + mSubsection;
        }
        viewHolder.section.setText(mSection);

        String mDate = articles.get(i).getDate();
        try {
            mDate = util.simplifyDateFormat(mDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        viewHolder.date.setText(mDate);

        final String urlArticle = articles.get(i).getUrlArticle();

        if(!articles.get(i).getUrlImage().equals("")) {
            Glide.with(viewHolder.urlImage).load(articles.get(i).getUrlImage()).into(viewHolder.urlImage);
        }
        else {
            viewHolder.urlImage.setVisibility(View.GONE);
        }

        viewHolder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent articleDisplayIntent = new Intent(viewHolder.mainLayout.getContext(), DisplaySelectedArticleActivity.class);
                articleDisplayIntent.putExtra("Url_Article", urlArticle);
                mContext.startActivity(articleDisplayIntent);
            }
        });

        if(i == articles.size()-1) {
            onBottomReachedListener.onBottomReached(i);
        }
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void setArticles(ArrayList<Articles> articles) {
        this.articles = articles;
        notifyDataSetChanged(); //indique à l'adapter que les données ont été modifiées.
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout mainLayout;
        TextView title;
        TextView section;
        TextView date;
        WebView urlArticle;
        ImageView urlImage;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            mainLayout = itemView.findViewById(R.id.layout_item_parentLayout_relativeLayout);
            section = itemView.findViewById(R.id.layout_item_section_textview);
            date = itemView.findViewById(R.id.layout_item_date_textview);
            title = itemView.findViewById(R.id.layout_item_title_textview);
            urlImage = itemView.findViewById(R.id.layout_item_image_imageview);
            urlArticle = itemView.findViewById(R.id.layout_item_article_webview);
        }
    }
}
