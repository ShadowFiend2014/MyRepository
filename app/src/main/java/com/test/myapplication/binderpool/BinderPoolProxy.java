package com.test.myapplication.binderpool;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.test.myapplication.IBinderPool;

public class BinderPoolProxy {

    public static final int BINDER_COMPUTE = 0;
    public static final int BINDER_SECURITY_CENTER = 1;
    public static final int BINDER_BOOK_MANAGER = 2;

    public static final String TAG = "BinderPool";

    private Context mContext;
    private IBinderPool mBinderPool;
    private static volatile BinderPoolProxy sInstance;

    private BinderPoolProxy(Context context) {
        mContext = context.getApplicationContext();
        connectBinderPoolService();
    }

    public static BinderPoolProxy getInsance(Context context) {
        if (sInstance == null) {
            synchronized (BinderPoolProxy.class) {
                if (sInstance == null) {
                    sInstance = new BinderPoolProxy(context);
                }
            }
        }
        return sInstance;
    }

    private void connectBinderPoolService() {
        Intent service = new Intent(mContext, BinderPoolService.class);
        mContext.bindService(service, mBinderPoolConnection,
                Context.BIND_AUTO_CREATE);
    }

    public IBinder queryBinder(int binderCode) {
        IBinder binder = null;
        try {
            if (mBinderPool != null) {
                binder = mBinderPool.queryBinder(binderCode);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return binder;
    }

    private ServiceConnection mBinderPoolConnection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, "onServiceDisconnected Thread : " + Thread.currentThread().getName());
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, "onServiceConnected Thread : " + Thread.currentThread().getName());
            mBinderPool = BinderPoolImpl.asInterface(service);
        }
    };
}
