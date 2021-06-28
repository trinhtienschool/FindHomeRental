package com.trinhtien2212.findhomerental.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseAuth;
import com.trinhtien2212.findhomerental.MainActivity;

import com.trinhtien2212.findhomerental.MainAdminActivity;
import com.trinhtien2212.findhomerental.R;
import com.trinhtien2212.findhomerental.adapter.MyRoomAdapter;
import com.trinhtien2212.findhomerental.adapter.RoomHomeAdapter;
import com.trinhtien2212.findhomerental.model.Room;
import com.trinhtien2212.findhomerental.presenter.RoomPresenter;
import com.trinhtien2212.findhomerental.presenter.RoomsResult;
import com.trinhtien2212.findhomerental.presenter.SearchPresenter;
import com.trinhtien2212.findhomerental.ui.PaginationScrollListener;
import com.trinhtien2212.findhomerental.ui.Util;

import java.util.List;

public class HomeFragment extends Fragment implements RoomsResult, RoomHomeAdapter.ItemClickListener, IGetMyLocation {

    private ImageButton btnSearch;
    private EditText edtSearch;
    private RecyclerView recyclerView;
    private RoomHomeAdapter adapter;
    private List<Room> mListRoom;
    private MainActivity mainActivity;
    private RoomPresenter roomPresenter;
    private View root;
    private boolean isLoading, isLastPage;
    private int currentPage=1, totalPage=2;

    private SearchPresenter searchPresenter;
    public HomeFragment() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mainActivity = (MainActivity) getActivity();
        root = inflater.inflate(R.layout.fragment_home, container, false);
        assign();

        buildRecyclerView();

        setFirstData();
        // Search
        search();
        return root;
    }

    private void search() {
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(edtSearch.getText().toString())){
                    Toast.makeText(mainActivity, "Bạn chưa nhập thông tin", Toast.LENGTH_LONG).show();
                } else {
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(mainActivity,SearchActivity.class);
                    bundle.putString("address",edtSearch.getText().toString());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        Util.checkNetwork(mainActivity,this);

    }
    private void buildRecyclerView() {
        adapter = new RoomHomeAdapter(mListRoom, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mainActivity, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
        roomPresenter = new RoomPresenter(this);
        recyclerView.addOnScrollListener(new PaginationScrollListener(gridLayoutManager) {
            @Override
            public void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
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

    private void assign(){

        recyclerView = root.findViewById(R.id.recycler_home);
        btnSearch = root.findViewById(R.id.ImageButtonSearch);
        edtSearch = root.findViewById(R.id.EditTextSearch);
    }

    //Load data
    private void setFirstData(){

        roomPresenter.getRandomRooms();
        Toast.makeText(mainActivity, "Đang tải chờ xíu...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void returnRooms(List<Room> rooms) {
        if(rooms == null){
            mainActivity.showSnackbar("Lỗi tải trang, vui lòng thử lại");
        }else {
            mListRoom = rooms;
            adapter.setData(mListRoom);
            mainActivity.showWaiting(View.INVISIBLE);
        }
        if(FirebaseAuth.getInstance().getCurrentUser() !=null){
            Log.e("MainStart","Dang kiem");
           mainActivity.setShowAdmin();
        }
    }


    @Override
    public void onItemClick(int position) {
        // Todo ITEM
        Log.e("Home", "itemClick: clicked" + position);
        Room room = mListRoom.get(position);
        Log.e("room",room.toString());
        Intent intent = new Intent(mainActivity, RoomDetail.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("room",room);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void returnMyLocation(String location) {

    }

    @Override
    public void showSnackbar(String message) {
        mainActivity.showSnackbar(message);
    }
}