package com.trinhtien2212.findhomerental;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.trinhtien2212.findhomerental.ui.Util;
import com.trinhtien2212.findhomerental.ui.home.IGetMyLocation;

import java.util.Locale;

public class MainAdminActivity extends AppCompatActivity implements IGetMyLocation {

    Button btnUser, btnRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);
        Log.e("Dang vao onCreate","ONCreated");
        btnUser = findViewById(R.id.buttonUserManagement);
        btnRoom = findViewById(R.id.buttonRoomManagement);

        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Util.checkNetwork(MainAdminActivity.this,MainAdminActivity.this)) {
                    Intent intent = new Intent(MainAdminActivity.this, UserListActivity.class);
                    startActivity(intent);
                }
            }
        });

        btnRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Util.checkNetwork(MainAdminActivity.this,MainAdminActivity.this)) {
                    Intent intent = new Intent(MainAdminActivity.this, RoomListActivity.class);
                    startActivity(intent);
                }
            }
        });
        Log.e("Ket thuc onCreate","ONCreated");

    }

    @Override
    public void returnMyLocation(String location) {

    }

    @Override
    public void showSnackbar(String message) {
        Util.showSnackbar(findViewById(R.id.contraint_layout),message);
    }
}