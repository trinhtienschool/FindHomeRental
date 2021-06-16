package com.trinhtien2212.findhomerental.ui.love;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mmin18.widget.RealtimeBlurView;
import com.google.firebase.auth.FirebaseAuth;
import com.trinhtien2212.findhomerental.MainActivity;
import com.trinhtien2212.findhomerental.PaginationScrollListener;
import com.trinhtien2212.findhomerental.R;
import com.trinhtien2212.findhomerental.adapter.LoveAdapter;
import com.trinhtien2212.findhomerental.model.Room;
import com.trinhtien2212.findhomerental.presenter.BookmarkPresenter;
import com.trinhtien2212.findhomerental.presenter.RoomsResult;


import java.util.ArrayList;
import java.util.List;


public class LoveFragment extends Fragment implements RoomsResult {

    private RecyclerView recyclerView;
    private LoveAdapter adapter;
    private List<Room> mListRoom;
    private MainActivity mainActivity;

    private View root;
    private boolean isLoading, isLastPage;
    private int currentPage=1, totalPage=2;
    private RealtimeBlurView realtimeBlurView;
    private ProgressBar progressBar;
    private BookmarkPresenter bookmarkPresenter;
    public LoveFragment(){
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mainActivity = (MainActivity) getActivity();
        mListRoom = new ArrayList<Room>();
        root = inflater.inflate(R.layout.fragment_love, container, false);
        assign();
        adapter = new LoveAdapter();
        bookmarkPresenter = new BookmarkPresenter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mainActivity);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            public void loadMoreItems() {
                isLoading = true;
                progressBar.setVisibility(View.VISIBLE);
                currentPage += 1;
                getListRoom();
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
//        bookmarkPresenter.("0b4oSVQ6aB6fpmvbkVvo",FirebaseAuth.getInstance().getCurrentUser().getUid());

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

    public void showStatus(String s) {
        Toast.makeText(mainActivity,s,Toast.LENGTH_SHORT).show();

    }
}