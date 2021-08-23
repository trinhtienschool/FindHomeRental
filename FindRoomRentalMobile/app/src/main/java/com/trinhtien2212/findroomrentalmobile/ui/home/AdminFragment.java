package com.trinhtien2212.findroomrentalmobile.ui.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.trinhtien2212.findroomrentalmobile.MainActivity;
import com.trinhtien2212.findroomrentalmobile.MainAdminActivity;
import com.trinhtien2212.findroomrentalmobile.R;
import com.trinhtien2212.findroomrentalmobile.RoomListActivity;
import com.trinhtien2212.findroomrentalmobile.UserListActivity;


public class AdminFragment extends Fragment {
    private MainActivity mainActivity;
    private View view;
    Button btnUser, btnRoom;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_admin, container, false);
        mainActivity = (MainActivity) getActivity();
        btnUser = view.findViewById(R.id.buttonUserManagement);
        btnRoom = view.findViewById(R.id.buttonRoomManagement);

        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainActivity, UserListActivity.class);
                startActivity(intent);
            }
        });

        btnRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainActivity, RoomListActivity.class);
                startActivity(intent);
            }
        });
        Log.e("Ket thuc onCreate","ONCreated");


        return view;
    }
}