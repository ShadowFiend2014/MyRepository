package com.test.myapplication.binderpool;

import android.os.IBinder;
import android.os.RemoteException;

import com.test.myapplication.IBinderPool;

public class BinderPoolImpl extends IBinderPool.Stub {
    @Override
    public IBinder queryBinder(int binderCode) throws RemoteException {
        IBinder binder = null;
        switch (binderCode) {
            case BinderPoolProxy.BINDER_SECURITY_CENTER: {
                binder = new SecurityCenterImpl();
                break;
            }
            case BinderPoolProxy.BINDER_COMPUTE: {
                binder = new ComputeImpl();
                break;
            }
            case BinderPoolProxy.BINDER_BOOK_MANAGER: {
                binder = new BookManagerImpl();
            }
            default:
                break;
        }

        return binder;
    }
}
