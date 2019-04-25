package com.zuluft.impl;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.zuluft.safeFragmentTransaction.SafeFragmentTransaction;

public abstract class SafeFragmentTransactorFragment
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


    @SuppressWarnings({"unused", "WeakerAccess"})
    public final SafeFragmentTransaction getSafeFragmentTransaction() {
        if (mSafeFragmentTransaction == null) {
            throw new RuntimeException(
                    "getSafeFragmentTransaction() method was called before super.onActivityCreated"
            );
        }
        return mSafeFragmentTransaction;
    }

    @Override
    public void onDestroyView() {
        if (mSafeFragmentTransaction != null) {
            mSafeFragmentTransaction.detachLifecycle();
        }
        super.onDestroyView();
    }
}
