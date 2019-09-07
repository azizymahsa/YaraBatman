package azizi.mahsa.yarabatman.model.network;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.lang.ref.WeakReference;
import java.util.List;

import azizi.mahsa.yarabatman.model.data.JMovie;
import azizi.mahsa.yarabatman.model.data.JMovieDetail;
import azizi.mahsa.yarabatman.model.data.JSearchResult;
import azizi.mahsa.yarabatman.model.database.DetailDao;
import azizi.mahsa.yarabatman.model.database.MovieDao;
import azizi.mahsa.yarabatman.model.database.MovieDatabase;
import azizi.mahsa.yarabatman.model.network.utils.ApiCallback;
import azizi.mahsa.yarabatman.model.network.utils.ApiRequestHelper;
import azizi.mahsa.yarabatman.model.network.utils.Response;
import retrofit2.Call;

public class MovieRepo {

    private static final String SEARCH_KEYWORD = "batman";

    public MovieRepo(Context context) {
        mMovieDao = MovieDatabase.createInstance(context).getMovieDao();
        mDetailDao = MovieDatabase.createInstance(context).getDetailDao();
        mWeakContext = new WeakReference<>(context);
    }

    private WeakReference<Context> mWeakContext;
    private MovieDao mMovieDao;
    private DetailDao mDetailDao;
    private OmdbApi mApi = ApiProvider.getInstance().getOmdbApi();

    private MutableLiveData<Response<List<JMovie>>> searchObservable = new MutableLiveData<>();
    private MutableLiveData<Response<JMovieDetail>> movieDetailObservable = new MutableLiveData<>();

    public LiveData<Response<List<JMovie>>> getSearchObservable() {
        return searchObservable;
    }

    public LiveData<Response<JMovieDetail>> getMovieDetailObservable() {
        return movieDetailObservable;
    }

    public void searchMovies() {
        List<JMovie> cachedMovies = mMovieDao.getAll();
        if (cachedMovies == null || cachedMovies.isEmpty()) {
            fetchMoviesFromNetwork();
        } else {
            searchObservable.setValue(new Response.Builder<List<JMovie>>().success(cachedMovies));
            toast("Data received from database");
        }
    }

    public void getDetails(String id) {
       JMovieDetail cachedDetail = mDetailDao.getDetail(id);
        if (cachedDetail == null ) {
            fetchDetailsMovieFromNetwork(id);
        } else {
            movieDetailObservable.setValue(new Response.Builder<JMovieDetail>().success(cachedDetail));
            toast("Data received from database!!!");
        }
    }

    private void fetchDetailsMovieFromNetwork(String id) {
        Call<JMovieDetail> call = mApi.getMovieDetail(OmdbApi.API_KEY, id);
        movieDetailObservable.setValue(new Response.Builder<JMovieDetail>().loading());
        call.enqueue(new ApiCallback<JMovieDetail>() {
            @Override
            public void onSuccess(JMovieDetail data) {
                JMovieDetail movies = data;
                movieDetailObservable.setValue(new Response.Builder<JMovieDetail>().success(movies));
                mDetailDao.insert(movies);
                toast("Data received from network and cached!!!");
            }

            @Override
            public void onFailure(Throwable t) {
                movieDetailObservable.setValue(new Response.Builder<JMovieDetail>().error(t));
            }
        });
    }

    private void fetchMoviesFromNetwork() {
        Call<JSearchResult> call = mApi.searchMovies(OmdbApi.API_KEY, SEARCH_KEYWORD);
        searchObservable.setValue(new Response.Builder<List<JMovie>>().loading());
        call.enqueue(new ApiCallback<JSearchResult>() {
            @Override
            public void onSuccess(JSearchResult data) {
                List<JMovie> movies = data.getSearch();
                searchObservable.setValue(new Response.Builder<List<JMovie>>().success(movies));
                mMovieDao.insert(movies);
                toast("Data received from network and cached");
            }

            @Override
            public void onFailure(Throwable t) {
                searchObservable.setValue(new Response.Builder<List<JMovie>>().error(t));
            }
        });
    }




    private void toast(String msg) {
        Context context = mWeakContext.get();
        if (context != null) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
    }
}
