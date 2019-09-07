package azizi.mahsa.yarabatman.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import azizi.mahsa.yarabatman.model.data.JMovie;
import azizi.mahsa.yarabatman.model.data.JMovieDetail;
import azizi.mahsa.yarabatman.model.network.MovieRepo;
import azizi.mahsa.yarabatman.model.network.utils.Response;

public class MovieViewModel extends AndroidViewModel {

    private MovieRepo repo = new MovieRepo(getApplication());

    public MovieViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Response<List<JMovie>>> getSearchObservable() {
        return repo.getSearchObservable();
    }

    public void searchMovies() {
        repo.searchMovies();
    }
    public void searchDetails() {
        repo.searchDetails();
    }

    public LiveData<Response<JMovieDetail>> getMovieDetailObservable() {
        return repo.getMovieDetailObservable();
    }

    public void getMovieDetail(String imdbId) {
        repo.getMovieDetail(imdbId);
    }
}
