package com.trinhtien2212.findhomerental.ui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trinhtien2212.findhomerental.MainActivity;
import com.trinhtien2212.findhomerental.R;


public class ContactFragment extends Fragment {

    private View view;
    private MainActivity mainActivity;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_contact, container, false);
        //Todo Hang
//        textZalo=view.findViewById(R.id.text_zalo);
        mainActivity = (MainActivity) getActivity();



        return view;
    }



}