// IBookManager.aidl
package com.test.myapplication;

import com.test.myapplication.Book;
import com.test.myapplication.IOnNewBookArrivedListener;

interface IBookManager {
    void addBook(in Book book);
    List<Book> getBookList();
    void addListener(IOnNewBookArrivedListener listener);
    void removeListener(IOnNewBookArrivedListener listener);
}
