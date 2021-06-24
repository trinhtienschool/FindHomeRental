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
        realtimeBlurView = findViewById(R.id.realtimeBlurView);
        pb_waiting = findViewById(R.id.pb_waiting);
        userManagerPresenter = new UserManagerPresenter(this,this);
        recyclerView = findViewById(R.id.recycler_view_user);
        progressBar = findViewById(R.id.progress_bar);
        imgBtnBack = findViewById(R.id.ImageButtonBack);

        imgBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserListActivity.this, MainAdminActivity.class);
                startActivity(intent);
            }
        });
//        btnFilter = findViewById(R.id.ImgButtonFilter);
//        btnSort = findViewById(R.id.ImgButtonSort);
//        txtTotalResults = findViewById(R.id.TextViewTotalResult);
        userAdapter = new UserAdapter();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(userAdapter);

//        recyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
//            @Override
//            public void loadMoreItems() {
//                isLoading = true;
//                progressBar.setVisibility(View.VISIBLE);
//                currentPage += 1;
//                loadNextPage();
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
    }

    private void loadNextPage() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<User> list = getListUser();
                mListUser.addAll(list);
                userAdapter.notifyDataSetChanged();

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
        mListUser = getListUser();
        userAdapter.setData(mListUser);
        userManagerPresenter.getAllUsers();
    }
    private List<User> getListUser(){
        Toast.makeText(this, "Đang tải chờ xiu...", Toast.LENGTH_SHORT).show();

        List<User> list = new ArrayList<>();
        User u1 = new User("user1","Trần Nhật Thy", "nhatthy1224@gmail.com","image");
        User u2 = new User("user1","Trần Nhật Thy", "nhatthy1224@gmail.com","image");
        User u3 = new User("user1","Trần Nhật Thy", "nhatthy1224@gmail.com","image");
        User u4 = new User("user1","Trần Nhật Thy", "nhatthy1224@gmail.com","image");
        User u5 = new User("user1","Trần Nhật Thy", "nhatthy1224@gmail.com","image");
        User u6 = new User("user1","Trần Nhật Thy", "nhatthy1224@gmail.com","image");
        User u7 = new User("user1","Trần Nhật Thy", "nhatthy1224@gmail.com","image");
        User u8 = new User("user1","Trần Nhật Thy", "nhatthy1224@gmail.com","image");
        User u9 = new User("user1","Trần Nhật Thy", "nhatthy1224@gmail.com","image");
        User u10 = new User("user1","Trần Nhật Thy", "nhatthy1224@gmail.com","image");

        list.add(u1);
        list.add(u2);
        list.add(u3);
        list.add(u4);
        list.add(u5);
        list.add(u6);
        list.add(u7);
        list.add(u8);
        list.add(u9);
        list.add(u10);

        return list;
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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_search, menu);
//
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//        searchView.setMaxWidth(Integer.MAX_VALUE);
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                UserAdapter.getFilter().filter(query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                UserAdapter.getFilter().filter(newText);
//                return false;
//            }
//        });
//
//        return true;
//    }
//
//    @Override
//    public void onBackPressed() {
//        if(!searchView.isIconified()){
//            searchView.setIconified(true);
//            return;
//        }
//        super.onBackPressed();
//    }
//
//    public void filterDistance(View v){
//        PopupMenu popup = new PopupMenu(this,v);
//        popup.setOnMenuItemClickListener(this);
//        popup.inflate(R.menu.distance_menu);
//        popup.show();
//    }
//    public void sortPrice(View v){
//        PopupMenu popup = new PopupMenu(this,v);
//        popup.setOnMenuItemClickListener(this);
//        popup.inflate(R.menu.price_menu);
//        popup.show();
//    }
//
//    @Override
//    public boolean onMenuItemClick(MenuItem item) {
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
//
//    }
}