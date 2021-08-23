package com.trinhtien2212.findroomrentalmobile.ui.warn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.trinhtien2212.findroomrentalmobile.R;
import com.trinhtien2212.findroomrentalmobile.model.Notification;
import com.trinhtien2212.findroomrentalmobile.model.Room;
import com.trinhtien2212.findroomrentalmobile.presenter.RoomPresenter;
import com.trinhtien2212.findroomrentalmobile.presenter.RoomsResult;
import com.trinhtien2212.findroomrentalmobile.ui.Util;
import com.trinhtien2212.findroomrentalmobile.ui.home.IGetMyLocation;
import com.trinhtien2212.findroomrentalmobile.ui.home.RoomDetail;

import java.util.List;

public class WarnDetail extends AppCompatActivity implements RoomsResult, IGetMyLocation {
    TextView tv_title,tv_content;
    Button btn_openDetail;
    private RoomPresenter roomPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warm_detail);
        getSupportActionBar().setTitle("Chi tiết cảnh báo");

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button

        roomPresenter = new RoomPresenter(this);
        tv_title = findViewById(R.id.tv_title);
        tv_content = findViewById(R.id.tv_content);
        btn_openDetail = findViewById(R.id.btn_openDetail);

        Bundle bundle = getIntent().getExtras();
        Notification notification = (Notification) bundle.getSerializable("note");
        tv_title.setText(notification.getAddress());
        tv_content.setText(notification.getMessage());
        btn_openDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Util.checkNetwork(WarnDetail.this,WarnDetail.this)) return;
                roomPresenter.getARoomByRoomId(notification.getRoomId());
            }
        });
    }

    @Override
    public void returnRooms(List<Room> rooms) {
        if(rooms !=null){
            Room room = rooms.get(0);
            Intent intent = new Intent(WarnDetail.this, RoomDetail.class);
            Bundle bundle1 = new Bundle();
            bundle1.putSerializable("room",room);
            bundle1.putString("admin","true");
            intent.putExtras(bundle1);
            startActivity(intent);
        }else{
            Toast.makeText(this,"Phòng trọ không tồn tại",Toast.LENGTH_LONG).show();
        }
    }
    // back
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void returnMyLocation(String location) {

    }

    @Override
    public void showSnackbar(String message) {
        Util.showSnackbar(findViewById(R.id.activity_constrain_layout),message);
    }
}