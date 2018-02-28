package com.zuluft.sample;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zuluft.impl.SafeFragmentTransactorFragment;


public class MainFragment
        extends
        SafeFragmentTransactorFragment {

    private static final String TAG = "MainFragment";

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
        Log.d(TAG, "onViewCreated: ");

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getChildFragmentManager().findFragmentById(R.id.flChildContent) == null) {
            new Handler().postDelayed(this::drawFragment, 5000);
        }
    }

    private void drawFragment() {
        getSafeFragmentTransaction().registerFragmentTransition(fragmentManager ->
                fragmentManager.beginTransaction().add(R.id.flChildContent, new ChildFragment())
                        .commit());
    }
}
