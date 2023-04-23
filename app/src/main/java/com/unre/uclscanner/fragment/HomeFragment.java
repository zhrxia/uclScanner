package com.unre.uclscanner.fragment;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.tab.QMUIBasicTabSegment;
import com.qmuiteam.qmui.widget.tab.QMUITabBuilder;
import com.qmuiteam.qmui.widget.tab.QMUITabIndicator;
import com.qmuiteam.qmui.widget.tab.QMUITabSegment;

import com.unre.uclscanner.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends BaseFragment {
    private final static String TAG = HomeFragment.class.getSimpleName();

    @BindView(R.id.pager) ViewPager mViewPager;
    @BindView(R.id.tabs) QMUITabSegment mTabSegment;

    TwoFrameLayout mTwo;
    OneFrameLayout mOne;
    private PagerAdapter mPagerAdapter = new PagerAdapter() {

        private int mChildCount = 0;

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Object instantiateItem(final ViewGroup container, int position) {
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            switch (position) {
                case 0:
                    container.addView(mOne, params);
                    return mOne;
                case 1:
                    container.addView(mTwo, params);
                    return mTwo;
            }
            return null;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getItemPosition(Object object) {
            if (mChildCount == 0) {
                return POSITION_NONE;
            }
            return super.getItemPosition(object);
        }

        @Override
        public void notifyDataSetChanged() {
            mChildCount = getCount();
            super.notifyDataSetChanged();
        }
    };


    @Override
    protected View onCreateView() {
        FrameLayout layout = (FrameLayout) LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home, null);
        ButterKnife.bind(this, layout);
        initPagers();
        initTabs();
        return layout;
    }

    private void initTabs() {
        //设置tab
        mTabSegment.setIndicator(new QMUITabIndicator(QMUIDisplayHelper.dp2px(getContext(), 2), false, true));
        int space = QMUIDisplayHelper.dp2px(getContext(), 20);

        mTabSegment.setItemSpaceInScrollMode(space);
        mTabSegment.setPadding(space, 0, space, 0);
        mTabSegment.setMode(QMUITabSegment.MODE_FIXED);
        mTabSegment.setRight(space);

        int size = QMUIDisplayHelper.dp2px(getContext(), 14);

        QMUITabBuilder builder = mTabSegment.tabBuilder();
        builder.setSelectedDrawable(ContextCompat.getDrawable(getContext(), R.mipmap.icon_tabbar_lab_selected))
                .setNormalDrawable(ContextCompat.getDrawable(getContext(), R.mipmap.icon_tabbar_lab))
                .setText("oneTab")
                .setSelectColor(Color.parseColor("#FFBC00"))
                .setNormalColor(Color.parseColor("#5E5F60"))
                .setTextSize(size,size);
        mTabSegment.addTab(builder.build(getContext()));

        builder.setSelectedDrawable(ContextCompat.getDrawable(getContext(), R.mipmap.icon_tabbar_component_selected))
                .setNormalDrawable(ContextCompat.getDrawable(getContext(), R.mipmap.icon_tabbar_component))
                .setSelectColor(Color.parseColor("#FFBC00"))
                .setNormalColor(Color.parseColor("#5E5F60"))
                .setText("twoTab")
                .setTextSize(size,size);
        mTabSegment.addTab(builder.build(getContext()));

        mTabSegment.setupWithViewPager(mViewPager, false);
        mTabSegment.addOnTabSelectedListener(new QMUIBasicTabSegment.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int index) {

            }

            @Override
            public void onTabUnselected(int index) {

            }

            @Override
            public void onTabReselected(int index) {

            }

            @Override
            public void onDoubleTap(int index) {

            }
        });
    }

    private void initPagers() {
        mOne = new OneFrameLayout(getActivity());
        mTwo = new TwoFrameLayout(getActivity());
        mViewPager.setAdapter(mPagerAdapter);
        mTabSegment.setupWithViewPager(mViewPager, false);
    }

}