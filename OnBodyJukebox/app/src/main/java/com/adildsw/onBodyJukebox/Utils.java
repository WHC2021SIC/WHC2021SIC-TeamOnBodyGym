package com.adildsw.onBodyJukebox;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.Window;
import android.view.WindowManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class Utils {

    private int PORT = 11996;

    public void getRequest(String uri, Context context, HttpRequestListener requestListener) {
        if (!uri.startsWith("http")) {
            uri = "http://" + uri;
        }

        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(Request.Method.GET, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                requestListener.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                requestListener.onError(error.toString());
            }
        });

        queue.add(request);
    }

    public String validateIP(String ip) {
        if (ip.indexOf(":") < 6) {
            ip = ip + ":" + PORT;
        }

        return ip;
    }

    public void setNotificationBarColor(Window window, String colorString) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor(colorString));
    }

    public void setIPAddress(String ip, Context context) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("ip", ip);
        editor.apply();
    }

    public String getIPAddress(Context context) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPref.getString("ip", "192.168.0.2:11996");
    }

    // modeKey Can hold one of two values: ["music_volume", "grain_volume"]
    public void setVolume(Float volume, String modeKey, Context context) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putFloat(modeKey, volume);
        editor.apply();
    }

    // modeKey Can hold one of two values: ["music_volume", "grain_volume"]
    public Float getVolume(String modeKey, Context context) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPref.getFloat(modeKey, (float) 70);
    }

}
