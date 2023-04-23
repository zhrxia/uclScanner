package com.unre.uclscanner.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.unre.uclscanner.fragment.BaseFragment;

public abstract class BaseFragmentActivity extends AppCompatActivity {
    private static final String TAG = "BaseFragmentActivity";
    private FrameLayout mFragmentContainer;

    @SuppressWarnings("SameReturnValue")
    protected abstract int getContextViewId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QMUIStatusBarHelper.translucent(this);
        mFragmentContainer = new FrameLayout(this);
        mFragmentContainer.setId(getContextViewId());
        setContentView(mFragmentContainer);
    }

    @Override
    public void onBackPressed() {
        BaseFragment fragment = getCurrentFragment();
        if (fragment != null) {
            popBackStack();
        }
    }

    /**
     * 获取当前的 Fragment。
     */
    public BaseFragment getCurrentFragment() {
        return (BaseFragment) getSupportFragmentManager().findFragmentById(getContextViewId());
    }

    public void startFragment(BaseFragment fragment) {
        Log.i(TAG, "startFragment");
        BaseFragment.TransitionConfig transitionConfig = fragment.onFetchTransitionConfig();
        String tagName = fragment.getClass().getSimpleName();
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(transitionConfig.enter, transitionConfig.exit, transitionConfig.popenter, transitionConfig.popout)
                .replace(getContextViewId(), fragment, tagName)
                .addToBackStack(tagName)
                .commit();
    }

    /**
     * 退出当前的 Fragment。
     */
    public void popBackStack() {
        Log.i(TAG, "popBackStack: getSupportFragmentManager().getBackStackEntryCount() = " + getSupportFragmentManager().getBackStackEntryCount());
        if (getSupportFragmentManager().getBackStackEntryCount() <= 1) {
            BaseFragment fragment = getCurrentFragment();
            if (fragment == null) {
                finish();
                return;
            }
            BaseFragment.TransitionConfig transitionConfig = fragment.onFetchTransitionConfig();
            Object toExec = fragment.onLastFragmentFinish();
            if (toExec != null) {
                if (toExec instanceof BaseFragment) {
                    BaseFragment mFragment = (BaseFragment) toExec;
                    startFragment(mFragment);
                } else if (toExec instanceof Intent) {
                    Intent intent = (Intent) toExec;
                    finish();
                    startActivity(intent);
                    overridePendingTransition(transitionConfig.popenter, transitionConfig.popout);
                } else {
                    throw new Error("can not handle the result in onLastFragmentFinish");
                }
            } else {
                finish();
                overridePendingTransition(transitionConfig.popenter, transitionConfig.popout);
            }
        } else {
            getSupportFragmentManager().popBackStackImmediate();
        }
    }

    /**
     * <pre>
     * 返回到clazz类型的Fragment，
     * 如 Home --> List --> Detail，
     * popBackStack(Home.class)之后，就是Home
     *
     * 如果堆栈没有clazz或者就是当前的clazz（如上例的popBackStack(Detail.class)），就相当于popBackStack()
     * </pre>
     */
    public void popBackStack(Class<? extends BaseFragment> clazz) {
        getSupportFragmentManager().popBackStack(clazz.getSimpleName(), 0);
    }

    /**
     * <pre>
     * 返回到非clazz类型的Fragment
     *
     * 如果上一个是目标clazz，则会继续pop，直到上一个不是clazz。
     * </pre>
     */
    public void popBackStackInclusive(Class<? extends BaseFragment> clazz) {
        getSupportFragmentManager().popBackStack(clazz.getSimpleName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}