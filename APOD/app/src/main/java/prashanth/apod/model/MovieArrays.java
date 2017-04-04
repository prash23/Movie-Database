package prashanth.apod.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Prashanth on 1/30/2017.
 */

import java.util.List;
import com.google.gson.annotations.Expose;

public class MovieArrays {

    @SerializedName("Search")
    @Expose
    private List<Movie> movieList = null;
    @SerializedName("totalResults")
    @Expose
    private String totalResults;
    @SerializedName("Response")
    @Expose
    private String response;

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public MovieArrays(List<Movie> movieList, String totalResults, String response) {
        this.movieList = movieList;
        this.totalResults = totalResults;
        this.response = response;
    }

    @Override
    public String toString() {
        return "MovieArrays{" +
                "movieList=" + movieList +
                ", totalResults='" + totalResults + '\'' +
                ", response='" + response + '\'' +
                '}';
    }
}


