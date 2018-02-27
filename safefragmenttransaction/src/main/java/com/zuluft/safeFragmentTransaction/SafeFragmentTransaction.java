package com.zuluft.safeFragmentTransaction;


import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import java.util.Stack;

public class SafeFragmentTransaction
        implements
        LifecycleObserver {

    private Lifecycle mLifecycle;
    private FragmentManager mFragmentManager;
    private final Stack<TransitionHandler> mFragmentTransitionsStack;

    public static SafeFragmentTransaction createInstance() {
        return new SafeFragmentTransaction();
    }

    private SafeFragmentTransaction() {
        this.mFragmentTransitionsStack = new Stack<>();
    }

    private void onTransitionRegistered(SafeFragmentTransaction
                                                .TransitionHandler transition) {
        mFragmentTransitionsStack.add(transition);
        if (mLifecycle != null && mFragmentManager != null &&
                mLifecycle.getCurrentState()
                        .isAtLeast(Lifecycle.State.RESUMED)) {
            doTransactions();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onReadyToDoPendingTransactions() {
        doTransactions();
    }

    private synchronized void doTransactions() {
        while (mFragmentTransitionsStack.size() != 0) {
            TransitionHandler transition = mFragmentTransitionsStack.pop();
            transition.onTransitionAvailable(mFragmentManager);
        }
    }

    public void registerFragmentTransition(TransitionHandler transitionHandler) {
        onTransitionRegistered(transitionHandler);
    }

    public void detachLifecycle() {
        mLifecycle.removeObserver(this);
        mLifecycle = null;
        mFragmentManager = null;
    }

    public void attachLifecycle(@NonNull final Lifecycle lifecycle,
                                @NonNull final FragmentManager fragmentManager) {
        this.mLifecycle = lifecycle;
        this.mFragmentManager = fragmentManager;
        lifecycle.addObserver(this);
    }

    @FunctionalInterface
    public interface TransitionHandler {
        void onTransitionAvailable(final FragmentManager fragmentManager);
    }
}
