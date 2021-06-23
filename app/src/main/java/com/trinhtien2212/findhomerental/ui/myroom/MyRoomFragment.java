package com.trinhtien2212.findhomerental.ui.myroom;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.mmin18.widget.RealtimeBlurView;
import com.google.firebase.auth.FirebaseAuth;
import com.trinhtien2212.findhomerental.MainActivity;
import com.trinhtien2212.findhomerental.R;
import com.trinhtien2212.findhomerental.adapter.MyRoomAdapter;
import com.trinhtien2212.findhomerental.adapter.RoomHomeAdapter;
import com.trinhtien2212.findhomerental.dao.RoomDB;
import com.trinhtien2212.findhomerental.model.Room;
import com.trinhtien2212.findhomerental.presenter.RoomPresenter;
import com.trinhtien2212.findhomerental.presenter.RoomsResult;
import com.trinhtien2212.findhomerental.presenter.StatusResult;
import com.trinhtien2212.findhomerental.ui.add_room.AddRoomActivity;

import java.util.List;


public class MyRoomFragment extends Fragment implements RoomsResult,StatusResult {

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
    private int room_pending_delete;
    private Button btnThoat;
    private Button btnXoa;
    public MyRoomFragment(){
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mainActivity = (MainActivity) getActivity();
        root = inflater.inflate(R.layout.fragment_my_room, container, false);
        assign();
        buildRecyclerView();

        actionItemRecyclerView();

        setFirstData();
        return root;
    }


    // ToDo Remove Item
    public void removeItem(int position){
        mListRoom.remove(position);
        adapter.notifyDataSetChanged();
    }
    private void assign(){
        realtimeBlurView = root.findViewById(R.id.realtimeBlurView3);
        progressBar = root.findViewById(R.id.pb_saving3);
        recyclerView = root.findViewById(R.id.recycler_home3);
    }

    //Load data
    private void setFirstData(){
//        roomPresenter.getAllRoomsOfUser("mfSmbqjLoKd8YgphOJuZrQtJ7cj1");
        roomPresenter.getAllRoomsOfUser(FirebaseAuth.getInstance().getCurrentUser().getUid());
    }

    @Override
    public void returnRooms(List<Room> rooms) {
        if(rooms == null){
            Toast.makeText(mainActivity,"Chưa có bài đăng phòng trọ nào",Toast.LENGTH_LONG).show();
            showWaiting(View.INVISIBLE);
        }else {
            mListRoom = rooms;
            adapter.setData(mListRoom);
            showWaiting(View.INVISIBLE);
        }
    }
    private void showWaiting(int waiting){
        realtimeBlurView.setVisibility(waiting);
        progressBar.setVisibility(waiting);
    }

    private void buildRecyclerView(){
        adapter = new MyRoomAdapter();
        roomPresenter = new RoomPresenter(this,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mainActivity);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void actionItemRecyclerView(){
        adapter.setOnItemClickListener(new MyRoomAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int positon) {
                // Todo item
                Log.e("Room", mListRoom.get(positon).toString());
            }

            @Override
            public void onDeleteClick(int position) {
                // ToDo button DELETE
//                removeItem(position);
                Room room = mListRoom.get(position);
                room_pending_delete = position;
                //Todo
                //startdialog
                Dialog dialog = new Dialog(getContext());
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
                        roomPresenter.setRoom(room);
                        roomPresenter.deleteRoom();

                    }
                });

                dialog.show();
                //enddialog
                Log.e("RoomID",room.getRoomID());


            }

            @Override
            public void onEditClick(int position) {
                // Todo Button EDIT
                Room room = mListRoom.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("room",room);
                Intent intent = new Intent(mainActivity, AddRoomActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }

        });
    }

    @Override
    public void onFail() {
        Toast.makeText(mainActivity,"Thất bại",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onSuccess() {
        Toast.makeText(mainActivity,"Thành công",Toast.LENGTH_LONG).show();
        mListRoom.remove(room_pending_delete);
        adapter.notifyDataSetChanged();
    }
}