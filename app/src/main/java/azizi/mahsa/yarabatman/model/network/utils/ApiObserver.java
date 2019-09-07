package azizi.mahsa.yarabatman.model.network.utils;

import androidx.lifecycle.Observer;

public abstract class ApiObserver<T> implements Observer<Response<T>> {

    @Override
    final public void onChanged(Response<T> tResponse) {
        switch (tResponse.getStatus()) {
            case LOADING:
                onLoading();
                break;
            case SUCCESS:
                onSuccess(tResponse.getData());
                break;
            case ERROR:
                onFailure(tResponse.getThrowable());
                break;
        }
    }

    public abstract void onLoading();

    public abstract void onSuccess(T data);

    public abstract void onFailure(Throwable throwable);
}
