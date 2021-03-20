package com.test.myapplication.binderpool;

import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import com.test.myapplication.Book;
import com.test.myapplication.IBookManager;
import com.test.myapplication.IOnNewBookArrivedListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BookManagerImpl extends IBookManager.Stub {

    private CopyOnWriteArrayList<Book> mBookList;
    private RemoteCallbackList<IOnNewBookArrivedListener> mOnNewBookArrivedListeners;

    public BookManagerImpl() {
        mBookList = new CopyOnWriteArrayList<>();
        mOnNewBookArrivedListeners = new RemoteCallbackList<>();
    }

    @Override
    public void addBook(Book book) throws RemoteException {
        mBookList.add(book);
        onNewBookArrived(book);
    }

    @Override
    public List<Book> getBookList() throws RemoteException {
        return mBookList;
    }

    @Override
    public void addListener(IOnNewBookArrivedListener listener) throws RemoteException {
        Log.i("BinderPool", "addListener listener : " + listener);
        mOnNewBookArrivedListeners.register(listener);
        final int N = mOnNewBookArrivedListeners.beginBroadcast();
        mOnNewBookArrivedListeners.finishBroadcast();
        Log.d("BinderPool", "addListener, current size:" + N);
    }

    @Override
    public void removeListener(IOnNewBookArrivedListener listener) throws RemoteException {
        Log.i("BinderPool", "removeListener listener : " + listener);
        mOnNewBookArrivedListeners.unregister(listener);
        final int N = mOnNewBookArrivedListeners.beginBroadcast();
        mOnNewBookArrivedListeners.finishBroadcast();
        Log.d("BinderPool", "removeListener, current size:" + N);
    }

    public void onNewBookArrived(Book book) throws RemoteException {
        final int N = mOnNewBookArrivedListeners.beginBroadcast();
        for (int i = 0; i < N; i++) {
            IOnNewBookArrivedListener listener = mOnNewBookArrivedListeners.getBroadcastItem(i);
            Log.i("BinderPool", "onNewBookArrived listener " + i + " :" + listener);
            if (listener != null) {
                listener.onNewBookArrived(book);
            }
        }
        mOnNewBookArrivedListeners.finishBroadcast();
    }
}
