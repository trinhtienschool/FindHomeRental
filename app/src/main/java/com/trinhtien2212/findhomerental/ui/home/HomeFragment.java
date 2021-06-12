package com.trinhtien2212.findhomerental.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mmin18.widget.RealtimeBlurView;
import com.trinhtien2212.findhomerental.MainActivity;
import com.trinhtien2212.findhomerental.PaginationScrollListener;
import com.trinhtien2212.findhomerental.R;
import com.trinhtien2212.findhomerental.SearchActivity;
import com.trinhtien2212.findhomerental.adapter.RoomHomeAdapter;
import com.trinhtien2212.findhomerental.dao.RoomDB;
import com.trinhtien2212.findhomerental.model.Room;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements RoomsResult{

    private ImageButton btnSearch;
    private EditText edtSearch;
    private RecyclerView recyclerView;
    private RoomHomeAdapter adapter;
    private List<Room> mListRoom;
    private MainActivity mainActivity;

    private View root;
    private boolean isLoading, isLastPage;
    private int currentPage=1, totalPage=2;
    private RealtimeBlurView realtimeBlurView;
    private ProgressBar progressBar;

    public HomeFragment() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mainActivity = (MainActivity) getActivity();
        root = inflater.inflate(R.layout.fragment_home, container, false);
        assign();
        adapter = new RoomHomeAdapter();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mainActivity, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new PaginationScrollListener(gridLayoutManager) {
            @Override
            public void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
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

        setFirstData();
        // Move to Search Activity
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(edtSearch.getText().toString())){
                    Toast.makeText(mainActivity, "Bạn chưa nhập thông tin", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(mainActivity, SearchActivity.class);
                    startActivity(intent);
                }
            }
        });

//        btnMenu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Nhuận chuyển tại đây
//            }
//        });

        return root;
    }
    private void assign(){
        realtimeBlurView = root.findViewById(R.id.realtimeBlurView);
        progressBar = root.findViewById(R.id.pb_saving);
        recyclerView = root.findViewById(R.id.recycler_home);
        btnSearch = root.findViewById(R.id.ImageButtonSearch);
//        btnMenu = root.findViewById(R.id.menu_icon);
        edtSearch = root.findViewById(R.id.EditTextSearch);
//        showWaiting(View.VISIBLE);
    }
//    private void loadNextPage() {
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                List<Room> list = getListRoom();
//                mListRoom.addAll(list);
//                adapter.notifyDataSetChanged();
//
//                isLoading = false;
//                if(currentPage == totalPage){
//                    isLastPage = true;
//                }
//            }
//        }, 2000);
//    }

    //Load data
    private void setFirstData(){

        RoomDB roomDB = RoomDB.getInstance();
        roomDB.getRandomRooms(this);

        Toast.makeText(mainActivity, "Load data page", Toast.LENGTH_SHORT).show();
    }
    private void getListRoom(){


//        List<Room> list = new ArrayList<>();
//        Room r1 = new Room( 1600000, "8 Tân Hòa Đông, Quận 6");
//        Room r2 = new Room( 1600000, "18 Tân Hòa Đông, Quận 6");
//        Room r3 = new Room( 1600000, "28 Tân Hòa Đông, Quận 6");
//        Room r4 = new Room( 1600000, "38 Tân Hòa Đông, Quận 6");
//        Room r5 = new Room(1600000, "48 Tân Hòa Đông, Quận 6");
//        Room r6 = new Room(1600000, "58 Tân Hòa Đông, Quận 6");
//
//        list.add(r1);
//        list.add(r2);
//        list.add(r3);
//        list.add(r4);
//        list.add(r5);
//        list.add(r6);
//
//        return list;
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