package com.cliquet.gautier.mynews.Models.PojoMostPopular;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Media {

    @SerializedName("media-metadata")
    @Expose
    private List<Media_metadata> media_metadata;

    public List<Media_metadata> getMedia_metadata ()
    {
        return media_metadata;
    }
    public void setMedia_metadata (List<Media_metadata> media_metadata)
    {
        this.media_metadata = media_metadata;
    }
}
