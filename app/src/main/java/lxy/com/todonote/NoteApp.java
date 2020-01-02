package lxy.com.todonote;

import android.app.Application;
import android.content.Context;



/**
 * Creator : lxy
 * date: 2019/1/30
 */

public class NoteApp extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
//        String rootDir = MMKV.initialize(this);
//        MMKV.mmkvWithID("cookies_prefs", MMKV.MULTI_PROCESS_MODE);
//        System.out.println("mmkv root: " + rootDir);
    }

    public static Context getContext(){
        return context;
    }
}
