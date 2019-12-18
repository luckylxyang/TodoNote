package lxy.com.todonote.net;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2019/11/8.
 */

public class RxHelper {
//    public static <T> ObservableTransformer<T, T> observableIO2Main(final Context mContext) {
//        return upstream -> {
//            Observable<T> observable = upstream.subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread());
//            return composeContext(mContext, observable);
//        };
//    }

//    public static <T> ObservableTransformer<T, T> observableIO2Main(final RxFragment fragment) {
//        return upstream -> upstream.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread()).compose(fragment.<T>bindToLifecycle());
//    }



    public static <T> ObservableTransformer<T, T> observableIO2Main() {
        return upstream -> upstream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

//    private static <T> ObservableSource<T> composeContext(Context mContext, Observable<T> observable) {
//        if(mContext instanceof RxActivity) {
//            return observable.compose(((RxActivity) mContext).bindUntilEvent(ActivityEvent.DESTROY));
//        } else if(mContext instanceof RxFragmentActivity){
//            return observable.compose(((RxFragmentActivity) mContext).bindUntilEvent(ActivityEvent.DESTROY));
//        }else if(mContext instanceof RxAppCompatActivity){
//            return observable.compose(((RxAppCompatActivity) mContext).bindUntilEvent(ActivityEvent.DESTROY));
//        }else {
//            return observable;
//        }
//    }
}
