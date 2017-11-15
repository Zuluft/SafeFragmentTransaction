package com.zuluft.safeFragmentTransaction;


import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.v4.app.FragmentManager;

import java.util.Stack;

public class SafeFragmentTransaction
        implements
        LifecycleObserver {

    private final Lifecycle mLifecycle;
    private final FragmentManager mFragmentManager;
    private final Stack<TransitionHandler> mFragmentTransitionsStack;

    public static SafeFragmentTransaction createInstance(final Lifecycle lifecycle,
                                                         final FragmentManager fragmentManager) {
        return new SafeFragmentTransaction(lifecycle, fragmentManager);
    }

    private SafeFragmentTransaction(final Lifecycle lifecycle,
                                    final FragmentManager fragmentManager) {
        this.mLifecycle = lifecycle;
        this.mFragmentManager = fragmentManager;
        this.mFragmentTransitionsStack = new Stack<>();
    }

    private void onTransitionRegistered(SafeFragmentTransaction
                                                .TransitionHandler transition) {
        mFragmentTransitionsStack.add(transition);
        if (mLifecycle.getCurrentState()
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

    @FunctionalInterface
    public interface TransitionHandler {
        void onTransitionAvailable(final FragmentManager fragmentManager);
    }
}
