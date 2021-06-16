package com.adildsw.onBodyJukebox;

public interface HttpRequestListener {
    void onError(String error);
    void onResponse(Object response);
}
