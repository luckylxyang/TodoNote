package lxy.com.todonote.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import lxy.com.todonote.MainActivity;
import lxy.com.todonote.R;
import lxy.com.todonote.net.BaseObserver;
import lxy.com.todonote.net.NetworkManager;
import lxy.com.todonote.utils.ToastUtils;

/**
 * Creator : lxy
 * date: 2019/2/27
 */

public class RegisterFragment extends Fragment {

    private static String TAG = LoginFragment.class.getSimpleName();

    private TextInputEditText etUsername;
    private TextInputEditText etPassword;
    private TextInputEditText etREPassword;
    private Button btnLogin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register,container,false);
        initView(view);
        initListener();
        return view;
    }

    private void initView(View view) {
        etUsername = view.findViewById(R.id.register_et_username);
        etPassword = view.findViewById(R.id.register_et_password);
        etREPassword = view.findViewById(R.id.register_et_repassword);
        btnLogin = view.findViewById(R.id.register_btn);
    }

    private void initListener(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void register() {
        String name = etUsername.getText().toString().trim();
        String pswd = etPassword.getText().toString().trim();
        String repswd = etREPassword.getText().toString().trim();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pswd) || TextUtils.isEmpty(repswd)){
            ToastUtils.show(R.string.login_toast);
            return;
        }
        NetworkManager.getManager().getServer().register(name,pswd,repswd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<LoginModel>(){
                    @Override
                    public void onSuccess(LoginModel loginModel) {
                        ToastUtils.show(R.string.login_success);
                        loginModel.setPassword(pswd);
                        LoginUtil.getInstance().setLoginInfo(new Gson().toJson(loginModel));
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }

                    @Override
                    public void onFailure(String message) {
                        ToastUtils.show(message);
                    }
                });
    }
}
