package lxy.com.todonote.base;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import lxy.com.todonote.NoteApp;
import lxy.com.todonote.R;
import lxy.com.todonote.login.LoginActivity;
import lxy.com.todonote.net.Resource.OnHandleCallback;
import lxy.com.todonote.utils.ToastUtils;


/**
 * @author lxy
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected FrameLayout flContext;
    protected Toolbar toolbar;
    protected Bundle saveBundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        saveBundle = savedInstanceState;

        init();
        addContextView();
        initOptions();
    }


    /**
     * 设置 activity 内容
     * @return
     */
    public abstract int setContextView();

    protected abstract void initOptions();

    private void init() {
        flContext = findViewById(R.id.base_activity_context);

        toolbar = findViewById(R.id.base_activity_toolbar);
        setSupportActionBar(toolbar);
    }


    public void addContextView(){
        View view = LayoutInflater.from(this).inflate(setContextView(),null);
        flContext.addView(view);
    }

    protected void showToolbarBack(boolean isShow){
        if (isShow){
            Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
            upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(v -> finish());
        }
    }

    protected void setToolbarTitle(String title){
        showToolbarBack(true);
        getSupportActionBar().setTitle(title);
    }

    public abstract static class OnCallback<T> implements OnHandleCallback<T>{
        @Override
        public void onLoading(String showMessage) {

        }

        @Override
        public void onFailure(int code,String msg) {
            ToastUtils.show(msg);
            if (code == Constants.NET_FAIL_LOGIN){
                Intent intent = new Intent(NoteApp.getContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                NoteApp.getContext().startActivity(intent);
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
