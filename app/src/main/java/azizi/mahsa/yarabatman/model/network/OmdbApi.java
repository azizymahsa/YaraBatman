package azizi.mahsa.yarabatman.model.network;

import azizi.mahsa.yarabatman.model.data.JMovieDetail;
import azizi.mahsa.yarabatman.model.data.JSearchResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OmdbApi {

    String BASE_URL = "http://www.omdbapi.com/";
    String API_KEY = "3e974fca";

    @GET("./")
    Call<JSearchResult> searchMovies(@Query("apikey") String apiKey,
                                     @Query("s") String keyword);

    @GET("./")
    Call<JMovieDetail> getMovieDetail(@Query("apikey") String apiKey,
                                      @Query("i") String imdbId);
}
