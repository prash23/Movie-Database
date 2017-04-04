package prashanth.apod.network.services;

import prashanth.apod.model.Movie;
import prashanth.apod.model.MovieArrays;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Prashanth on 1/30/2017.
 */

public interface MovieService {
    @GET("/")
    Call<MovieArrays> getMovieData(@Query("s") String title);
    @GET("/")
    Call<Movie> getFullMovieData(@Query("i") String id);
}
