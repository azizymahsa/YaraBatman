package azizi.mahsa.yarabatman.model.network.utils;

import androidx.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class ApiCallback<T> implements Callback<T> {

    @Override
    final public void onResponse(@NonNull Call<T> call,
                           @NonNull Response<T> response) {
        if (response.body() != null) {
            onSuccess(response.body());
        } else {
            onFailure(new IllegalStateException("Response body is empty"));
        }
    }

    @Override
    final public void onFailure(@NonNull Call<T> call,
                                @NonNull Throwable t) {
        onFailure(t);
    }

    public abstract void onSuccess(T data);

    public abstract void onFailure(Throwable t);
}
