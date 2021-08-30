package com.trinhtien2212.mobilefindroomrental;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.trinhtien2212.mobilefindroomrental.adapter.WarnAdapter;
import com.trinhtien2212.mobilefindroomrental.model.Notification;
import com.trinhtien2212.mobilefindroomrental.presenter.NotificationPresenter;
import com.trinhtien2212.mobilefindroomrental.presenter.NotificationResult;
import com.trinhtien2212.mobilefindroomrental.presenter.StatusResult;
import com.trinhtien2212.mobilefindroomrental.ui.Util;
import com.trinhtien2212.mobilefindroomrental.ui.home.IGetMyLocation;
import com.trinhtien2212.mobilefindroomrental.ui.warn.WarnDetail;

import java.util.ArrayList;
import java.util.List;

public class WarnList extends AppCompatActivity implements NotificationResult, StatusResult, IGetMyLocation {

    private RecyclerView recyclerView;
    private WarnAdapter adapter;
    private List<Notification> mListNoti;

    private boolean isLoading, isLastPage;
    private int currentPage = 1, totalPage = 2;

    private NotificationPresenter notificationPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warn_list);

        getSupportActionBar().setTitle("Danh sách cảnh báo");

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button

        mListNoti = new ArrayList<Notification>();
        notificationPresenter = new NotificationPresenter((NotificationResult) this);
        assign();

        buildRecyclerView();

        actionItemRecyclerView();

        Bundle bundle = getIntent().getExtras();
        String userId = bundle.getString("userId");

        notificationPresenter.getNotifications(userId);
        Log.e("WarnFrag", "Co vao");
    }

    private void assign() {
        recyclerView = findViewById(R.id.recycler_home5);
    }

    //Load data
    private void getListNoti() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        Util.checkNetwork(this,this);
    }

    private void buildRecyclerView() {
        adapter = new WarnAdapter();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);


    }

    public void actionItemRecyclerView() {
        adapter.setOnItemClickListener(new WarnAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int positon) {
                // Todo item
                Log.e("Notification", mListNoti.get(positon).toString());
                Notification notification = mListNoti.get(positon);
                Bundle bundle = new Bundle();
                Intent intent = new Intent(WarnList.this, WarnDetail.class);
                bundle.putSerializable("note", notification);

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    public void showStatus(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void returnNotification(List<Notification> notifications) {
        if (notifications != null) {
            mListNoti.addAll(notifications);
            adapter.setData(mListNoti);
            adapter.notifyDataSetChanged();

        } else Toast.makeText(this, "Chưa có cảnh báo nào", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFail() {

    }

    @Override
    public void onSuccess() {

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
        Util.showSnackbar(findViewById(R.id.layoutWarnlist), message);
    }
}