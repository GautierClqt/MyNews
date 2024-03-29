package com.cliquet.gautier.mynews.controllers.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private Context mContext;

    private List<Articles> articles;
    private ArrayList<String> mClickedIdList;
    private Gson gson = new Gson();
    private String mJsonId;
    private SharedPreferences mPreferences;

    private Utils utils = new Utils();

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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_recycler_item, viewGroup, false);
        mPreferences = mContext.getSharedPreferences("already_read_articles", 0);
        mJsonId = mPreferences.getString("mClickedIdList", null);

        mClickedIdList = gson.fromJson(mJsonId, new TypeToken<ArrayList<String>>(){}.getType());
        if(mClickedIdList == null) {
            mClickedIdList = new ArrayList<>();
        }

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {

        //initialiaze all items with the "unread articles" color
        viewHolder.mainLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.unreadArticles));
        viewHolder.title.setText(articles.get(i).getTitle());

        String mSection = articles.get(i).getSection();
        viewHolder.section.setText(mSection);

        String mDate = articles.get(i).getDate();
        try {
            mDate = utils.simplifyDateFormat(mDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        viewHolder.date.setText(mDate);

        final String urlArticle = articles.get(i).getUrlArticle();

        if(!articles.get(i).getUrlImage().equals("")) {
            Glide.with(viewHolder.urlImage).load(articles.get(i).getUrlImage()).into(viewHolder.urlImage);
        }
        else {
            //load a default image if no images were provided by the api
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                viewHolder.urlImage.setImageDrawable(mContext.getDrawable(R.drawable.icon_nopicture));
            }
        }

        //check if article has been read to change its background color
        for(int j = 0; j <= mClickedIdList.size()-1; j++){
            if(articles.get(i).getId().equals(mClickedIdList.get(j))) {
                viewHolder.mainLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.readArticles));
            }
        }

        viewHolder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent articleDisplayIntent = new Intent(viewHolder.mainLayout.getContext(), DisplaySelectedArticleActivity.class);
                articleDisplayIntent.putExtra("Url_Article", urlArticle);

                //here id of clicked article is put in a list if not already, this list will be used to change layout color of previously read articles
                int idListSize = mClickedIdList.size();
                int j = 0;
                if(idListSize == 0) {
                    mClickedIdList.add(articles.get(i).getId());
                    viewHolder.mainLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.readArticles));
                }
                else {
                    while(j <= idListSize) {
                        if(j == idListSize) {
                            mClickedIdList.add(articles.get(i).getId());
                            viewHolder.mainLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.readArticles));
                            break;
                        }
                        else if(articles.get(i).getId().equals(mClickedIdList.get(j))) {
                            break;
                        }
                        j++;
                    }
                }
                mJsonId = gson.toJson(mClickedIdList);
                mPreferences.edit().putString("mClickedIdList", mJsonId).apply();

                mContext.startActivity(articleDisplayIntent);
            }
        });

        //check if the user reach the last Recycler item
        if(i == articles.size()) {
            onBottomReachedListener.onBottomReached(i);
        }
    }

    public void setArticles(ArrayList<Articles> articles) {
        this.articles = articles;
        //notify the adapter that there is some datas change
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return articles.size();
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
