package com.trinhtien2212.findhomerental;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.trinhtien2212.findhomerental.adapter.RoomAdminAdapter;
import com.trinhtien2212.findhomerental.model.Room;
import com.trinhtien2212.findhomerental.ui.PaginationScrollListener;

import java.util.ArrayList;
import java.util.List;

public class RoomListActivity extends AppCompatActivity implements androidx.appcompat.widget.PopupMenu.OnMenuItemClickListener{
    private RecyclerView recyclerView;
    private RoomAdminAdapter adapter;
    private List<Room> mListlist;
    private ProgressBar progressBar;
    private SearchView searchView;
    private ImageButton btnFilter, btnSort;
    private TextView txtTotalResults;
    private ImageButton imgBtnBack;

    private boolean isLoading, isLastPage;
    private int currentPage=1, totalPage=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);

        recyclerView = findViewById(R.id.recycler_view_room);
        progressBar = findViewById(R.id.progress_bar);
        imgBtnBack = findViewById(R.id.ImageButtonBack2);

        imgBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RoomListActivity.this, MainAdminActivity.class);
                startActivity(intent);
            }
        });
//        btnFilter = findViewById(R.id.ImgButtonFilter);
//        btnSort = findViewById(R.id.ImgButtonSort);
//        txtTotalResults = findViewById(R.id.TextViewTotalResult);
        adapter = new RoomAdminAdapter();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            public void loadMoreItems() {
                isLoading = true;
                progressBar.setVisibility(View.VISIBLE);
                currentPage += 1;
                loadNextPage();
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
    }

    private void loadNextPage() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<Room> list = getListRoom();
                mListlist.addAll(list);
                adapter.notifyDataSetChanged();

                isLoading = false;
                progressBar.setVisibility(View.GONE);
                if(currentPage == totalPage){
                    isLastPage = true;
                }
            }
        }, 2000);
    }

    //Load data
    private void setFirstData(){
        mListlist = getListRoom();
        adapter.setData(mListlist);
    }
    private List<Room> getListRoom(){
        Toast.makeText(this, "Đang tải chờ xíu...", Toast.LENGTH_SHORT).show();

        List<Room> list = new ArrayList<>();
        Room r1 = new Room( 1600000, "8 Tân Hòa Đông, Quận 6");
        Room r2 = new Room( 1600000, "18 Tân Hòa Đông, Quận 6");
        Room r3 = new Room( 1600000, "28 Tân Hòa Đông, Quận 6");
        Room r4 = new Room( 1600000, "38 Tân Hòa Đông, Quận 6");
        Room r5 = new Room(1600000, "48 Tân Hòa Đông, Quận 6");
        Room r6 = new Room(1600000, "58 Tân Hòa Đông, Quận 6");
//        Room r7 = new Room("Nhà trọ 7", "1,6 triệu VND/căn", "68 Tân Hòa Đông, Quận 6", R.drawable.tro7);
//        Room r8 = new Room("Nhà trọ 8", "1,7 triệu VND/căn", "78 Tân Hòa Đông, Quận 6", R.drawable.tro8);
//        Room r9 = new Room("Nhà trọ 9", "1,8 triệu VND/căn", "88 Tân Hòa Đông, Quận 6", R.drawable.tro1);
//        Room r10 = new Room("Nhà trọ 10", "1,9 triệu VND/căn", "98 Tân Hòa Đông, Quận 6", R.drawable.tro2);

        list.add(r1);
        list.add(r2);
        list.add(r3);
        list.add(r4);
        list.add(r5);
        list.add(r6);
//        list.add(r7);
//        list.add(r8);
//        list.add(r9);
//        list.add(r10);

        return list;
    }



    public void filterDistance(View v){
        androidx.appcompat.widget.PopupMenu popup = new androidx.appcompat.widget.PopupMenu(this,v);
        popup.setOnMenuItemClickListener(RoomListActivity.this);
        popup.inflate(R.menu.distance_menu);
        popup.show();
    }
    public void sortPrice(View v){
        androidx.appcompat.widget.PopupMenu popup = new androidx.appcompat.widget.PopupMenu(this,v);
        popup.setOnMenuItemClickListener(RoomListActivity.this);
        popup.inflate(R.menu.price_menu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.item1:
//                Toast.makeText(this, "Lọc theo 500m", Toast.LENGTH_SHORT).show();
//                return true;
//            case R.id.item2:
//                Toast.makeText(this, "Lọc theo 1000m", Toast.LENGTH_SHORT).show();
//                return true;
//            case R.id.item3:
//                Toast.makeText(this, "Lọc theo 1500m", Toast.LENGTH_SHORT).show();
//                return true;
//            case R.id.sort1:
//                Toast.makeText(this, "Sắp xếp theo giá tăng dần", Toast.LENGTH_SHORT).show();
//                return true;
//            case R.id.sort2:
//                Toast.makeText(this, "Sắp xếp theo giá giảm dần", Toast.LENGTH_SHORT).show();
//                return true;
//            default:
//                return false;
//        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_admin, menu);
        return true;
    }
}