package com.trinhtien2212.findhomerental.ui.add_room;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.github.mmin18.widget.RealtimeBlurView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.trinhtien2212.findhomerental.R;
import com.trinhtien2212.findhomerental.model.Room;
import com.trinhtien2212.findhomerental.presenter.RoomPresenter;
import com.trinhtien2212.findhomerental.presenter.RoomsResult;
import com.trinhtien2212.findhomerental.presenter.StatusResult;

import java.util.List;
import java.util.Map;

public class AddRoomActivity extends AppCompatActivity implements StatusResult {
    private TabLayout mTabLayout;
    private ViewPager2 viewPager2;
    private AddRoomViewPagerAdapter adapter;
    private Room room;
    private RoomPresenter roomPresenter;
    RealtimeBlurView realtimeBlurView;
    ProgressBar progressBar;
    private boolean isUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);
        init();
        this.room = new Room();
        isUpdate = false;
        //include back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);      //icon in appbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);      //back button in android
        getSupportActionBar().setHomeButtonEnabled(true);

        //set Title for action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Đăng tin phòng trọ");
        realtimeBlurView = findViewById(R.id.realtimeBlurView);
        progressBar = findViewById(R.id.pb_saving);
        showWaiting(View.INVISIBLE);

        Bundle bundle = getIntent().getExtras();
        if(bundle !=null) {
            Room roomReceive = (Room) bundle.getSerializable("room");
            if (roomReceive != null) {
//            Log.e("Room",room.toString());
                this.room = roomReceive;
                isUpdate = true;
            }
        }

        //Test delete
//        roomPresenter = new RoomPresenter(this,room);
//        roomPresenter.deleteRoom();


    }
    @Override
    public boolean onSupportNavigateUp() {
        int currentPage = viewPager2.getCurrentItem();
        if(currentPage == 0){
            finish();
        }
        else viewPager2.setCurrentItem(currentPage-1);
        return true;
    }
    private void init(){
        mTabLayout = findViewById(R.id.tab_layout);
        viewPager2 = findViewById(R.id.vp_addRoom);
        FragmentManager fragmentManager = getSupportFragmentManager();
        adapter = new AddRoomViewPagerAdapter(fragmentManager,getLifecycle());
        viewPager2.setAdapter(adapter);

        mTabLayout.addTab(mTabLayout.newTab().setText("Địa chỉ"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Thông tin"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Tiện ích"));
        viewPager2.setUserInputEnabled(false);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
               mTabLayout.selectTab(mTabLayout.getTabAt(position));
            }
        });
        //keep 3 fragments on either side of the current fragment alive in memory
        viewPager2.setOffscreenPageLimit(3);
        
    }
    private void showWaiting(int waiting){
        realtimeBlurView.setVisibility(waiting);
        progressBar.setVisibility(waiting);
    }
    public void getAddress(Map<String,Object>address){
        room.setAddress((String)address.get("address"));
        room.setPhone((String)address.get("phone"));
        Log.e("Map",room.toString());
        viewPager2.setCurrentItem(1);
    }
    public void getInfo(Map<String,Object>info){
        room.setCost((int)info.get("costPerMonth"));
        room.setDeposit((int)info.get("dCost"));
        room.setEleCost((int)info.get("eCost"));
        room.setWatCost((int)info.get("wCost"));
        room.setArea((float)info.get("area"));
        room.setDescription((String)info.get("description"));
//        room.putAll(info);
        Log.e("Room",room.toString());
        viewPager2.setCurrentItem(2);
    }
    public void saveRoom(Map<String,Object>utilities,boolean isUpdate){
//        room.putAll(utilities);
        //ToDo
//        room.setUserCreatedId(FirebaseAuth.getInstance().getCurrentUser().getUid());
        room.setUserCreatedId(FirebaseAuth.getInstance().getCurrentUser().getUid());
        room.setImages((List<String>) utilities.get("images"));

        List<String>utilitiesList = (List<String>) utilities.get("utilities");

        for(String utility : utilitiesList){
            switch (utility){
                case "Tivi":
                    room.setIsTivi(true);
                    break;
                case "Tủ đồ":
                    room.setIsWardrobe(true);
                case "Wifi":
                    room.setIsWifi(true);
                case "Tủ lạnh":
                    room.setIsFre(true);
                case "Máy lạnh":
                    room.setIsAirCondition(true);
                case "Gác lủng":
                    room.setIsAttic(true);
                case "Máy nước nóng":
                    room.setIsHotWater(true);
                case  "Nhà vệ sinh riêng":
                    room.setIsWC(true);
                case "Tự do giờ giấc":
                    room.setFreeTime(true);
                case "An ninh":
                    room.setIsFence(true);
                case "Chỗ để xe riêng":
                    room.setIsPark(true);
            }
        }
        roomPresenter = new RoomPresenter(this,room);
        if(isUpdate){
            roomPresenter.updateRoom();
        }
        else roomPresenter.saveRoom();
        showWaiting(View.VISIBLE);
    }
    public Room sendRoom(){
        if(isUpdate){
            return room;
        }else return null;
    }
    public void notifyStatus(String status){
        showWaiting(View.INVISIBLE);
        Toast.makeText(this,status,Toast.LENGTH_LONG).show();
    }



    @Override
    public void onFail() {
        notifyStatus("Thất bại");
    }

    @Override
    public void onSuccess() {
        notifyStatus("Thành công");
    }
}