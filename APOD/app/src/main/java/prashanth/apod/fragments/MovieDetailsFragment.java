package prashanth.apod.fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import prashanth.apod.R;
import prashanth.apod.model.Movie;
import prashanth.apod.network.NetworkManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Prashanth on 1/30/2017.
 */

public class MovieDetailsFragment extends Fragment {

    String imdbID;
    String query;
    NetworkManager mNetworkManager;
    Activity activity;
    TextView movieTitle,year,rating,released,runTime,genre,plot,
            language,director,writer,actors,country,awards,imdbRating,imdbVotes,type;
    ImageView poster;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Bundle args = getArguments();
        imdbID = args.getString("movieID");
        query = args.getString("movieName");
        mNetworkManager = new NetworkManager();
        super.onCreate(savedInstanceState);
    }

    private void initialize(View view) {
        movieTitle = (TextView) view.findViewById(R.id.summaryTitle);
        year = (TextView) view.findViewById(R.id.summaryYear);
        rating = (TextView) view.findViewById(R.id.summaryRating);
        released = (TextView) view.findViewById(R.id.summaryReleased);
        runTime = (TextView) view.findViewById(R.id.summaryRunTime);
        genre = (TextView) view.findViewById(R.id.summaryGenre);
        plot = (TextView) view.findViewById(R.id.summaryPlot);
        language = (TextView) view.findViewById(R.id.summaryLanguage);
        director = (TextView) view.findViewById(R.id.summaryDirector);
        writer = (TextView) view.findViewById(R.id.summaryWriter);
        actors = (TextView) view.findViewById(R.id.summaryActors);
        country = (TextView) view.findViewById(R.id.summaryCountry);
        awards = (TextView) view.findViewById(R.id.summaryAwards);
        imdbRating = (TextView) view.findViewById(R.id.summaryIMDBRating);
        imdbVotes = (TextView) view.findViewById(R.id.summaryIMDBVotes);
        type = (TextView) view.findViewById(R.id.summaryType);
        poster = (ImageView) view.findViewById(R.id.summaryPoster);
    }

    public static MovieDetailsFragment newInstance() {
        
      return new MovieDetailsFragment();
    }

    private void displayList(Movie movie) {

        movieTitle.setText("Movie Title: "+movie.getTitle());
        year.setText("Year: "+movie.getYear());
        rating.setText("Rating: "+movie.getRated());
        released.setText("Released in: "+movie.getReleased());
        runTime.setText("Runtime: "+movie.getRuntime());
        genre.setText("Genre: "+movie.getGenre());
        plot.setText("Plot: "+movie.getPlot());
        language.setText("Language: "+movie.getLanguage());
        director.setText("Director: "+movie.getDirector());
        writer.setText("Writer: "+movie.getWriter());
        actors.setText("Actors: "+movie.getActors());
        country.setText("Country: "+movie.getCountry());
        awards.setText("Awards: "+movie.getAwards());
        imdbRating.setText("IMDB Rating:"+movie.getImdbRating());
        imdbVotes.setText("IMDB Votes: "+movie.getImdbVotes());
        type.setText("Type: "+movie.getType());
        Picasso.with(getActivity()).load(movie.getPoster()).fit().into(poster);
    }

    public MovieDetailsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_movie_summary, container, false);
        initialize(view);
        Log.e("id is",imdbID);
        Call<Movie> movieDataCall = mNetworkManager.getMovieService().getFullMovieData(imdbID);
        movieDataCall.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if(response.isSuccessful())
                {
                    displayList(response.body());

                    Log.e("onResponse","Success "+ response.body());
                }else {

                    Log.e("onResponse","Custom error code "+ response.code());
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                if(t instanceof UnknownHostException)
                {
                    Log.e("onFailure","No  network "+ t.getMessage());
                }else if (t instanceof SocketTimeoutException){
                    Log.e("onFailure","Timeout "+ t.getMessage());
                }
                else
                {
                    Log.e("onFailure","Error "+ t.getMessage());
                }
            }
        });
        return view;
    }

}
