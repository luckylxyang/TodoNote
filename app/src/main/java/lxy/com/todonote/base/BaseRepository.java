package lxy.com.todonote.base;

import androidx.lifecycle.MutableLiveData;

import io.reactivex.Observable;
import lxy.com.todonote.net.BaseResponse;
import lxy.com.todonote.net.NetworkAPI;
import lxy.com.todonote.net.NetworkManager;
import lxy.com.todonote.net.Resource;
import lxy.com.todonote.net.RxHelper;

/**
 * Created by lxy on 2019/12/12.
 */

public class BaseRepository {

    private NetworkAPI server;

    public NetworkAPI getServer(){
        return NetworkManager.getManager().getServer();
    }

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
