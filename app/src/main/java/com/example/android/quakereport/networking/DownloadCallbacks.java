package com.example.android.quakereport.networking;

/**
 * Created by saunakc on 02/03/17.
 */

public interface DownloadCallbacks<T> {
    public void onSuccess(EarthQuakeAsyncTask.Result result);
    public void onFailed();
}
