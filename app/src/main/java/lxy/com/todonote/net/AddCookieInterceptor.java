package lxy.com.todonote.net;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;


import com.google.gson.Gson;
import com.tencent.mmkv.MMKV;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

import lxy.com.todonote.NoteApp;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Creator : lxy
 * date: 2019/1/30
 */

public class AddCookieInterceptor implements Interceptor {
    private static final String COOKIE_PREF = "cookies_prefs";


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
//        HashSet cookies = getCookie(request.url().toString(), request.url().host());
//        Log.i("OkCookieTAG", Arrays.toString(cookies.toArray()));
//        for (Object header : cookies) {
//            Log.d("OkCookieAdd",header.toString());
//            builder.addHeader("Cookie",(String)header);
//        }
        JSONArray cookies = getCookie(request.url().toString(), request.url().host());
        Log.i("OkCookieTAG", cookies.toString());
        for (int i = 0; i < cookies.length(); i++) {
            String header = cookies.optString(i);
            Log.d("OkCookieAdd",header);
            builder.addHeader("Cookie",header);
        }
        return chain.proceed(builder.build());
    }

//    private HashSet getCookie(String url, String domain) {
//        MMKV mmkv = MMKV.mmkvWithID(COOKIE_PREF, MMKV.MULTI_PROCESS_MODE);
//        if (!TextUtils.isEmpty(url) && mmkv.contains(url)) {
//            return (HashSet) mmkv.getStringSet(url,new HashSet<>());
//        }
//        if (!TextUtils.isEmpty(domain) && mmkv.contains(domain) ) {
//            return (HashSet) mmkv.getStringSet(domain,new HashSet<>());
//        }
//        return new HashSet<>();
//    }

    private JSONArray getCookie(String url, String domain) {
        SharedPreferences sp = NoteApp.getContext().getSharedPreferences("cookies_prefs", Context.MODE_PRIVATE);
        JSONArray array = new JSONArray();
        try {
//            if (!TextUtils.isEmpty(url) && sp.contains(url)) {
//                array = new JSONArray(sp.getString(url,""));
//            }
            if (!TextUtils.isEmpty(domain) && sp.contains(domain) ) {
                array = new JSONArray(sp.getString(domain,""));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return array;
    }

}
