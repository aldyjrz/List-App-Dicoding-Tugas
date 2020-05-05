package com.aldyjrz.mountaindo;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;


public class BaseApp
        extends Application {
    public static final String TAG = BaseApp.class.getSimpleName();
    private static BaseApp mInstance;
    private LinkedList<Activity> activities = new LinkedList();
    private RequestQueue mRequestQueue;
    @NotNull

    public void removeActivity(Activity activity) {
        activities.remove(activity);

    }
    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static BaseApp obtainApp(Context context) {
        return (BaseApp) context.getApplicationContext();
    }

    public static synchronized BaseApp getInstance() {
        return mInstance;
    }
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }


    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
