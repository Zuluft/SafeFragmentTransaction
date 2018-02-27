package com.zuluft.sample;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mSafeFragmentTransaction == null) {
            mSafeFragmentTransaction = SafeFragmentTransaction
                    .createInstance();
        }
        mSafeFragmentTransaction.attachLifecycle(getLifecycle(), getChildFragmentManager());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            new Handler().postDelayed(this::drawFragment, 5000);
        }


    }

    private void drawFragment() {
        mSafeFragmentTransaction.registerFragmentTransition(fragmentManager ->
                fragmentManager.beginTransaction().add(R.id.flChildContent, new ChildFragment())
                        .commit());
    }

    @Override
    public void onDestroyView() {
        mSafeFragmentTransaction.detachLifecycle();
        super.onDestroyView();
    }
}
