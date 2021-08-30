package com.trinhtien2212.mobilefindroomrental.ui.add_room;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.github.mmin18.widget.RealtimeBlurView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.trinhtien2212.mobilefindroomrental.R;
import com.trinhtien2212.mobilefindroomrental.model.Room;
import com.trinhtien2212.mobilefindroomrental.presenter.RoomPresenter;
import com.trinhtien2212.mobilefindroomrental.presenter.RoomsResult;
import com.trinhtien2212.mobilefindroomrental.presenter.StatusResult;
import com.trinhtien2212.mobilefindroomrental.ui.Util;
import com.trinhtien2212.mobilefindroomrental.ui.home.IGetMyLocation;

import java.util.List;
import java.util.Map;

public class AddRoomActivity extends AppCompatActivity implements StatusResult, IGetMyLocation {
    private TabLayout mTabLayout;
    private ViewPager2 viewPager2;
    private AddRoomViewPagerAdapter adapter;
    private Room room;
    private RoomPresenter roomPresenter;
    private RealtimeBlurView realtimeBlurView;
    private ProgressBar progressBar;
    private FrameLayout frameLayout;
    private boolean isUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);
        init();

        isUpdate = false;
        roomPresenter = new RoomPresenter(this);
        //include back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);      //icon in appbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);      //back button in android
        getSupportActionBar().setHomeButtonEnabled(true);
        frameLayout = findViewById(R.id.activity_add_room);
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
        LinearLayout tabStrip = ((LinearLayout)mTabLayout.getChildAt(0));
        for(int i = 0; i < tabStrip.getChildCount(); i++) {
            tabStrip.getChildAt(i).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }

            });
        }
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
    public void setAddress(Map<String,Object>address){
        Log.e("AddressMap",address.toString());
        Log.e("Roompre",roomPresenter.toString());
        roomPresenter.setAddress(address);
        viewPager2.setCurrentItem(1);
    }
    public void setInfo(Map<String,Object>info){
        roomPresenter.setInfo(info);
        viewPager2.setCurrentItem(2);
    }
    public void saveRoom(Map<String,Object>utilities){

        if(!Util.checkNetwork(this,this)) {
            Log.e("Ket thuc","Da ket thuc");
            return;
        }
      if(isUpdate){
          utilities.put("roomId",room.getRoomID());
      }
        roomPresenter.saveRoom(utilities, isUpdate);
        showWaiting(View.VISIBLE);

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

    @Override
    public void returnMyLocation(String location) {

    }
    public Room sendRoom(){
        if(isUpdate){
            return room;
        }else return null;
    }
    @Override
    public void showSnackbar(String message) {
        Util.showSnackbar(frameLayout,message);
    }
}