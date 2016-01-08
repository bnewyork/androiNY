package com.example.dahae.myandroiice.NewPlan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dahae.myandroiice.Adapter.Trees;
import com.example.dahae.myandroiice.R;

import java.util.ArrayList;
import java.util.Iterator;

public class NewPlanConfig extends Fragment {

    EditText planName;

    public static NewPlanConfig init(int pageNumber) {
        NewPlanConfig fragment2 = new NewPlanConfig();
        Bundle args = new Bundle();
        args.putInt("page", pageNumber);
        fragment2.setArguments(args);
        return fragment2;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.new_plan_config, null);
        planName = (EditText) view.findViewById(R.id.planNameInput);

        return view;
    }

}
