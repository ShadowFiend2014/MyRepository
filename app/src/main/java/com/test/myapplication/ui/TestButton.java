package com.test.myapplication.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class TestButton extends TextView {

    private static final String TAG = "LWTest";

    private int mScaledTouchSlop;

    // Record last x,y
    private int mLastX;
    private int mLastY;

    public TestButton(Context context) {
        this(context, null);
    }

    public TestButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TestButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mScaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        Log.i(TAG, "mScaledTouchSlop : " + mScaledTouchSlop);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        Log.i(TAG, "onTouchEvent, x : " + x + " y : " + y);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                Log.i(TAG, "move, deltaX : " + deltaX + " deltaY : " + deltaY);
                int translationX = (int) getTranslationX() + deltaX;
                int translationY = (int) getTranslationY() + deltaY;
                setTranslationX(translationX);
                setTranslationY(translationY);
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }

        mLastX = x;
        mLastY = y;
        Log.i(TAG, "onTouchEvent, mLastY : " + mLastX + " mLastY : " + mLastY);

        return true;
    }
}
