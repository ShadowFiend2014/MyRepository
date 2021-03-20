package com.test.myapplication.binderpool;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.test.myapplication.IBinderPool;

/**
 * 远程服务，用于提供服务实现
 */
public class BinderPoolService extends Service {

    private Binder mIbinderPool = new BinderPoolImpl();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(BinderPoolProxy.TAG, "onBind");
        return mIbinderPool;
    }

    @Override
    public void onDestroy() {
        Log.i(BinderPoolProxy.TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onCreate() {
        Log.i(BinderPoolProxy.TAG, "onCreate");
        super.onCreate();
    }
}
