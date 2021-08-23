package com.trinhtien2212.findroomrentalmobile;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mmin18.widget.RealtimeBlurView;
import com.trinhtien2212.findroomrentalmobile.adapter.RoomAdminAdapter;
import com.trinhtien2212.findroomrentalmobile.adapter.UserAdapter;
import com.trinhtien2212.findroomrentalmobile.model.Notification;
import com.trinhtien2212.findroomrentalmobile.model.Room;
import com.trinhtien2212.findroomrentalmobile.model.User;
import com.trinhtien2212.findroomrentalmobile.presenter.AdminUserPresenter;
import com.trinhtien2212.findroomrentalmobile.presenter.IUserResult;
import com.trinhtien2212.findroomrentalmobile.presenter.StatusResult;
import com.trinhtien2212.findroomrentalmobile.presenter.UserManagerPresenter;
import com.trinhtien2212.findroomrentalmobile.ui.Util;
import com.trinhtien2212.findroomrentalmobile.ui.home.IGetMyLocation;
import com.trinhtien2212.findroomrentalmobile.ui.home.RoomDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UserListActivity extends AppCompatActivity implements StatusResult, IUserResult, IGetMyLocation {
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> mListUser;
    private ProgressBar progressBar;
    private ImageButton imgBtnBack;
    private SearchView searchView;
    private UserManagerPresenter userManagerPresenter;
    private RealtimeBlurView realtimeBlurView;
    private ProgressBar pb_waiting;
    private AdminUserPresenter adminUserPresenter;
    private int position_index_pending;
    private Button btnThoat;
    private Button btnXoa;
    private TextView txtWarning;
    private boolean isLoading, isLastPage;
    private int currentPage=1, totalPage=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        getSupportActionBar().setTitle("Danh sách người dùng");
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button
        adminUserPresenter = AdminUserPresenter.getInstance();
        assign();
        buildRecyclerView();
        actionItemRecyclerView();
        setFirstData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Util.checkNetwork(this,this);
    }

    private void buildRecyclerView() {
        userAdapter = new UserAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(userAdapter);

    }

    private void assign() {
        realtimeBlurView = findViewById(R.id.realtimeBlurView);
        pb_waiting = findViewById(R.id.pb_waiting);
        userManagerPresenter = new UserManagerPresenter(this,this);
        recyclerView = findViewById(R.id.recycler_view_user);
        progressBar = findViewById(R.id.progress_bar);
    }

    //Load data
    private void setFirstData(){
        userManagerPresenter.getAllUsers();
    }

    @Override
    public void onFail() {
        Toast.makeText(this,"Có lỗi xảy ra, vui lòng thử lại",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSuccess() {
        mListUser.remove(position_index_pending);
        userAdapter.notifyDataSetChanged();
        Toast.makeText(this,"Thành công",Toast.LENGTH_LONG).show();
    }

    @Override
    public void returnUser(List<User> users) {
        mListUser = users;
        userAdapter.setData(mListUser);
        showWaiting(View.INVISIBLE);
    }
    private void showWaiting(int waiting){
        realtimeBlurView.setVisibility(waiting);
        pb_waiting.setVisibility(waiting);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void actionItemRecyclerView() {
        userAdapter.setOnItemClickListener(new UserAdapter.OnItemClickListener() {

            @Override
            public void onDeleteClick(int position) {
                Log.e("DeleteClick",position+"");
                if(!Util.checkNetwork(UserListActivity.this,UserListActivity.this)) {
                    return;
                }
                    position_index_pending = position;

                    //startdialog
                    Dialog dialog = new Dialog(UserListActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.warning);
                    Window window = dialog.getWindow();
                    if (window == null) {
                        return;
                    }
                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                    window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    WindowManager.LayoutParams windowatribute = window.getAttributes();
                    windowatribute.gravity = Gravity.CENTER;
                    window.setAttributes(windowatribute);
                    btnThoat = dialog.findViewById(R.id.btnthoatid);
                    btnXoa = dialog.findViewById(R.id.btnxoaid);
                    txtWarning = dialog.findViewById(R.id.textWarning);
                    txtWarning.setText("Xóa người dùng vĩnh viễn");
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
                            userManagerPresenter.deleteUser(mListUser.get(position));
                        }
                    });

                    dialog.show();

                //enddialog

            }

            @Override
            public void onAdminClick(int position) {
                Log.e("AdminClick",position+"");


            }

            @Override
            public void onHouseClick(int position) {
                if(!Util.checkNetwork(UserListActivity.this,UserListActivity.this)) return;
                User user = mListUser.get(position);
                Intent intent = new Intent(UserListActivity.this,RoomListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userId",user.getUserUid());
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onNoteClick(int position) {
                if(!Util.checkNetwork(UserListActivity.this,UserListActivity.this)) return;
                Log.e("NoteClick",position+"");
                User user = mListUser.get(position);
                Intent intent = new Intent(UserListActivity.this,WarnList.class);
                Bundle bundle = new Bundle();
                bundle.putString("userId",user.getUserUid());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public void returnMyLocation(String location) {

    }

    @Override
    public void showSnackbar(String message) {
        Util.showSnackbar(findViewById(R.id.frame_layout),message);
    }
}