package com.zuluft.safeFragmentTransaction.components;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.zuluft.safeFragmentTransaction.SafeFragmentTransaction;

public class SafeFragmentTransactorActivity
        extends
        AppCompatActivity {

    private SafeFragmentTransaction mSafeFragmentTransaction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSafeFragmentTransaction = SafeFragmentTransaction.createInstance(getLifecycle(),
                getSupportFragmentManager());
        getLifecycle().addObserver(mSafeFragmentTransaction);
    }

    public final SafeFragmentTransaction getSafeFragmentTransaction() {
        if (mSafeFragmentTransaction == null) {
            throw new RuntimeException(
                    "getSafeFragmentTransaction() method was called before super.onCreate"
            );
        }
        return mSafeFragmentTransaction;
    }
}
