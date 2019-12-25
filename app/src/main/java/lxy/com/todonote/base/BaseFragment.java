package lxy.com.todonote.base;

import android.content.Intent;

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

    public abstract class OnCallback<T> implements Resource.OnHandleCallback<T> {
        @Override
        public void onLoading(String showMessage) {

        }

        @Override
        public void onFailure(int code,String msg) {
            ToastUtils.show(msg);
            if (code == NetConstants.NET_FAIL_LOGIN){
                Intent intent = new Intent(getContext(), LoginActivity.class);
                getContext().startActivity(intent);
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
