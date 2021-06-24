package com.trinhtien2212.findhomerental.ui.love;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mmin18.widget.RealtimeBlurView;
import com.google.firebase.auth.FirebaseAuth;
import com.trinhtien2212.findhomerental.MainActivity;
import com.trinhtien2212.findhomerental.R;
import com.trinhtien2212.findhomerental.adapter.LoveAdapter;
import com.trinhtien2212.findhomerental.model.Room;
import com.trinhtien2212.findhomerental.presenter.BookmarkPresenter;
import com.trinhtien2212.findhomerental.presenter.RoomsResult;
import com.trinhtien2212.findhomerental.presenter.StatusResult;
import com.trinhtien2212.findhomerental.ui.PaginationScrollListener;
import com.trinhtien2212.findhomerental.ui.home.RoomDetail;


import java.util.ArrayList;
import java.util.List;


public class LoveFragment extends Fragment implements RoomsResult, StatusResult {

    private RecyclerView recyclerView;
    private LoveAdapter adapter;
    private List<Room> mListRoom;
    private MainActivity mainActivity;
    private int room_pending_delete;
    private View root;
    private boolean isLoading, isLastPage;
    private int currentPage=1, totalPage=2;
    private RealtimeBlurView realtimeBlurView;
    private ProgressBar progressBar;
    private Button btnThoat;
    private Button btnXoa;
    private BookmarkPresenter bookmarkPresenter;
    public LoveFragment(){
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mainActivity = (MainActivity) getActivity();
        mListRoom = new ArrayList<Room>();
        root = inflater.inflate(R.layout.fragment_love, container, false);
        bookmarkPresenter = new BookmarkPresenter(this,this);
        assign();

        buildRecyclerView();
        actionItemRecyclerView();

//        bookmarkPresenter.("0b4oSVQ6aB6fpmvbkVvo",FirebaseAuth.getInstance().getCurrentUser().getUid());
//
        setFirstData();
        return root;
    }
    private void assign(){
        realtimeBlurView = root.findViewById(R.id.realtimeBlurView4);
        progressBar = root.findViewById(R.id.pb_saving4);
        recyclerView = root.findViewById(R.id.recycler_home4);
    }
    //Load data
    private void setFirstData(){
        //Todo FirebaseAuth.getInstance().getCurrentUser().getUid()
        bookmarkPresenter.getAllBookmarks(FirebaseAuth.getInstance().getCurrentUser().getUid());
        //ToDo
//        bookmarkPresenter.getAllBookmarks();
//        RoomDB roomDB = RoomDB.getInstance();
//        roomDB.getRandomRooms(this);
//
//        Toast.makeText(mainActivity, "Load data page", Toast.LENGTH_SHORT).show();
    }
    private void getListRoom(){
        bookmarkPresenter.getNext();
    }
    @Override
    public void returnRooms(List<Room> rooms) {

        if(rooms!=null){
            mListRoom.addAll(rooms);
            adapter.setData(mListRoom);
            totalPage = bookmarkPresenter.getTotalPage();
//            txtTotalResults.setText(searchPresenter.getTotalResults()+"");
            if(currentPage == totalPage){
                isLastPage = true;
            }
            recyclerView.post(new Runnable() {
                public void run() {
                    // There is no need to use notifyDataSetChanged()
                    adapter.notifyDataSetChanged();
                }
            });
            isLoading = false;
//            bookmarkPresenter.removeRoom("0b4oSVQ6aB6fpmvbkVvo",FirebaseAuth.getInstance().getCurrentUser().getUid());
        }else Toast.makeText(mainActivity,"Chưa có yêu thích nào",Toast.LENGTH_LONG).show();

        progressBar.setVisibility(View.GONE);
        showWaiting(View.INVISIBLE);
    }
    private void showWaiting(int waiting){
        realtimeBlurView.setVisibility(waiting);
        progressBar.setVisibility(waiting);
    }
    private void buildRecyclerView(){
        adapter = new LoveAdapter();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mainActivity);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            public void loadMoreItems() {
                isLoading = true;
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
    public void actionItemRecyclerView() {
        adapter.setOnItemClickListener(new LoveAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int positon) {
                // Todo item
                Log.e("Room", mListRoom.get(positon).toString());
                Bundle bundle = new Bundle();
                bundle.putSerializable("room",mListRoom.get(positon));
                Intent intent = new Intent(mainActivity, RoomDetail.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onDeleteClick(int position) {
                // ToDo button DELETE

                Log.e("Dang vao Delete Love",position+"");
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
                        room_pending_delete = position;
                        bookmarkPresenter.removeRoom(mListRoom.get(position).getRoomID(),FirebaseAuth.getInstance().getCurrentUser().getUid());

                    }
                });

                dialog.show();
                //enddialog


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