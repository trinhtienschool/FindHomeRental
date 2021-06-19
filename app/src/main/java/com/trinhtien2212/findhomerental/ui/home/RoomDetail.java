package com.trinhtien2212.findhomerental.ui.home;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.trinhtien2212.findhomerental.R;
import com.trinhtien2212.findhomerental.model.Room;


import java.util.ArrayList;
import java.util.List;


public class RoomDetail extends AppCompatActivity {
    TextView text_dia_chi, text_sdt,  text_giaphong,text_deposit,text_electronic,text_water,text_area,text_detail;
    TextView text_tu_do,text_may_nuoc_nong,text_may_lanh, text_wc, text_xe_dap, text_wifi, text_tu_lanh, text_gac_lung, text_dung_gio, text_an_ninh, text_tivi;
    ImageView hinh_tu_do,hinh_may_nuoc_nong, hinh_yeu_thich, hinh_dat_coc, hinh_gia_dien, hinh_gia_nuoc, hinh_dien_tich, hinh_may_lanh, hinh_wc, hinh_xe_dap, hinh_wifi, hinh_tu_lanh, hinh_gac_lung, hinh_dung_gio, hinh_an_ninh, hinh_tivi;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        setContentView(R.layout.activity_room_detail);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail);
        ImageSlider imageSlider = findViewById(R.id.slider);
        List<SlideModel> slideModel = new ArrayList<>();

        slideModel.add(new SlideModel("https://kollersi.com/wp-content/uploads/2019/08/album_tam-9.jpg"));
        slideModel.add(new SlideModel("https://ss-images.saostar.vn/2018/10/30/3961121/dge.png"));
        slideModel.add(new SlideModel("https://giaitri.vn/wp-content/uploads/2018/12/q13-2.jpg"));
        slideModel.add(new SlideModel("https://topnlist.com/wp-content/uploads/2019/04/ca-si-my-tam.jpg"));
        imageSlider.setImageList(slideModel, true);
        hinh_tu_do = findViewById(R.id.image_closet);
        text_tu_do = findViewById(R.id.text_closet);
        hinh_may_nuoc_nong = findViewById(R.id.image_hotwater);
        text_may_nuoc_nong = findViewById(R.id.text_hotwater);
        text_detail = findViewById(R.id.text_chitiet);
        text_area.findViewById(R.id.text_area_price);
        text_water = findViewById(R.id.text_water_price);
        text_electronic = findViewById(R.id.text_electronic_price);
        text_deposit = findViewById(R.id.text_deposit_price);
        text_giaphong = findViewById(R.id.gia_phong);

        text_sdt = findViewById(R.id.text_numberphone);
        text_dia_chi = findViewById(R.id.text_address);
        hinh_dat_coc = findViewById(R.id.image_deposit);
        hinh_gia_dien = findViewById(R.id.image_electronic_price);
        hinh_gia_nuoc = findViewById(R.id.image_water_price);
        hinh_dien_tich = findViewById(R.id.image_area);
        hinh_may_lanh = findViewById((R.id.image_cool_machine));
        hinh_wc = findViewById(R.id.image_wc);
        hinh_xe_dap = findViewById(R.id.image_bike);
        hinh_tu_lanh = findViewById(R.id.image_cool_tu);
        hinh_wifi = findViewById(R.id.image_wifi);
        hinh_gac_lung = findViewById(R.id.image_gaclung);
        hinh_dung_gio = findViewById(R.id.image_clock);
        hinh_an_ninh = findViewById(R.id.image_security);
        hinh_tivi = findViewById(R.id.image_tivi);
        hinh_yeu_thich = findViewById(R.id.image_yeu_thich);

        text_an_ninh = findViewById(R.id.text_anninh);
        text_dung_gio = findViewById(R.id.text_clock);
        text_may_lanh = findViewById(R.id.text_cool_marchine);
        text_xe_dap = findViewById(R.id.text_bike);
        text_tu_lanh = findViewById(R.id.text_tulanh);
        text_wc = findViewById(R.id.text_wc);
        text_wifi = findViewById(R.id.text_wifi);
        text_gac_lung = findViewById(R.id.text_gaclung);
        text_tivi = findViewById(R.id.text_tivi);
        hinh_yeu_thich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        Bundle bundle = getIntent().getExtras();
        Room room = (Room) bundle.getSerializable("room");
        boolean isLove = bundle.getBoolean("isLove");
        if (isLove == true) {
            hinh_yeu_thich.setImageResource(R.drawable.heart_red);
        }


        if (room.isAirCondition() == true) {
            hinh_may_lanh.setImageResource(R.drawable.cool_machin_yellow);
            text_may_lanh.setTextColor(Color.YELLOW);
        }
        if (room.isWifi() == true) {
            hinh_may_lanh.setImageResource(R.drawable.cool_machin_yellow);
            text_may_lanh.setTextColor(Color.YELLOW);
        }
        if (room.isAttic() == true) {
            hinh_gac_lung.setImageResource(R.drawable.gac_lung_yellow);
            text_gac_lung.setTextColor(Color.YELLOW);
        }
        if (room.isWC() == true) {
            hinh_wc.setImageResource(R.drawable.toilet_yellow);
            text_wc.setTextColor(Color.YELLOW);
        }
        if (room.isTivi() == true) {
            hinh_tivi.setImageResource(R.drawable.television_yellow);
            text_may_lanh.setTextColor(Color.YELLOW);
        }
        if (room.isWifi() == true) {
            hinh_wifi.setImageResource(R.drawable.wifi_yellow);
            text_wifi.setTextColor(Color.YELLOW);
        }
        if (room.isPark() == true) {
            hinh_xe_dap.setImageResource(R.drawable.bike_yellow);
            text_xe_dap.setTextColor(Color.YELLOW);
        }
        if (room.isHotWater() == true) {
            hinh_may_nuoc_nong.setImageResource(R.drawable.hot_water_yellow);
            text_may_nuoc_nong.setTextColor(Color.YELLOW);
        }
        if (room.isWardrobe() == true) {
            hinh_tu_do.setImageResource(R.drawable.closet);
            text_tu_do.setTextColor(Color.YELLOW);
        }

        text_dia_chi.setText(room.getAddress());
        text_sdt.setText(room.getPhone());
        text_giaphong.setText(room.getCost());
        text_deposit.setText(room.getDeposit());
        text_electronic.setText(room.getEleCost());
        text_water.setText(room.getWatCost());
        text_area.setText((int) room.getArea());
        text_detail.setText(room.getDescription());


    }
}