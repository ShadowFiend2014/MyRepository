package com.test.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

public class IntentFilterActvity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("LWTest", "IntentFilterActvity oncreate");
        setContentView(R.layout.test_filter);
    }
}
