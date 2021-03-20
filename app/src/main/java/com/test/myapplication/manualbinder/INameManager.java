package com.test.myapplication.manualbinder;

import android.os.IInterface;
import android.os.RemoteException;

import java.util.List;

public interface INameManager extends IInterface {
    public void addName(String name) throws RemoteException;
    public List<String> getNames() throws RemoteException;
}
