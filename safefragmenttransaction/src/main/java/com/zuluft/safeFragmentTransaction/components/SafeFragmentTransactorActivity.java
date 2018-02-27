package com.zuluft.safeFragmentTransaction.components;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.zuluft.safeFragmentTransaction.SafeFragmentTransaction;

public abstract class SafeFragmentTransactorActivity
        extends
        AppCompatActivity {

    private SafeFragmentTransaction mSafeFragmentTransaction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Object nonConfigInstance = getLastCustomNonConfigurationInstance();
        if (nonConfigInstance == null || ((CustomNonConfigInstance) nonConfigInstance)
                .safeFragmentTransaction == null) {
            mSafeFragmentTransaction = SafeFragmentTransaction
                    .createInstance();
        } else {
            mSafeFragmentTransaction = ((CustomNonConfigInstance) nonConfigInstance)
                    .safeFragmentTransaction;
        }
        mSafeFragmentTransaction.attachLifecycle(getLifecycle(),
                getSupportFragmentManager());
    }

    public final SafeFragmentTransaction getSafeFragmentTransaction() {
        if (mSafeFragmentTransaction == null) {
            throw new RuntimeException(
                    "getSafeFragmentTransaction() method was called before super.onCreate"
            );
        }
        return mSafeFragmentTransaction;
    }


    public Object onRetainOtherNonConfigInstance() {
        return null;
    }

    @SuppressWarnings("unused")
    @Nullable
    public final Object getLastOtherNonConfigInstance() {
        return ((CustomNonConfigInstance) getLastCustomNonConfigurationInstance()).other;
    }

    @Override
    public final Object onRetainCustomNonConfigurationInstance() {
        return new CustomNonConfigInstance(
                mSafeFragmentTransaction,
                onRetainOtherNonConfigInstance()
        );
    }

    @Override
    protected void onDestroy() {
        if (mSafeFragmentTransaction != null) {
            mSafeFragmentTransaction.detachLifecycle();
        }
        super.onDestroy();
    }
}
