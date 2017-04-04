package prashanth.apod;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import prashanth.apod.fragments.MovieDetailsFragment;
import prashanth.apod.fragments.MoviesListFragment;
import prashanth.apod.interfaces.MovieInterface;

public class MainActivity extends AppCompatActivity implements MovieInterface {
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void fragmentsTransaction(String query) {
        MoviesListFragment listFragment = new MoviesListFragment();
        Bundle args = new Bundle();
        args.putString("movieName",query);
        listFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, listFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_menu,menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setQueryHint("enter a movie name to search");
        searchView.setSearchableInfo(searchManager.
                getSearchableInfo(getComponentName()));
//        searchView.setSubmitButtonEnabled(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fragmentsTransaction(query);
                searchView.clearFocus();
                searchView.setQuery("",false);
                searchView.setVisibility(View.GONE);
                Log.e("search",query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public void onMovieItemSelected(String movieID,boolean landMode) {
        MovieDetailsFragment detailFragment = new MovieDetailsFragment();
        Bundle args = new Bundle();
        args.putString("movieID",movieID);
        detailFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (landMode)
        {
            transaction.replace(R.id.landFrameLayout, detailFragment);
        }
        else {
            transaction.replace(R.id.fragment_list, detailFragment);
            transaction.addToBackStack(null);
        }
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
        } else {
            searchView.setVisibility(View.VISIBLE);
            getSupportFragmentManager().popBackStackImmediate();
        }
    }


    @Override
    public boolean onSupportNavigateUp() {

        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            return super.onSupportNavigateUp();
        } else {
            getSupportFragmentManager().popBackStack();
            return true;
        }

    }
}
