package com.trinhtien2212.findhomerental;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.trinhtien2212.findhomerental.adapter.RoomAdminAdapter;
import com.trinhtien2212.findhomerental.model.Room;
import com.trinhtien2212.findhomerental.presenter.RoomPresenter;
import com.trinhtien2212.findhomerental.presenter.RoomsResult;
import com.trinhtien2212.findhomerental.presenter.SearchPresenter;
import com.trinhtien2212.findhomerental.presenter.StatusResult;
import com.trinhtien2212.findhomerental.ui.PaginationScrollListener;

import java.util.List;

public class RoomListActivity extends AppCompatActivity implements RoomsResult, StatusResult, PopupMenu.OnMenuItemClickListener {
    private RecyclerView recyclerView;
    private RoomAdminAdapter adapter;
    private List<Room> mListRoom;
    private ProgressBar progressBar;
    private int room_pending_delete;
    SearchView searchView;
    private ImageButton btnFilter, btnSort;
    private TextView txtTotalResults;
    private ImageButton imgBtnBack;
    private RoomPresenter roomPresenter;
    SearchPresenter searchPresenter;

    private boolean isLoading, isLastPage;
    private int currentPage=1, totalPage=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);

        getSupportActionBar().setTitle("Danh sách bài viết");

        assign();
        buildRecyclerView();
        actionItemRecyclerView();
        setFirstData();
    }



    private void setFirstData() {
        roomPresenter.getRandomRooms();
        Toast.makeText(this, "Đang tải ...", Toast.LENGTH_SHORT).show();
    }

    private void actionItemRecyclerView() {
        adapter.setOnItemClickListener(new RoomAdminAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Todo item

            }

            @Override
            public void onDeleteClick(int position) {
                // Todo DELETE
            }

            @Override
            public void onReportClick(int position) {
                // Todo REPORT
            }
        });
    }

    private void buildRecyclerView() {
        adapter = new RoomAdminAdapter();
        roomPresenter = new RoomPresenter(this, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void assign() {
        recyclerView = findViewById(R.id.recycler_view_room);
        progressBar = findViewById(R.id.progress_bar);
    }

    @Override
    public void returnRooms(List<Room> rooms) {
        mListRoom = rooms;
        adapter.setData(mListRoom);
    }

    @Override
    public void onFail() {
        Toast.makeText(RoomListActivity.this,"Thất bại",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSuccess() {
        Toast.makeText(RoomListActivity.this,"Thành công",Toast.LENGTH_LONG).show();
        mListRoom.remove(room_pending_delete);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public void onBackPressed() {
        if(!searchView.isIconified()){
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    public void filterDistance(View v){
        PopupMenu popup = new PopupMenu(this,v);
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
        switch (item.getItemId()){
            case R.id.filter_3000:
                Toast.makeText(this, "Lọc theo 500m", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.filter_5000:
                Toast.makeText(this, "Lọc theo 1000m", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.filter_M5000:
                Toast.makeText(this, "Lọc theo 1500m", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item_distance_increase:
                Toast.makeText(this, "Sắp xếp theo giá tăng dần", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item_distance_decrease:
                Toast.makeText(this, "Sắp xếp theo giá giảm dần", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }

}
