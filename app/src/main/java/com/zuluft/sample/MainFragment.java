package com.zuluft.sample;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zuluft.safeFragmentTransaction.SafeFragmentTransaction;

public class MainFragment
        extends
        Fragment {

    private SafeFragmentTransaction mSafeFragmentTransaction;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mSafeFragmentTransaction = SafeFragmentTransaction.createInstance(getLifecycle(), getChildFragmentManager());
        getLifecycle().addObserver(mSafeFragmentTransaction);
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new Handler().postDelayed(this::drawFragment, 1000);


    }

    private void drawFragment() {
        mSafeFragmentTransaction.registerFragmentTransition(fragmentManager ->
                fragmentManager.beginTransaction().add(R.id.flChildContent, new ChildFragment())
                        .commit());
    }
}
