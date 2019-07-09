package com.cliquet.gautier.mynews.Models.PojoMostPopular;

import java.util.List;

public class Media {

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
