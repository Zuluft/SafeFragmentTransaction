package com.zuluft.sample;

import android.os.Bundle;
import android.os.Handler;

import com.zuluft.safeFragmentTransaction.components.SafeFragmentTransactorActivity;

public class MainActivity
        extends
        SafeFragmentTransactorActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(this::drawFragment, 1000);
    }

    private void drawFragment() {
        getSafeFragmentTransaction().registerFragmentTransition(fragmentManager ->
                fragmentManager.beginTransaction().add(R.id.flMainContent, new MainFragment())
                        .commit());
    }
}
