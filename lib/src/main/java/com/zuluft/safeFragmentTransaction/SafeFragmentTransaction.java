package com.zuluft.safeFragmentTransaction;




import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

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

    @SuppressWarnings("unused")
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
