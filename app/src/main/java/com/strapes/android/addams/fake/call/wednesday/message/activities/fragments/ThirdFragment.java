package com.strapes.android.addams.fake.call.wednesday.message.activities.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.strapes.android.addams.fake.call.R;
import com.strapes.android.addams.fake.call.wednesday.message.activities.PrivacyScreen;


public class ThirdFragment extends Fragment {
    private TextView textView1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.third_fragament, container, false);

        textView1 = (TextView) view.findViewById(R.id.text1);
        PrivacyScreen.Companion.buttonenable();
        return view;
    }
}
