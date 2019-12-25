package lxy.com.todonote.net;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;


import com.google.gson.Gson;
import com.tencent.mmkv.MMKV;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
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
        if (!headers.isEmpty() && chain.request().url().toString().contains(NetConstants.URL_LOGIN)){
            HashSet<String> cookies = new HashSet<>();
            List<String> model = new ArrayList<>();
            for (String header : headers) {
                cookies.add(header);
                model.add(header);
            }
//            saveCookie(chain.request().url().toString(), chain.request().url().host(), cookies);
            saveCookie(chain.request().url().toString(), chain.request().url().host(), model);
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
        Log.i("OkCookieSave", Arrays.toString(cookies.toArray()));
        if (!TextUtils.isEmpty(url)) {
            boolean encode = mmkv.encode(url, cookies);
            Log.i("OkCookieResult",String.valueOf(encode));
            editor.putStringSet(url,cookies);
        }

        if (!TextUtils.isEmpty(domain)) {
            boolean encode = mmkv.encode(domain,cookies);
            Log.i("OkCookieResultdomain",String.valueOf(encode));
        }
//        editor.apply();
    }

    private void saveCookie(String url, String domain, List<String> cookies) {
        SharedPreferences sp = NoteApp.getContext().getSharedPreferences("cookies_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Log.i("OkCookieSave", Arrays.toString(cookies.toArray()));
        JSONArray array = new JSONArray();
        for (String cookie : cookies) {
            array.put(cookie);
        }
        if (!TextUtils.isEmpty(url)) {
            editor.putString(url,array.toString());
        }

        if (!TextUtils.isEmpty(domain)) {
            editor.putString(domain,array.toString());
        }
        editor.apply();
    }
}
