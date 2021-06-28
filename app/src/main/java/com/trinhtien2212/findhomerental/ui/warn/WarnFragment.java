package com.trinhtien2212.findhomerental.ui.warn;

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
import com.trinhtien2212.findhomerental.MainActivity;
import com.trinhtien2212.findhomerental.R;
import com.trinhtien2212.findhomerental.adapter.WarnAdapter;
import com.trinhtien2212.findhomerental.model.Notification;
import com.trinhtien2212.findhomerental.presenter.NotificationPresenter;
import com.trinhtien2212.findhomerental.ui.PaginationScrollListener;

import java.util.ArrayList;
import java.util.List;

public class WarnFragment extends Fragment {

    private RecyclerView recyclerView;
    private WarnAdapter adapter;
    private List<Notification> mListNoti;
    private MainActivity mainActivity;

    private View root;
    private boolean isLoading, isLastPage;
    private int currentPage=1, totalPage=2;
    private RealtimeBlurView realtimeBlurView;
    private ProgressBar progressBar;
    private NotificationPresenter notificationPresenter;

    public WarnFragment(){

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainActivity = (MainActivity) getActivity();
        mListNoti = new ArrayList<Notification>();
        root = inflater.inflate(R.layout.fragment_warn, container, false);
        assign();

        buildRecyclerView();
        actionItemRecyclerView();
        setFirstData();
        return root;
    }

    private void assign(){
        realtimeBlurView = root.findViewById(R.id.realtimeBlurView5);
        progressBar = root.findViewById(R.id.pb_saving5);
        recyclerView = root.findViewById(R.id.recycler_home5);
    }
    //Load data
    private void setFirstData(){
        //ToDo
        notificationPresenter.getNotifications("At137YkMB7OXy99UzZINGbVExY72");
    }
    private void getListNoti(){

    }
    public void returnRooms(List<Notification> notifications) {
        if(notifications!=null){
            mListNoti.addAll(notifications);
            adapter.setData(mListNoti);
//            totalPage = bookmarkPresenter.getTotalPage();
//            txtTotalResults.setText(searchPresenter.getTotalResults()+"");
            if(currentPage == totalPage){
                isLastPage = true;
            }
            recyclerView.post(new Runnable() {
                public void run() {
                    // There is no need to use notifyDataSetChanged()
                    adapter.notifyDataSetChanged();
                }
            });
            isLoading = false;
        }else Toast.makeText(mainActivity,"Chưa có cảnh báo nào",Toast.LENGTH_LONG).show();

        progressBar.setVisibility(View.GONE);
        showWaiting(View.INVISIBLE);
    }
    private void showWaiting(int waiting){
        realtimeBlurView.setVisibility(waiting);
        progressBar.setVisibility(waiting);
    }
    private void buildRecyclerView(){
        adapter = new WarnAdapter();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mainActivity);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            public void loadMoreItems() {
                isLoading = true;
//                loadNextPage();
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }
        });
    }
    public void actionItemRecyclerView() {
        adapter.setOnItemClickListener(new WarnAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int positon) {
                // Todo item
                Log.e("Notification", mListNoti.get(positon).toString());
            }
        });

    }
    public void showStatus(String s) {
        Toast.makeText(mainActivity,s,Toast.LENGTH_SHORT).show();
    }
}