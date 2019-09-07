package azizi.mahsa.yarabatman.model.network.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Response<T> {

    private final T mData;
    private final Throwable mThrowable;
    private final NetworkStatus mStatus;

    private Response(@NonNull NetworkStatus status,
                     @Nullable T data,
                     @Nullable Throwable throwable) {
        mStatus = status;
        mData = data;
        mThrowable = throwable;
    }

    public T getData() {
        return mData;
    }

    public Throwable getThrowable() {
        return mThrowable;
    }

    public NetworkStatus getStatus() {
        return mStatus;
    }

    public static class Builder<T> {
        public Builder() {
        }

        public Response<T> loading() {
            return new Response<T>(NetworkStatus.LOADING, null, null);
        }

        public Response<T> success(@NonNull T data) {
            return new Response<T>(NetworkStatus.SUCCESS, data, null);
        }

        public Response<T> error(@NonNull Throwable throwable) {
            return new Response<>(NetworkStatus.ERROR, null, throwable);
        }
    }
}
