package com.trinhtien2212.findhomerental.ui.home;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.firebase.auth.FirebaseAuth;
import com.trinhtien2212.findhomerental.R;
import com.trinhtien2212.findhomerental.UserListActivity;
import com.trinhtien2212.findhomerental.model.Room;
import com.trinhtien2212.findhomerental.presenter.BookmarkPresenter;
import com.trinhtien2212.findhomerental.presenter.RoomsResult;
import com.trinhtien2212.findhomerental.presenter.StatusResult;
import com.trinhtien2212.findhomerental.ui.Util;

import java.util.ArrayList;
import java.util.List;


public class RoomDetail extends AppCompatActivity implements StatusResult, RoomsResult,IGetMyLocation {
    private ImageView iv_love;
    private boolean isLove = false;
    private Room room;
    private TextView text_dia_chi, text_sdt,  text_giaphong,text_deposit,text_electronic,text_water,text_area,text_detail;
    private TextView txt_author_name, text_tu_do,text_may_nuoc_nong,text_may_lanh, text_wc, text_xe_dap, text_wifi, text_tu_lanh, text_gac_lung, text_dung_gio, text_an_ninh, text_tivi;
    private ImageView author_img,hinh_tu_do,hinh_may_nuoc_nong, hinh_dat_coc, hinh_gia_dien, hinh_gia_nuoc, hinh_dien_tich, hinh_may_lanh, hinh_wc, hinh_xe_dap, hinh_wifi, hinh_tu_lanh, hinh_gac_lung, hinh_dung_gio, hinh_an_ninh, hinh_tivi;
    private BookmarkPresenter bookmarkPresenter;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Log.e("OnCreateRoomDetail","Dang vao");

        setContentView(R.layout.activity_room_detail);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail);
        assign();
    }
    private void assign(){

        Log.e("OnCreateRoomDetail","Dang vao 2");
        ImageSlider imageSlider = findViewById(R.id.slider);
        List<SlideModel> slideModel = new ArrayList<>();

        author_img = findViewById(R.id.author_img);
        txt_author_name = findViewById(R.id.author_name);
        hinh_tu_do = findViewById(R.id.image_closet);
        text_tu_do = findViewById(R.id.text_closet);
        hinh_may_nuoc_nong = findViewById(R.id.image_hotwater);
        text_may_nuoc_nong = findViewById(R.id.text_hotwater);
        text_detail = findViewById(R.id.text_chitiet);
        Log.e("OnCreateRoomDetail","Dang vao 4");
        text_area = findViewById(R.id.text_area_price);
        Log.e("OnCreateRoomDetail","Dang vao 10");
        text_water = findViewById(R.id.text_water_price);
        Log.e("OnCreateRoomDetail","Dang vao 11");
        text_electronic = findViewById(R.id.text_electronic_price);
        Log.e("OnCreateRoomDetail","Dang vao 12");
        text_deposit = findViewById(R.id.text_deposit_price);
        Log.e("OnCreateRoomDetail","Dang vao 13");
        text_giaphong = findViewById(R.id.gia_phong);
        Log.e("OnCreateRoomDetail","Dang vao 5");
        text_sdt = findViewById(R.id.text_numberphone);
        text_dia_chi = findViewById(R.id.text_address);
        hinh_dat_coc = findViewById(R.id.image_deposit);
        Log.e("OnCreateRoomDetail","Dang vao 6");
        hinh_gia_dien = findViewById(R.id.image_electronic_price);
        hinh_gia_nuoc = findViewById(R.id.image_water_price);
        hinh_dien_tich = findViewById(R.id.image_area);
        hinh_may_lanh = findViewById((R.id.image_cool_machine));
        hinh_wc = findViewById(R.id.image_wc);
        Log.e("OnCreateRoomDetail","Dang vao 7");
        hinh_xe_dap = findViewById(R.id.image_bike);
        hinh_tu_lanh = findViewById(R.id.image_cool_tu);
        hinh_wifi = findViewById(R.id.image_wifi);
        hinh_gac_lung = findViewById(R.id.image_gaclung);
        hinh_dung_gio = findViewById(R.id.image_clock);
        hinh_an_ninh = findViewById(R.id.image_security);
        hinh_tivi = findViewById(R.id.image_tivi);

        Log.e("OnCreateRoomDetail","Dang vao 8");
        text_an_ninh = findViewById(R.id.text_anninh);
        text_dung_gio = findViewById(R.id.text_clock);
        text_may_lanh = findViewById(R.id.text_cool_marchine);
        text_xe_dap = findViewById(R.id.text_bike);
        Log.e("OnCreateRoomDetail","Dang vao 9");
        text_tu_lanh = findViewById(R.id.text_tulanh);
        text_wc = findViewById(R.id.text_wc);
        text_wifi = findViewById(R.id.text_wifi);
        text_gac_lung = findViewById(R.id.text_gaclung);
        text_tivi = findViewById(R.id.text_tivi);
        Log.e("OnCreateRoomDetail","Dang vao 3");
        iv_love = findViewById(R.id.image_yeu_thich);
        Bundle bundle = getIntent().getExtras();
        room = (Room) bundle.getSerializable("room");
        String admin = bundle.getString("admin");
        if(admin != null){
            Log.e("VisibleLove","CoAn");
            iv_love.setVisibility(View.GONE);
        }
        Log.e("imageRoom",room.getImages().toString());
        //slide show
        for(String imageUrl: room.getImages()){
            slideModel.add(new SlideModel(imageUrl));
        }
        Log.e("Da vao 20","20");
        imageSlider.setImageList(slideModel, true);

//        boolean isLove = bundle.getBoolean("isLove");
//        if (isLove == true) {
//            hinh_yeu_thich.setImageResource(R.drawable.heart_red);
//        }

        bookmarkPresenter = new BookmarkPresenter(this,this);

        iv_love.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(!Util.checkNetwork(RoomDetail.this,RoomDetail.this)) {
                    return;
                }
                if(FirebaseAuth.getInstance().getCurrentUser() ==null){

                    Toast.makeText(RoomDetail.this,"Bạn phải đăng nhập để thêm yêu thích",Toast.LENGTH_LONG).show();
                }else if(isLove){
                    bookmarkPresenter.removeRoom(room.getRoomID(),FirebaseAuth.getInstance().getCurrentUser().getUid());

                }
                else if(!isLove){
                    bookmarkPresenter.addBookmark(room.getRoomID(), FirebaseAuth.getInstance().getCurrentUser().getUid());
                }
            }
        });


        if (room.isAirCondition() == true) {
            hinh_may_lanh.setImageResource(R.drawable.cool_machin_yellow);
            text_may_lanh.setTextColor(Color.parseColor("#D2B633"));
        }else setVisibleGone(hinh_may_lanh,text_may_lanh);
        if (room.isAttic() == true) {
            hinh_gac_lung.setImageResource(R.drawable.gac_lung_yellow);
            text_gac_lung.setTextColor(Color.parseColor("#D2B633"));
        }else setVisibleGone(hinh_gac_lung,text_gac_lung);
        if (room.isWC() == true) {
            hinh_wc.setImageResource(R.drawable.toilet_yellow);
            text_wc.setTextColor(Color.parseColor("#D2B633"));
        }else setVisibleGone(hinh_wc,text_wc);
        if (room.isTivi() == true) {
            hinh_tivi.setImageResource(R.drawable.television_yellow);
            text_tivi.setTextColor(Color.parseColor("#D2B633"));
        }else setVisibleGone(hinh_tivi,text_tivi);
        if (room.isWifi() == true) {
            hinh_wifi.setImageResource(R.drawable.wifi_yellow);
            text_wifi.setTextColor(Color.parseColor("#D2B633"));
        }else setVisibleGone(hinh_wifi,text_wifi);
        if (room.isPark() == true) {
            hinh_xe_dap.setImageResource(R.drawable.bike_yellow);
            text_xe_dap.setTextColor(Color.parseColor("#D2B633"));
        }else setVisibleGone(hinh_xe_dap,text_xe_dap);
        if (room.isHotWater() == true) {
            hinh_may_nuoc_nong.setImageResource(R.drawable.hot_water_yellow);
            text_may_nuoc_nong.setTextColor(Color.parseColor("#D2B633"));
        }else setVisibleGone(hinh_may_nuoc_nong,text_may_nuoc_nong);
        if(room.isFre()==true){
            hinh_tu_lanh.setImageResource(R.drawable.fridge_yellow);
            text_tu_lanh.setTextColor(Color.parseColor("#D2B633"));
        }
        else setVisibleGone(hinh_tu_lanh,text_tu_lanh);


        if (room.isWardrobe() == true) {
            hinh_tu_do.setImageResource(R.drawable.closet_yellow);
            text_tu_do.setTextColor(Color.parseColor("#D2B633"));
        }else setVisibleGone(hinh_tu_do,text_tu_do);

        if (room.isFence()== true) {
            hinh_an_ninh.setImageResource(R.drawable.security_yellow);
            text_an_ninh.setTextColor(Color.parseColor("#D2B633"));
        }else setVisibleGone(hinh_an_ninh,text_an_ninh);

        if (room.isFreeTime()== true) {
            hinh_dung_gio.setImageResource(R.drawable.clock_yellow);
            text_dung_gio.setTextColor(Color.parseColor("#D2B633"));
        }else setVisibleGone(hinh_dung_gio,text_dung_gio);

        //thêm ổ khóa, tủ lạnh

        Log.e("Da vao 20","20");
        text_dia_chi.setText(room.getAddress());
        text_sdt.setText(room.getPhone());
        text_giaphong.setText(Util.formatCurrency(room.getCost()));
        text_deposit.setText(Util.formatCurrency(room.getDeposit()));
        text_electronic.setText(Util.formatCurrency(room.getEleCost()));
        text_water.setText(Util.formatCurrency(room.getWatCost()));
        text_area.setText(Html.fromHtml(room.getArea()+" m<sup><small>2</small></sup>"));
        text_detail.setText(room.getDescription());
        txt_author_name.setText(room.getUserDisplayName());
        Util.setImage(author_img,room.getUserPhotoUrl());
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() !=null){
            bookmarkPresenter.getAllBookmarks(FirebaseAuth.getInstance().getCurrentUser().getUid());
        }
    }

    private void setVisibleGone(ImageView imageView, TextView textView){
//        imageView.setVisibility(View.GONE);
//        textView.setVisibility(View.GONE);
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void returnRooms(List<Room> rooms) {
        if(rooms == null){
            return;
        }
        for(Room r : rooms){
            if(r.getRoomID().equalsIgnoreCase(this.room.getRoomID())){
                iv_love.setImageResource(R.drawable.heart_red);
                isLove = true;
                return;
            }
        }
    }

    @Override
    public void onFail() {
        Toast.makeText(this,"Thất bại",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSuccess() {
        Log.e("Success roomdetail","Succc");
        if(!isLove) {
            isLove = true;
            iv_love.setImageResource(R.drawable.heart_red);

        }else if(isLove){
            isLove = false;
            iv_love.setImageResource(R.drawable.traitim);
        }
//        Toast.makeText(this,"Thành công",Toast.LENGTH_LONG).show();
    }

    @Override
    public void returnMyLocation(String location) {

    }

    @Override
    public void showSnackbar(String message) {
        Util.showSnackbar(findViewById(R.id.layoutRoomdetail),message);
    }
}