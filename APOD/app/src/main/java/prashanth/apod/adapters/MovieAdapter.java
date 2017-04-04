package prashanth.apod.adapters;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import prashanth.apod.R;
import prashanth.apod.interfaces.MovieInterface;
import prashanth.apod.model.Movie;

/**
 * Created by Prashanth on 1/27/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{


    List<Movie> movieList = new ArrayList<>();
    Context context;
    Fragment fragment;
    int index = 0;
    boolean landMode= false;
    MovieInterface movieInterface;


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameOfMovie,yearReleased;
        ImageView movieImage;
        int holderIndex;
        public ViewHolder(View itemView) {
            super(itemView);
            nameOfMovie = (TextView) itemView.findViewById(R.id.movieName);
            yearReleased = (TextView) itemView.findViewById(R.id.year);
            movieImage = (ImageView) itemView.findViewById(R.id.poster);
        }
    }

    public MovieAdapter(List<Movie> movieList,Context context, Fragment fragment,boolean landMode) {
        this.movieList = movieList;
        this.fragment = fragment;
        this.context = context;
        this.landMode = landMode;
    }

    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.holderIndex = index;
        index++;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieAdapter.ViewHolder holder, int position) {
        final Movie movie = movieList.get(position);
        holder.nameOfMovie.setText(movie.getTitle());
        holder.yearReleased.setText(movie.getYear());
        Picasso.with(context)
                .load(movie.getPoster())
                .error(R.drawable.errorimg)
                .fit()
                .into(holder.movieImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    movieInterface = (MovieInterface) context;
                }catch (Exception ex)
                {
                    Log.e("Exception: ",ex.toString());
                }
               movieInterface.onMovieItemSelected(movie.getImdbID(),landMode);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }


}
