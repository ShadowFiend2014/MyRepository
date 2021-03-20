package com.test.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.test.myapplication.binderpool.BinderPoolProxy;
import com.test.myapplication.binderpool.BookManagerImpl;
import com.test.myapplication.binderpool.ComputeImpl;
import com.test.myapplication.binderpool.SecurityCenterImpl;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button mTextView;
    private TextView mTestButton;
    ObjectAnimator mOa;

    BinderPoolProxy mBinderProxy;
    ICompute mComputeServer;
    ISecurityCenter mSecurityServer;
    IBookManager mBookManager;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = findViewById(R.id.test);
        mTestButton = findViewById(R.id.test1);
        mBinderProxy = BinderPoolProxy.getInsance(MainActivity.this);

        mTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("LWTest", "mTestButton onClick ");
            }
        });
        mTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.i("LWTest", "onLongClick " + mTextView.getX());
                return true;
            }
        });

        findViewById(R.id.start_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick ");
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, SecondActivity.class);
                intent.putExtra("time", System.currentTimeMillis());
                startActivity(intent);
            }
        });

        findViewById(R.id.testbinder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("BinderPool", "onClick Thread:"+Thread.currentThread().getName());
                IBinder securityBinder = mBinderProxy
                        .queryBinder(BinderPoolProxy.BINDER_SECURITY_CENTER);
                mSecurityServer = SecurityCenterImpl.asInterface(securityBinder);

                IBinder computeBinder = mBinderProxy
                        .queryBinder(BinderPoolProxy.BINDER_COMPUTE);
                mComputeServer = ComputeImpl.asInterface(computeBinder);
                if (mComputeServer == null || mSecurityServer == null) {
                    Log.i("BinderPool", "mComputeServer mSecurityServer null");
                    return;
                }

                try {
                    Log.d("BinderPool", "visit ISecurityCenter");
                    String msg = "helloworld-安卓";
                    Log.d("BinderPool", "content:" + msg);
                    String password = mSecurityServer.encrypt(msg);
                    Log.d("BinderPool", "encrypt:" + password);
                    Log.d("BinderPool", "decrypt:" + mSecurityServer.decrypt(password));

                    Log.d("BinderPool", "visit ICompute");
                    Log.d("BinderPool", "3+5=" + mComputeServer.add(3, 5));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.add_book).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("BinderPool", "onClick add_book Thread:"+Thread.currentThread().getName());
                if (mBookManager == null) {
                    IBinder securityBinder = mBinderProxy
                            .queryBinder(BinderPoolProxy.BINDER_BOOK_MANAGER);
                    mBookManager = BookManagerImpl.asInterface(securityBinder);
                    try {
                        mBookManager.addListener(mOnNewBookArrivedListener);
                        mBookManager.asBinder().linkToDeath(mDeathRecipient, 0);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                Log.i("BinderPool", "mBookManager : " + mBookManager);
                if (mBookManager == null) {
                    Log.i("BinderPool", "mBookManager null");
                    return;
                }

                try {
                    Log.d("BinderPool", "visit IBookManager");
                    Random random = new Random();
                    int bookId = random.nextInt(1000);
                    Book book = new Book("Android development " + bookId, bookId);
                    Log.d("BinderPool", "addBook book:"+book);
                    mBookManager.addBook(book);
                    Log.d("BinderPool", "addBook called success");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        findViewById(R.id.get_books).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("BinderPool", "onClick get_books");
                if (mBookManager == null) {
                    IBinder securityBinder = mBinderProxy
                            .queryBinder(BinderPoolProxy.BINDER_BOOK_MANAGER);
                    mBookManager = BookManagerImpl.asInterface(securityBinder);
                }
                Log.i("BinderPool", "mBookManager : " + mBookManager);
                if (mBookManager == null) {
                    Log.i("BinderPool", "mBookManager null");
                    return;
                }

                try {
                    Log.d("BinderPool", "visit BookManagerImpl");
                    List<Book> bookList = mBookManager.getBookList();
                    if (bookList != null) {
                        Log.d("BinderPool", "getBookList ： " + bookList.toString());
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    /*Log.i("LWTest", "startActivity");
                    Intent intent = new Intent();
                    intent.setAction("com.test.action.INTENT");
                    intent.setDataAndType(Uri.parse("content://abc"), "text/plain");
                    ComponentName cp = intent.resolveActivity(getPackageManager());
                    Log.i("LWTest", "ComponentName : " + cp);
                    MainActivity.this.startActivity(intent);*/
                    Log.i("LWTest", "X = " + mTextView.getX());
                    Log.i("LWTest", "Y = " + mTextView.getY());
                    Log.i("LWTest", "getScaleX = " + mTextView.getScaleX());
                    Log.i("LWTest", "getScaleY = " + mTextView.getScaleY());
                    Log.i("LWTest", "getScrollX = " + mTextView.getScrollX());
                    Log.i("LWTest", "getScrollY = " + mTextView.getScrollY());
                    Log.i("LWTest", "getLeft = " + mTextView.getLeft());
                    Log.i("LWTest", "getTop = " + mTextView.getTop());
                    Log.i("LWTest", "getRight = " + mTextView.getRight());
                    Log.i("LWTest", "getBottom = " + mTextView.getBottom());
                    Log.i("LWTest", "getTranslationX = " + mTextView.getTranslationX());
                    Log.i("LWTest", "getTranslationY = " + mTextView.getTranslationY());
                } catch (Exception e) {
                    Log.e("LWTest", "Not found activity", e);
                }
            }
        });
//        mOa = ObjectAnimator.ofFloat(mTextView, "rotation", 0, 360)
//                .setDuration(2000);
//        mOa.setRepeatCount(ValueAnimator.INFINITE);
//        mOa.start();
//        ObjectAnimator.ofFloat(mTextView, "translationY", 0, 100)
//                .setDuration(2000)
//                .start();
        Log.i("LWTest", "X = " + mTextView.getX());
        Log.i("LWTest", "Y = " + mTextView.getY());
        Log.i("LWTest", "getScaleX = " + mTextView.getScaleX());
        Log.i("LWTest", "getScaleY = " + mTextView.getScaleY());
        Log.i("LWTest", "getScrollX = " + mTextView.getScrollX());
        Log.i("LWTest", "getScrollY = " + mTextView.getScrollY());
        Log.i("LWTest", "getLeft = " + mTextView.getLeft());
        Log.i("LWTest", "getTop = " + mTextView.getTop());
        Log.i("LWTest", "getRight = " + mTextView.getRight());
        Log.i("LWTest", "getBottom = " + mTextView.getBottom());
        Log.i("LWTest", "getTranslationX = " + mTextView.getTranslationX());
        Log.i("LWTest", "getTranslationY = " + mTextView.getTranslationY());
    }

    private IOnNewBookArrivedListener mOnNewBookArrivedListener = new IOnNewBookArrivedListener.Stub() {
        @Override
        public void onNewBookArrived(Book newBook) throws RemoteException {
            Log.d("BinderPool", "onNewBookArrived : "+ newBook+" Thread:"+Thread.currentThread().getName());
        }
    };

    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            Log.d("BinderPool", "binder died Thread:"+Thread.currentThread().getName());
            //TODO reconnect
        }
    };

    @Override
    protected void onNewIntent(Intent intent) {
        Log.i(TAG, "onNewIntent");
        super.onNewIntent(intent);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        Log.i(TAG, "onRestoreInstanceState");
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        Log.i(TAG, "onSaveInstanceState");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "onResume");
        super.onResume();
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onStart() {
        Log.i(TAG, "onStart");
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy");
        super.onDestroy();
        if (mBookManager != null) {
            try {
                mBookManager.removeListener(mOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
//        if (mOa != null) {
//            mOa.cancel();
//        }
    }
}
