package com.test.myapplication.nav;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.test.myapplication.R;

public class Fragment1 extends Fragment {

    public static Fragment1 newInstance() {
        return new Fragment1();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        switchFragment(Fragment1.class.getName(), Fragment1.newInstance());
    }

    private void switchFragment(final String oldTag, Fragment1 newFragment) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        Fragment oldFragment = getChildFragmentManager().findFragmentByTag(oldTag);
        if (oldFragment != null) {
            transaction.hide(oldFragment);
        }
        Fragment newTagFragment = getChildFragmentManager().findFragmentByTag(newFragment.getTag());
        if (newTagFragment == null) {
//            transaction.add(R.id.childFrameLayout, newFragment, newFragment.getClass().getName());
            transaction.show(newFragment);
            transaction.addToBackStack(newFragment.getClass().getName());
        } else {
            getChildFragmentManager().popBackStackImmediate(newFragment.getId(), 0);
        }
        transaction.commitAllowingStateLoss();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_1, container, false);
    }
}
