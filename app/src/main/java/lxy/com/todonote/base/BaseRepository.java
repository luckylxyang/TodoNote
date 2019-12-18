package lxy.com.todonote.base;

import androidx.lifecycle.MutableLiveData;

import io.reactivex.Observable;
import lxy.com.todonote.net.BaseResponse;
import lxy.com.todonote.net.Resource;
import lxy.com.todonote.net.RxHelper;

/**
 * Created by Administrator on 2019/12/12.
 */

public class BaseRepository {

    public <T> MutableLiveData<T> observe(Observable observable, final MutableLiveData<T> liveData) {

        observable.compose(RxHelper.observableIO2Main())
                .subscribe(o -> {
                    liveData.postValue((T) Resource.response((BaseResponse<Object>) o));
                }, throwable -> {
                    liveData.postValue((T) Resource.error((Throwable) throwable));
                });

        return liveData;
    }
}
