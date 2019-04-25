package com.zuluft.impl;



import androidx.annotation.Nullable;

import com.zuluft.safeFragmentTransaction.SafeFragmentTransaction;

final class CustomNonConfigInstance {

    final SafeFragmentTransaction safeFragmentTransaction;
    final Object other;

    CustomNonConfigInstance(@Nullable final SafeFragmentTransaction safeFragmentTransaction,
                            @Nullable final Object other) {
        this.safeFragmentTransaction = safeFragmentTransaction;
        this.other = other;
    }
}
