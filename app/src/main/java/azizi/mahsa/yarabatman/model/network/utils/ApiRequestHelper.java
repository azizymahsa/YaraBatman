package azizi.mahsa.yarabatman.model.network.utils;

import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;

public final class ApiRequestHelper {

    public static <T> void request(Call<T> call,
                                   final MutableLiveData<Response<T>> observable) {
        observable.setValue(new Response.Builder<T>().loading());
        call.enqueue(new ApiCallback<T>() {
            @Override
            public void onSuccess(T data) {
                observable.postValue(new Response.Builder<T>().success(data));
            }

            @Override
            public void onFailure(Throwable t) {
                observable.postValue(new Response.Builder<T>().error(t));
            }
        });
    }
}
