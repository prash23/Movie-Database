package prashanth.apod.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import prashanth.apod.R;
import prashanth.apod.adapters.MovieAdapter;
import prashanth.apod.model.Movie;
import prashanth.apod.model.MovieArrays;
import prashanth.apod.network.NetworkManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Prashanth on 1/30/2017.
 */

public class MoviesListFragment extends Fragment{
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    List<Movie> movieList;
    String movieName;
    MovieAdapter movieAdapter;
    NetworkManager mNetworkManager;
    ProgressBar mProgressBar;
    Activity activity;
    boolean landMode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Bundle args = getArguments();
        movieName = args.getString("movieName");
        mNetworkManager = new NetworkManager();
        movieList = new ArrayList<>();
        activity = getActivity();
        super.onCreate(savedInstanceState);
    }



    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_movies_list, container, false);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        final RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.relativeLayout);
        if (view.findViewById(R.id.landFrameLayout)!=null)
        {
            landMode = true;
        }
        else
        {
            landMode = false;
        }
        mProgressBar.setVisibility(View.VISIBLE);
        Call<MovieArrays> movieDataCall = mNetworkManager.getMovieService().getMovieData(movieName);
        movieDataCall.enqueue(new Callback<MovieArrays>()
        {
            @Override
            public void onResponse(Call<MovieArrays> call, Response<MovieArrays> response) {
                mProgressBar.setVisibility(View.GONE);
                if(response.isSuccessful())
                {
                    if (response.body().getMovieList() == null)
                    {
                        Toast.makeText(getActivity(),"Sorry we couldn't find the movie "+movieName+"!",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        displayList(response.body().getMovieList());
                        Log.e("onResponse","Success "+ response.body().getMovieList());
                    }
                }
                else
                {
                    Toast.makeText(getActivity(),"Sorry error occurred loading images",Toast.LENGTH_LONG).show();
                    Log.e("onResponse","Custom error code "+ response.code());
                }
            }

            private void displayList(List<Movie> movieList) {
                mRecyclerView = (RecyclerView) view.findViewById(R.id.moviesList);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(activity.getApplicationContext(),LinearLayoutManager.VERTICAL,false));
                movieAdapter = new MovieAdapter(movieList,getActivity(),MoviesListFragment.this,landMode);
                mRecyclerView.setAdapter(movieAdapter);
                movieAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MovieArrays> call, Throwable t) {
                mProgressBar.setVisibility(View.GONE);
                if(t instanceof UnknownHostException)
                {
                    Toast.makeText(getActivity(),"No network! Please check your network connection and try again",Toast.LENGTH_LONG).show();
                    Log.e("onFailure","No  network "+ t.getMessage());
                }else if (t instanceof SocketTimeoutException){
                    Toast.makeText(getActivity(),"Sorry request timed out!",Toast.LENGTH_LONG).show();
                    Log.e("onFailure","Timeout "+ t.getMessage());
                }
                else
                {
                    Toast.makeText(getActivity(),"Sorry error occurred loading images",Toast.LENGTH_LONG).show();
                    Log.e("onFailure","Error "+ t.getMessage());
                }
            }
        });

        return view;
    }

    public static MoviesListFragment newInstance() {
        return new MoviesListFragment();
    }

}
