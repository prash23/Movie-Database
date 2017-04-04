package prashanth.apod.network;

import prashanth.apod.network.services.MovieService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Prashanth on 1/23/2017.
 */

public class NetworkManager {
    
    public static final String BaseURL = "http://www.omdbapi.com";
    private Retrofit mRetrofit;
    private MovieService movieService;

    public NetworkManager() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BaseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        movieService = mRetrofit.create(MovieService.class);
    }


    public MovieService getMovieService(){
        return movieService;
    }


}
