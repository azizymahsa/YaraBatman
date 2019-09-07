package azizi.mahsa.yarabatman.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import azizi.mahsa.yarabatman.R;
import azizi.mahsa.yarabatman.model.data.JMovie;
import azizi.mahsa.yarabatman.model.data.JSearchResult;
import azizi.mahsa.yarabatman.model.network.utils.ApiObserver;
import azizi.mahsa.yarabatman.view.adapter.MovieAdapter;
import azizi.mahsa.yarabatman.viewmodel.MovieViewModel;

public class MovieListActivity extends AppCompatActivity {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView list;

    private MovieViewModel mMovieViewModel;
    private MovieAdapter mAdapter = new MovieAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        initViews();
        initViewModel();

        if (savedInstanceState == null) mMovieViewModel.searchMovies();
    }

    private void initViews() {
        list = findViewById(R.id.movie_list);
        list.setLayoutManager(new GridLayoutManager(this, 2));
        list.setAdapter(mAdapter);
        mAdapter.setOnMovieClickListener(new MovieAdapter.OnMovieClickListener() {
            @Override
            public void onMovieClick(String imdbId) {
                Toast.makeText(MovieListActivity.this, "Movie Clicked ->" + imdbId, Toast.LENGTH_SHORT).show();
            }
        });
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh);
        // Configure the refreshing colors
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    private void initViewModel() {
        onSearch();

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

               mAdapter.clear();
               onSearch();
            }
        });
    }

    private void onSearch() {
        mMovieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        mMovieViewModel.getSearchObservable()
                .observe(this, new ApiObserver<List<JMovie>>() {
                    @Override
                    public void onLoading() {
                        mSwipeRefreshLayout.setRefreshing(true);
                    }

                    @Override
                    public void onSuccess(List<JMovie> data) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        mAdapter.addItems(data);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(MovieListActivity.this, "Failed to get movies, try agian ...", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}