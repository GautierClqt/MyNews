package com.cliquet.gautier.mynews.Models.PojoMostPopular;

import java.util.List;

public class PojoMostPopular {
    private String copyright;

    private List<Results> results;

    private String num_results;

    private String status;

    public String getCopyright ()
    {
        return copyright;
    }

    public void setCopyright (String copyright)
    {
        this.copyright = copyright;
    }

    public List<Results> getResults ()
    {
        return results;
    }

    public void setResults (List<Results> results)
    {
        this.results = results;
    }

    public String getNum_results ()
    {
        return num_results;
    }

    public void setNum_results (String num_results)
    {
        this.num_results = num_results;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }
}
