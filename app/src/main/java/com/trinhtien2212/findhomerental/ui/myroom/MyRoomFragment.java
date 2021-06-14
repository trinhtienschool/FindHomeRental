package com.trinhtien2212.findhomerental.ui.myroom;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.mmin18.widget.RealtimeBlurView;
import com.trinhtien2212.findhomerental.MainActivity;
import com.trinhtien2212.findhomerental.PaginationScrollListener;
import com.trinhtien2212.findhomerental.R;
import com.trinhtien2212.findhomerental.adapter.MyRoomAdapter;
import com.trinhtien2212.findhomerental.adapter.RoomHomeAdapter;
import com.trinhtien2212.findhomerental.dao.RoomDB;
import com.trinhtien2212.findhomerental.model.Room;
import com.trinhtien2212.findhomerental.presenter.RoomPresenter;
import com.trinhtien2212.findhomerental.presenter.RoomsResult;

import java.util.List;


public class MyRoomFragment extends Fragment implements RoomsResult {

    private RecyclerView recyclerView;
    private MyRoomAdapter adapter;
    private List<Room> mListRoom;
    private MainActivity mainActivity;
    private View root;
    private boolean isLoading, isLastPage;
    private int currentPage=1, totalPage=2;
    private RealtimeBlurView realtimeBlurView;
    private ProgressBar progressBar;
    private RoomPresenter roomPresenter;
    public MyRoomFragment(){
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mainActivity = (MainActivity) getActivity();
        root = inflater.inflate(R.layout.fragment_my_room, container, false);
        assign();
        adapter = new MyRoomAdapter();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mainActivity);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

//        recyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
//            @Override
//            public void loadMoreItems() {
//                isLoading = true;
////                loadNextPage();
//            }
//
//            @Override
//            public boolean isLoading() {
//                return isLoading;
//            }
//
//            @Override
//            public boolean isLastPage() {
//                return isLastPage;
//            }
//        });

        setFirstData();
        return root;
    }
    private void assign(){
        realtimeBlurView = root.findViewById(R.id.realtimeBlurView3);
        progressBar = root.findViewById(R.id.pb_saving3);
        recyclerView = root.findViewById(R.id.recycler_home3);
    }

    //Load data
    private void setFirstData(){
        roomPresenter.getAllRoomsOfUser("mfSmbqjLoKd8YgphOJuZrQtJ7cj1");
//        RoomDB roomDB = RoomDB.getInstance();
//        roomDB.getRandomRooms(this);
//
//        Toast.makeText(mainActivity, "Load data page", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void returnRooms(List<Room> rooms) {

        mListRoom = rooms;
        adapter.setData(mListRoom);
        showWaiting(View.INVISIBLE);
    }
    private void showWaiting(int waiting){
        realtimeBlurView.setVisibility(waiting);
        progressBar.setVisibility(waiting);
    }
}