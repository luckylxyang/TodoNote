package lxy.com.todonote.base;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import lxy.com.todonote.login.LoginActivity;
import lxy.com.todonote.net.NetConstants;
import lxy.com.todonote.net.Resource;
import lxy.com.todonote.utils.ToastUtils;

/**
 * Creator : lxy
 * date: 2019/12/25
 */
public class BaseFragment extends Fragment {
    private static Context mContext;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public abstract static class OnCallback<T> implements Resource.OnHandleCallback<T> {
        @Override
        public void onLoading(String showMessage) {

        }

        @Override
        public void onFailure(int code,String msg) {
            ToastUtils.show(msg);
            if (code == NetConstants.NET_FAIL_LOGIN){
                Intent intent = new Intent(mContext, LoginActivity.class);
                mContext.startActivity(intent);
            }
        }

        @Override
        public void onError(Throwable error) {

        }

        @Override
        public void onCompleted() {

        }
    }
}
