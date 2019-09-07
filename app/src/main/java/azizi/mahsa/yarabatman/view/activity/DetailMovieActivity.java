package azizi.mahsa.yarabatman.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import azizi.mahsa.yarabatman.R;
import azizi.mahsa.yarabatman.model.data.JMovie;
import azizi.mahsa.yarabatman.model.data.JMovieDetail;
import azizi.mahsa.yarabatman.model.network.utils.ApiObserver;
import azizi.mahsa.yarabatman.view.adapter.MovieAdapter;
import azizi.mahsa.yarabatman.viewmodel.MovieViewModel;

public class DetailMovieActivity extends AppCompatActivity {


    private MovieViewModel mMovieViewModel;
    private MovieAdapter mAdapter = new MovieAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        initViews();
        initViewModel();

        if (savedInstanceState == null) mMovieViewModel.searchDetails();
    }

    private void initViews() {

      /*  mAdapter.setOnMovieClickListener(new MovieAdapter.OnMovieClickListener() {
            @Override
            public void onMovieClick(String imdbId) {
                Toast.makeText(DetailMovieActivity.this, "Movie Clicked ->" + imdbId, Toast.LENGTH_SHORT).show();
            }
        });
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh);*/

    }

    private void initViewModel() {
        
        Bundle extras = getIntent().getExtras();
        if(extras!=null) {
            extras.getString("img");
            extras.getString("title");
            extras.getString("id");
            search(extras.getString("id"));

        }
    }

    private void search(String id) {
       // mMovieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        mMovieViewModel .getMovieDetail(id);
        mMovieViewModel.getMovieDetailObservable()
                .observe(this, new ApiObserver<JMovieDetail>() {

                    @Override
                    public void onLoading() {

                    }

                    @Override
                    public void onSuccess(JMovieDetail data) {
                        Toast.makeText(DetailMovieActivity.this, data.getCountry(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(DetailMovieActivity.this, "onSuccess to get movies, try agian ...", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Toast.makeText(DetailMovieActivity.this, "Failed to get movies, try agian ...", Toast.LENGTH_SHORT).show();

                    }
                });
    }//end onSearch
}
