package com.zuluft.sample;

import android.os.Bundle;
import android.view.View;

import com.zuluft.impl.SafeFragmentTransactorActivity;


public class MainActivity
        extends
        SafeFragmentTransactorActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportFragmentManager().findFragmentById(R.id.flMainContent) == null) {
            drawFragment();
        }
    }

    private void drawFragment() {
        getSafeFragmentTransaction().registerFragmentTransition(fragmentManager ->
                fragmentManager.beginTransaction().add(R.id.flMainContent, new MainFragment())
                        .commit());
    }

    public void onNextClicked(View view) {
        getSafeFragmentTransaction().registerFragmentTransition(fragmentManager ->
                fragmentManager.beginTransaction().replace(R.id.flMainContent,
                        TestFragment.newInstance(), "TAG2")
                        .addToBackStack(null)
                        .commit());
    }
}
