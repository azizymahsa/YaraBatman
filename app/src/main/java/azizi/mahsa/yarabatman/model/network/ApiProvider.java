package azizi.mahsa.yarabatman.model.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiProvider {

    private static ApiProvider INSTANCE = new ApiProvider();

    public static ApiProvider getInstance(){
        return INSTANCE;
    }

    private static final long CONNECT_TIMEOUT_MILLIS = 10000L;
    private static final long READ_TIMEOUT_MILLIS = 10000L;
    private OmdbApi mFourSquareApi;

    private ApiProvider() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
                .readTimeout(READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
                .build();
        mFourSquareApi = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(OmdbApi.BASE_URL)
                .client(client)
                .build()
                .create(OmdbApi.class);
    }

    public OmdbApi getOmdbApi() {
        return mFourSquareApi;
    }
}
