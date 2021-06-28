package com.trinhtien2212.findhomerental.ui.warn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.trinhtien2212.findhomerental.R;
import com.trinhtien2212.findhomerental.model.Notification;
import com.trinhtien2212.findhomerental.model.Room;
import com.trinhtien2212.findhomerental.presenter.RoomPresenter;
import com.trinhtien2212.findhomerental.presenter.RoomsResult;
import com.trinhtien2212.findhomerental.ui.home.RoomDetail;

import java.util.List;

public class WarnDetail extends AppCompatActivity implements RoomsResult {
    TextView tv_title,tv_content;
    Button btn_openDetail;
    private RoomPresenter roomPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warm_detail);
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
}