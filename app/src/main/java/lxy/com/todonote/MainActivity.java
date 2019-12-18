package lxy.com.todonote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import lxy.com.todonote.login.LoginActivity;
import lxy.com.todonote.login.LoginUtil;
import lxy.com.todonote.note.NoteFragment;

/**
 * @author lxy
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkLogin();
    }

    private void checkLogin() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container,new NoteFragment());
        transaction.commit();
    }
}
