package com.test.myapplication.manualbinder;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class NameManagerImpl extends Binder implements INameManager {

    public static final String DESCRIPTOR = "com.test.myapplication.manualbinder.INameManager";
    public static final int TRANSACTION_addName = IBinder.FIRST_CALL_TRANSACTION + 0;
    public static final int TRANSACTION_getNames = IBinder.FIRST_CALL_TRANSACTION + 1;

    public NameManagerImpl() {
        this.attachInterface(this, DESCRIPTOR);
    }

    public static INameManager asInterface(IBinder obj) {
        if (obj == null) {
            return null;
        }
        IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
        if (iin != null && iin instanceof INameManager) {
            return (INameManager) iin;
        }
        return new NameManagerImpl.Proxy(obj);
    }

    @Override
    public IBinder asBinder() {
        return this;
    }

    @Override
    protected boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags) throws RemoteException {
        switch (code) {
            case INTERFACE_TRANSACTION:
                reply.writeString(DESCRIPTOR);
                return true;
            case TRANSACTION_addName:
                data.enforceInterface(DESCRIPTOR);
                String name = data.readString();
                this.addName(name);
                reply.writeNoException();
                return true;
            case TRANSACTION_getNames:
                data.enforceInterface(DESCRIPTOR);
                List<String> result = this.getNames();
                reply.writeNoException();
                reply.writeStringList(result);
                return true;
        }
        return super.onTransact(code, data, reply, flags);
    }

    @Override
    public void addName(String name) throws RemoteException {
        // TODO
    }

    @Override
    public List<String> getNames() throws RemoteException {
        // TODO
        return null;
    }

    private static class Proxy implements INameManager {

        private IBinder mRemote;

        public Proxy(IBinder mRemote) {
            this.mRemote = mRemote;
        }

        @Override
        public void addName(String name) throws RemoteException {
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();
            try {
                data.writeInterfaceToken(DESCRIPTOR);
                if (name != null) {
                    data.writeString(name);
                    mRemote.transact(TRANSACTION_addName, data, reply, 0);
                    reply.readException();
                }
            } finally {
                data.recycle();
                reply.recycle();
            }
        }

        @Override
        public List<String> getNames() throws RemoteException {
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();
            List<String> result = null;
            try {
                data.writeInterfaceToken(DESCRIPTOR);
                mRemote.transact(TRANSACTION_getNames, data, reply, 0);
                reply.readException();
                result = reply.createStringArrayList();
            } finally {
                data.recycle();
                reply.recycle();
            }
            return result;
        }

        @Override
        public IBinder asBinder() {
            return mRemote;
        }

        public String getInterfaceDescriptor() {
            return DESCRIPTOR;
        }
    }
}
