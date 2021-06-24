package com.trinhtien2212.findhomerental;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mmin18.widget.RealtimeBlurView;
import com.trinhtien2212.findhomerental.adapter.UserAdapter;
import com.trinhtien2212.findhomerental.model.User;
import com.trinhtien2212.findhomerental.presenter.IUserResult;
import com.trinhtien2212.findhomerental.presenter.StatusResult;
import com.trinhtien2212.findhomerental.presenter.UserManagerPresenter;

import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends AppCompatActivity implements StatusResult, IUserResult {
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> mListUser;
    private ProgressBar progressBar;
    private ImageButton imgBtnBack;
    private SearchView searchView;
    private UserManagerPresenter userManagerPresenter;
    private RealtimeBlurView realtimeBlurView;
    private ProgressBar pb_waiting;

    private boolean isLoading, isLastPage;
    private int currentPage=1, totalPage=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        getSupportActionBar().setTitle("Danh sách người dùng");
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button

        assign();
        buildRecyclerView();
        setFirstData();
    }

    private void buildRecyclerView() {
        userAdapter = new UserAdapter();
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

    public void actionItemRecyclerView(){
        userAdapter.setOnItemClickListener(new UserAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                // Todo DELETE
            }
        });
    }

}