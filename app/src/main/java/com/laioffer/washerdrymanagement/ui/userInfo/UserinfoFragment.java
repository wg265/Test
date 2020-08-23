package com.laioffer.washerdrymanagement.ui.userInfo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.laioffer.washerdrymanagement.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserinfoFragment extends Fragment {

    public UserinfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_userinfo, container, false);
    }
}
