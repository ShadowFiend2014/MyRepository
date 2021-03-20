// IOnNewBookArrivedListener.aidl
package com.test.myapplication;

import com.test.myapplication.Book;

interface IOnNewBookArrivedListener {
    void onNewBookArrived(in Book newBook);
}