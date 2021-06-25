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
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mmin18.widget.RealtimeBlurView;
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
import com.trinhtien2212.findhomerental.ui.Util;
import com.trinhtien2212.findhomerental.ui.home.IGetMyLocation;
import com.trinhtien2212.findhomerental.ui.home.RoomDetail;

import java.util.List;

public class RoomListActivity extends AppCompatActivity implements IGetMyLocation, RoomsResult, StatusResult, PopupMenu.OnMenuItemClickListener {
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

    private  Dialog dialogreport;

    private TextView txtReportInfo;
    private TextView txtRoomInfo;
    private RoomPresenter roomPresenter;
    private SearchPresenter searchPresenter;
    private NotificationPresenter notificationPresenter;
    private boolean isLoading, isLastPage;
    private int currentPage = 1, totalPage = 2;
    private boolean isShowDialogReport;
    private boolean isResultSearch = false;
    private boolean isResultFilter = false;
    private boolean isResultSort = false;
    private boolean isDeleting = false;
    private boolean isNoting = false;
    private FrameLayout frameLayout;
    private RealtimeBlurView realtimeBlurView;
    private ProgressBar pb_waiting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);

        getSupportActionBar().setTitle("Danh sách bài viết");

        notificationPresenter = new NotificationPresenter(this);
        isShowDialogReport = false;
        realtimeBlurView = findViewById(R.id.realtimeBlurView);
        pb_waiting = findViewById(R.id.pb_waiting);
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button
        assign();
        frameLayout = findViewById(R.id.activity_room_list_frame);
        buildRecyclerView();
        actionItemRecyclerView();
        setFirstData();
        searchPresenter = new SearchPresenter(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        dialogreport = new Dialog(getApplicationContext());
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
                Log.e("RoomID",room.getRoomID());
                room_pending_delete = position;
                roomPresenter.setRoom(room);

                //ToDo Nhuan
                //startdialog
                Dialog dialog = new Dialog(RoomListActivity.this);
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
                        isDeleting =true;
                        Toast.makeText(RoomListActivity.this,"Đang xóa...",Toast.LENGTH_SHORT).show();
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

                //Todo Nhuan
                //startdialog
                dialogreport = new Dialog(RoomListActivity.this);
                dialogreport.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogreport.setContentView(R.layout.report);
                txtRoomInfo=dialogreport.findViewById(R.id.roominfo);
                txtReportInfo=dialogreport.findViewById(R.id.reportinfo);
                txtRoomInfo.setText(roomInfo);

                int width = (int)(getResources().getDisplayMetrics().widthPixels*0.90);
                int height = (int)(getResources().getDisplayMetrics().heightPixels*0.90);

                dialogreport.getWindow().setLayout(width, height);
                Window window=dialogreport.getWindow();
                if(window==null){
                    return;
                }
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                WindowManager.LayoutParams windowatribute=window.getAttributes();
                windowatribute.gravity= Gravity.CENTER;
                window.setAttributes(windowatribute);
                btngui=dialogreport.findViewById(R.id.btnGui);
                btngui.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isNoting = true;
                        Toast.makeText(RoomListActivity.this,"Đang gửi...",Toast.LENGTH_LONG).show();
                        String reportinfo=txtReportInfo.getText().toString();
                        Log.e("ReportInfo",reportinfo);
                        Log.e("RoomID",room.getRoomID());
                        Log.e("UserId",room.getUserCreatedId());
                        Notification notification = new Notification(room.getAddress(), reportinfo,room.getUserCreatedId());
                        notificationPresenter.addNotification(notification);
                    }
                });

                dialogreport.show();
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
        recyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            public void loadMoreItems() {
//                isLoading = true;
//                progressBar.setVisibility(View.VISIBLE);
//                currentPage += 1;
//                if(isResultSearch){
//                    getListRoom();
//                }else if(isResultFilter){
//
//                }

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
    private void getListRoom(){
        searchPresenter.getNext();
    }
    //Load data
    private void search(String address){
        if(Util.checkNetwork(this,this)) {
            searchPresenter.searchLocation(address);
            showWaiting(View.VISIBLE);
        }else showWaiting(View.INVISIBLE);
    }

    private void assign() {
        recyclerView = findViewById(R.id.recycler_view_room);
        progressBar = findViewById(R.id.progress_bar);
    }

    @Override
    public void returnRooms(List<Room> rooms) {
        mListRoom = rooms;
        adapter.setData(mListRoom);
       showWaiting(View.INVISIBLE);
    }

    @Override
    public void onFail() {
        Toast.makeText(RoomListActivity.this, "Thất bại", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSuccess() {
        //Todo Nhuan
        Log.e("onSucccccccccc","Co vao");
        if (isShowDialogReport) {
            isShowDialogReport = false;
            dialogreport.dismiss();
            //dismiss here
        }else if(isNoting){
            isNoting = false;
            Toast.makeText(RoomListActivity.this,"Thành công",Toast.LENGTH_LONG).show();
        }

        else if(isDeleting){
            isDeleting  = false;
            Toast.makeText(RoomListActivity.this, "Thành công", Toast.LENGTH_LONG).show();
            mListRoom.remove(room_pending_delete);
            adapter.notifyDataSetChanged();
        }
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
//                adapter.getFilter().filter(query);
                search(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                adapter.getFilter().filter(newText);
                return true;
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
            case R.id.filter_1month:
                Toast.makeText(this, "Lọc theo < 1 tháng", Toast.LENGTH_SHORT).show();
                    roomPresenter.filterRoom(0,1);
                    isResultSearch = false;
                    isResultSort = false;
                    isResultFilter = true;
                    showWaiting(View.VISIBLE);
                return true;
            case R.id.filter_1_3_month:
                Toast.makeText(this, "Lọc theo từ 1 đến 3 tháng", Toast.LENGTH_SHORT).show();
                roomPresenter.filterRoom(1,3);
                isResultSearch = false;
                isResultSort = false;
                isResultFilter = true;
                showWaiting(View.VISIBLE);
                return true;
            case R.id.filter_3month:
                Toast.makeText(this, "Lọc theo lớn hơn 3 tháng", Toast.LENGTH_SHORT).show();
                roomPresenter.filterRoom(3,6);
                isResultSearch = false;
                isResultSort = false;
                isResultFilter = true;
                showWaiting(View.VISIBLE);
                return true;
            case R.id.item_distance_increase:
                Toast.makeText(this, "Sắp xếp theo giá tăng dần", Toast.LENGTH_SHORT).show();
                roomPresenter.sortRoom(true);
                isResultSearch = false;
                isResultSort = true;
                isResultFilter = false;
                showWaiting(View.VISIBLE);
                return true;
            case R.id.item_distance_decrease:
                Toast.makeText(this, "Sắp xếp theo giá giảm dần", Toast.LENGTH_SHORT).show();
                roomPresenter.sortRoom(false);
                isResultSearch = false;
                isResultSort = true;
                isResultFilter = false;
                showWaiting(View.VISIBLE);
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

    @Override
    public void returnMyLocation(String location) {

    }

    @Override
    public void showSnackbar(String message) {
        Util.showSnackbar(frameLayout,message);
    }
    private void showWaiting(int waiting){
        realtimeBlurView.setVisibility(waiting);
        pb_waiting.setVisibility(waiting);
    }
}
