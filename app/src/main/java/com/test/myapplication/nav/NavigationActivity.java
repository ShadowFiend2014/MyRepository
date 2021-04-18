package com.test.myapplication.nav;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.test.myapplication.R;

public class NavigationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.navigation_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);

        // 获取 FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();
        // 开启一个 fragment 事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 向 FrameLayout 容器添加 MainFragment ，现在并未执行
        transaction.add(R.id.frame_main, MainFragment.newInstance(), MainFragment.class.getName());
        // 提交事务，真正执行添加动作
        transaction.commit();
    }
}
