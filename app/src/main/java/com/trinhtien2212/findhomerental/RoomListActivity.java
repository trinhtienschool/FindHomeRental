package com.trinhtien2212.findhomerental;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.trinhtien2212.findhomerental.adapter.RoomAdminAdapter;
import com.trinhtien2212.findhomerental.model.Notification;
import com.trinhtien2212.findhomerental.model.Room;
import com.trinhtien2212.findhomerental.presenter.NotificationPresenter;
import com.trinhtien2212.findhomerental.presenter.RoomPresenter;
import com.trinhtien2212.findhomerental.presenter.RoomsResult;
import com.trinhtien2212.findhomerental.presenter.SearchPresenter;
import com.trinhtien2212.findhomerental.presenter.StatusResult;
import com.trinhtien2212.findhomerental.ui.PaginationScrollListener;
import com.trinhtien2212.findhomerental.ui.home.RoomDetail;

import java.util.List;

public class RoomListActivity extends AppCompatActivity implements RoomsResult, StatusResult, PopupMenu.OnMenuItemClickListener {
    private RecyclerView recyclerView;
    private RoomAdminAdapter adapter;
    private List<Room> mListRoom;
    private ProgressBar progressBar;
    private int room_pending_delete;
    private SearchView searchView;
    private ImageButton btnFilter, btnSort;
    private TextView txtTotalResults;
    private ImageButton imgBtnBack;
    private Button btnThoat;
    private Button btnXoa;
    private Button btngui;
    private  Dialog dialog = new Dialog(getApplicationContext());
    private TextView txtReportInfo;
    private TextView txtRoomInfo;
    private RoomPresenter roomPresenter;
    private SearchPresenter searchPresenter;
    private NotificationPresenter notificationPresenter;
    private boolean isLoading, isLastPage;
    private int currentPage = 1, totalPage = 2;
    private boolean isShowDialogReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);

        getSupportActionBar().setTitle("Danh sách bài viết");

        notificationPresenter = new NotificationPresenter(this);
        isShowDialogReport = false;

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button
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
                Log.e("Room", mListRoom.get(position).toString());
                Bundle bundle = new Bundle();
                bundle.putSerializable("room", mListRoom.get(position));
                Intent intent = new Intent(RoomListActivity.this, RoomDetail.class);
                intent.putExtras(bundle);
                startActivity(intent);

            }

            @Override
            public void onDeleteClick(int position) {
                // Todo DELETE
                Room room = mListRoom.get(position);
                room_pending_delete = position;
                roomPresenter = new RoomPresenter(RoomListActivity.this, RoomListActivity.this);
                //ToDo Nhuan
                //startdialog
                Dialog dialog = new Dialog(getApplicationContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.warning);
                Window window=dialog.getWindow();
                if(window==null){
                    return;
                }
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                WindowManager.LayoutParams windowatribute=window.getAttributes();
                windowatribute.gravity= Gravity.CENTER;
                window.setAttributes(windowatribute);
                btnThoat=dialog.findViewById(R.id.btnthoatid);
                btnXoa=dialog.findViewById(R.id.btnxoaid);
                btnThoat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btnXoa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        roomPresenter.deleteRoom();

                    }
                });

                dialog.show();
                //enddialog


            }

            @Override
            public void onReportClick(int position) {
                // Todo REPORT
                Room room = mListRoom.get(position);
                String roomInfo=room.getDescription();
                txtRoomInfo=(TextView)findViewById(R.id.roominfo);
                txtReportInfo=(TextView) findViewById(R.id.reportinfo);
                txtRoomInfo.setText(roomInfo);
                String reportinfo=txtReportInfo.getText().toString();
                //Todo Nhuan
                //startdialog
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.warning);
                Window window=dialog.getWindow();
                if(window==null){
                    return;
                }
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                WindowManager.LayoutParams windowatribute=window.getAttributes();
                windowatribute.gravity= Gravity.CENTER;
                window.setAttributes(windowatribute);
                btngui.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Notification notification = new Notification(room.getAddress(), reportinfo, FirebaseAuth.getInstance().getCurrentUser().getUid());
                        notificationPresenter.addNotification(notification);
                    }
                });

                dialog.show();
                //enddialog


                //Message
                //M

                isShowDialogReport = true;
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
        Toast.makeText(RoomListActivity.this, "Thất bại", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSuccess() {
        //Todo Nhuan
        if (isShowDialogReport) {
            isShowDialogReport = false;
            dialog.dismiss();
            //dismiss here
        }

        Toast.makeText(RoomListActivity.this, "Thành công", Toast.LENGTH_LONG).show();
        mListRoom.remove(room_pending_delete);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_admin, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        searchView = (SearchView) menu.findItem(R.id.action_search2).getActionView();
        Log.e("Da qua cast search", "Da qua");
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
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    public void filterDistance(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(RoomListActivity.this);
        popup.inflate(R.menu.distance_menu);
        popup.show();
    }

    public void sortPrice(View v) {
        androidx.appcompat.widget.PopupMenu popup = new androidx.appcompat.widget.PopupMenu(this, v);
        popup.setOnMenuItemClickListener(RoomListActivity.this);
        popup.inflate(R.menu.price_menu);
        popup.show();
    }


    //ToDo nhan su kien loc, sort, filter
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
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
    // back
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
