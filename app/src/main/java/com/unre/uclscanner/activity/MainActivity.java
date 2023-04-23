package com.unre.uclscanner.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;

import com.unre.uclscanner.R;
import com.unre.uclscanner.fragment.BaseFragment;
import com.unre.uclscanner.fragment.HomeFragment;

public class MainActivity extends BaseFragmentActivity {
    static {
        System.loadLibrary("uclscanner");
    }

    @Override
    protected int getContextViewId() {
        return R.id.uclscanner;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            BaseFragment fragment = new HomeFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(getContextViewId(), fragment, fragment.getClass().getSimpleName())
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commit();
        }
    }

}
