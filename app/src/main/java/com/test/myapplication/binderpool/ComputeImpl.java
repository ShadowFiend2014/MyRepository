package com.test.myapplication.binderpool;

import android.os.RemoteException;

import com.test.myapplication.ICompute;

public class ComputeImpl extends ICompute.Stub {
    @Override
    public int add(int a, int b) throws RemoteException {
        return a + b;
    }
}
