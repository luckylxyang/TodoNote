package lxy.com.todonote.net;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;


import java.io.IOException;
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
//        Log.i(TAG,response.body().string());
        if (!response.headers("set-Cookie").isEmpty()){
            HashSet<String> cookies = new HashSet<>();

            for (String header : response.headers("set-cookie")) {
                cookies.add(header);
            }
            saveCookie(chain.request().url().toString(), chain.request().url().host(), cookies);
        }
        return response;
    }

    private String encodeCookie(List<String> cookies) {
        StringBuilder sb = new StringBuilder();
        Set<String> set = new HashSet<>();
        for (String cookie : cookies) {
            String[] arr = cookie.split(";");
            for (String s : arr) {
                if (set.contains(s)) {
                    continue;
                }
                set.add(s);
            }
        }

        for (String cookie : set) {
            sb.append(cookie).append(";");
        }

        int last = sb.lastIndexOf(";");
        if (last != -1 && sb.length() - 1 == last) {
            sb.deleteCharAt(last);
        }

        return sb.toString();
    }

    /**
     * 保存cookie到本地，这里我们分别为该url和host设置相同的cookie，其中host可选
     * 这样能使得该cookie的应用范围更广
     */
    private void saveCookie(String url, String domain, HashSet cookies) {
        SharedPreferences sp = NoteApp.getContext().getSharedPreferences("cookies_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (TextUtils.isEmpty(url)) {
            throw new NullPointerException("url is null.");
        } else {
            editor.putStringSet(url, cookies);
        }

        if (!TextUtils.isEmpty(domain)) {
            editor.putStringSet(domain, cookies);
        }
        editor.apply();
    }
}