package azizi.mahsa.yarabatman.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Locale;

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
    private String id;
    private ImageView movieImgView;
    private TextView movie_title, movie_Genre, movie_releas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        initViews();
        initViewModel();

        if (savedInstanceState == null) mMovieViewModel.searchDetails();
    }

    private void initViews() {
        movie_title = findViewById(R.id.movie_text);
        movie_Genre = findViewById(R.id.movie_Genre);
        movie_releas = findViewById(R.id.movie_releas);
        movieImgView = findViewById(R.id.movie_img);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getString("id");

        }

    }

    private void initViewModel() {
        mMovieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        mMovieViewModel.getMovieDetail(id);
        mMovieViewModel.getMovieDetailObservable()
                .observe(this, new ApiObserver<JMovieDetail>() {

                    @Override
                    public void onLoading() {

                    }

                    @Override
                    public void onSuccess(JMovieDetail data) {
                        Toast.makeText(DetailMovieActivity.this, "Country:" + data.getCountry(), Toast.LENGTH_SHORT).show();
                        movie_title.setText(String.format(Locale.US, "%s (%s)  Runtime : %s", data.getTitle(), data.getYear(), data.getRuntime()));
                        movieImgView.setImageResource(0);
                        Glide.with(DetailMovieActivity.this).load(data.getPoster()).into(movieImgView);
                        movie_Genre.setText(String.format(Locale.US, "Genre : ( %s )", data.getGenre()));
                        movie_releas.setText(String.format(Locale.US, "Writer : %s ", data.getWriter()));
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Toast.makeText(DetailMovieActivity.this, "Failed to get movies, try agian ...", Toast.LENGTH_SHORT).show();

                    }
                });

    }


    private void search(String id) {
        mMovieViewModel.searchDetails();

    }
}
