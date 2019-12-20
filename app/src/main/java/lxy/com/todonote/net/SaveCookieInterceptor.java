package lxy.com.todonote.net;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;


import com.tencent.mmkv.MMKV;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lxy.com.todonote.NoteApp;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Creator : lxy
 * date: 2019/1/30
 */

public class SaveCookieInterceptor implements Interceptor {

    private static String TAG = "SaveCookieInterceptor";


    @Override
    public Response intercept(Chain chain) throws IOException {
         Response response = chain.proceed(chain.request());
        List<String> headers = response.headers("Set-Cookie");
        if (!headers.isEmpty()){
            HashSet<String> cookies = new HashSet<>();
            Log.i("OkCookieTAG", Arrays.toString(headers.toArray()));
            for (String header : headers) {
                Log.d("OkCookieSave",header);
                cookies.add(header);
            }
            saveCookie(chain.request().url().toString(), chain.request().url().host(), cookies);
        }
        return response;
    }

    /**
     * 保存cookie到本地，这里我们分别为该url和host设置相同的cookie，其中host可选
     * 这样能使得该cookie的应用范围更广
     */
    private void saveCookie(String url, String domain, HashSet cookies) {
        SharedPreferences sp = NoteApp.getContext().getSharedPreferences("cookies_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        MMKV mmkv = MMKV.mmkvWithID("cookies_prefs", MMKV.MULTI_PROCESS_MODE);

        if (TextUtils.isEmpty(url)) {
            throw new NullPointerException("url is null.");
        } else {
            mmkv.encode(url,cookies);
        }

        if (!TextUtils.isEmpty(domain)) {
            mmkv.encode(domain,cookies);
        }
        editor.apply();
    }

    private HashSet getCookie(String url, String domain) {
        SharedPreferences sp = NoteApp.getContext().getSharedPreferences("cookies_prefs", Context.MODE_PRIVATE);
        if (!TextUtils.isEmpty(url) && sp.contains(url)) {
            return (HashSet) sp.getStringSet(url,new HashSet<>());
        }
        if (!TextUtils.isEmpty(domain) && sp.contains(domain) ) {
            return (HashSet) sp.getStringSet(domain,new HashSet<>());
        }
        return new HashSet<>();
    }
}
