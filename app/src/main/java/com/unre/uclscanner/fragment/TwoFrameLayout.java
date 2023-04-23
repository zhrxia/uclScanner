package com.unre.uclscanner.fragment;
import androidx.annotation.NonNull;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.unre.uclscanner.R;

import butterknife.ButterKnife;

public class TwoFrameLayout extends FrameLayout {

    public TwoFrameLayout(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.view_hometwo, this);
        ButterKnife.bind(this);
    }
}