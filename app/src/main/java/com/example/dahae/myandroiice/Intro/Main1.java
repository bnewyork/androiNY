package com.example.dahae.myandroiice.Intro;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dahae.myandroiice.R;

public class Main1 extends Fragment {

    int fragNum;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragNum = getArguments() != null ? getArguments().getInt("val") : 1;
    }

    public static Main1 init(int val) {
        Main1 fragment = new Main1();
        // Supply val input as an argument.
        Bundle args = new Bundle();
        args.putInt("val", val);
        fragment.setArguments(args);
        return fragment;
    }
    /**
     * The Fragment's UI is a list.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.introformain1, null);
        return layoutView;
    }

}
