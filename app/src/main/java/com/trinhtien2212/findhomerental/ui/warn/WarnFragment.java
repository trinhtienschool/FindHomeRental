package com.trinhtien2212.findhomerental.ui.warn;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.mmin18.widget.RealtimeBlurView;
import com.google.firebase.auth.FirebaseAuth;
import com.trinhtien2212.findhomerental.MainActivity;
import com.trinhtien2212.findhomerental.R;
import com.trinhtien2212.findhomerental.WarnList;
import com.trinhtien2212.findhomerental.adapter.WarnAdapter;
import com.trinhtien2212.findhomerental.model.Notification;
import com.trinhtien2212.findhomerental.presenter.NotificationPresenter;
import com.trinhtien2212.findhomerental.presenter.NotificationResult;
import com.trinhtien2212.findhomerental.presenter.StatusResult;
import com.trinhtien2212.findhomerental.ui.PaginationScrollListener;

import java.util.ArrayList;
import java.util.List;

public class WarnFragment extends Fragment implements NotificationResult, StatusResult {

    private RecyclerView recyclerView;
    private WarnAdapter adapter;
    private List<Notification> mListNoti;
    private MainActivity mainActivity;

    private View root;
    private boolean isLoading, isLastPage;
    private int currentPage=1, totalPage=2;

    private NotificationPresenter notificationPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_warn, container, false);
        mainActivity = (MainActivity) getActivity();

        mListNoti = new ArrayList<Notification>();
        notificationPresenter = new NotificationPresenter((NotificationResult) this);
        assign();

        buildRecyclerView();
        actionItemRecyclerView();
        setFirstData();
        Log.e("WarnFrag","Co vao");
        return root;
    }

    private void assign(){
        recyclerView = root.findViewById(R.id.recycler_home5);
    }
    //Load data
    private void setFirstData(){
        //ToDo
        notificationPresenter.getNotifications(FirebaseAuth.getInstance().getCurrentUser().getUid());
    }
    private void getListNoti(){

    }

    private void buildRecyclerView(){
        adapter = new WarnAdapter();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mainActivity);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);


    }
    public void actionItemRecyclerView() {
        adapter.setOnItemClickListener(new WarnAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int positon) {
                // Todo item
                Log.e("Notification", mListNoti.get(positon).toString());
                Log.e("Notification", mListNoti.get(positon).toString());
                Notification notification = mListNoti.get(positon);
                Bundle bundle = new Bundle();
                Intent intent = new Intent(mainActivity, WarnDetail.class);
                bundle.putSerializable("note",notification);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }
    public void showStatus(String s) {
        Toast.makeText(mainActivity,s,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void returnNotification(List<Notification> notifications) {
        if(notifications!=null){
            mListNoti.addAll(notifications);
            adapter.setData(mListNoti);
            adapter.notifyDataSetChanged();

        }else Toast.makeText(mainActivity,"Chưa có cảnh báo nào",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFail() {

    }

    @Override
    public void onSuccess() {

    }
}