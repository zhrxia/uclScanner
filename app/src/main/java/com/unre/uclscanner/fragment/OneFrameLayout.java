package com.unre.uclscanner.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.unre.uclscanner.R;

import butterknife.ButterKnife;

public class OneFrameLayout extends FrameLayout {
    public OneFrameLayout(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.view_homeone, this);
        ButterKnife.bind(this);
    }
}
