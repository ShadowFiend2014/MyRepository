package com.test.myapplication.nav;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.test.myapplication.R;

public class MainFragment extends Fragment {

    private BottomNavigationView mBottomNavigationView;

    private Fragment[] mFragments;

    private int mLastShowFragment = 0;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBottomNavigationView = view.findViewById(R.id.bottomNavigationView);
//        BottomNavigationViewHelper
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        initFragments();
    }

    private void initFragments() {
        mFragments = new Fragment[]{Fragment1.newInstance(),
                Fragment2.newInstance(), Fragment3.newInstance(),
                Fragment4.newInstance()};
        mLastShowFragment = 1;
        getChildFragmentManager()
                .beginTransaction()
                .add(R.id.frameLayout, mFragments[mLastShowFragment], mFragments[mLastShowFragment].getClass().getName())
                .show(mFragments[mLastShowFragment])
                .commit();
    }

    BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.fragment1:
                    if (mLastShowFragment != 0) {
                        switchFragment(0);
                        mLastShowFragment = 0;
                    }
                    return true;
                case R.id.fragment2:
                    if (mLastShowFragment != 1) {
                        switchFragment(1);
                        mLastShowFragment = 1;
                    }
                    return true;
                case R.id.fragment3:
                    if (mLastShowFragment != 2) {
                        switchFragment(2);
                        mLastShowFragment = 2;
                    }
                    return true;
                case R.id.fragment4:
                    if (mLastShowFragment != 3) {
                        switchFragment(3);
                        mLastShowFragment = 3;
                    }
                    return true;
                default:
                    return false;
            }
        }
    };

    private void switchFragment(final int index) {
        if (mLastShowFragment != index) {
            switchFragmentInner(mLastShowFragment, index);
            mLastShowFragment = index;
        }
    }

    private void switchFragmentInner(final int lastIndex, final int index) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.hide(mFragments[lastIndex]);

        if (!mFragments[index].isAdded()) {
            transaction.add(R.id.frameLayout, mFragments[index], mFragments[index].getClass().getName());
        }

        transaction.show(mFragments[index]).commitAllowingStateLoss();
    }
}
