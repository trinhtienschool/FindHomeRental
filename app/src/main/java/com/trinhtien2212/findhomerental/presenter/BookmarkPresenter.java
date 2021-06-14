package com.trinhtien2212.findhomerental.presenter;

import android.util.Log;

import com.trinhtien2212.findhomerental.dao.BookmarkDB;
import com.trinhtien2212.findhomerental.model.Location;
import com.trinhtien2212.findhomerental.model.Room;

import java.util.List;

public class BookmarkPresenter implements Presenter, RoomsResult {
    private BookmarkDB bookmarkDB;
    private GetRoomByListRoomIds getRoomByListRoomIds;
    private RoomsResult roomsResult;
    public BookmarkPresenter(RoomsResult roomsResult){
        this.roomsResult = roomsResult;
        this.bookmarkDB = BookmarkDB.getInstance();
        getRoomByListRoomIds = new GetRoomByListRoomIds(this);
    }
    public void addBookmark(String roomId,String userUid){
        this.bookmarkDB.addBookmarks(roomId,userUid,this);
    }
    public void getAllBookmarks(String userUid){
        this.bookmarkDB.getAllBookmarks(userUid,this);
    }
    public void setListRoomIds(List<String>listRoomIds){
        getRoomByListRoomIds.setRoomIds(listRoomIds);
        getNext();
    }
    public boolean hasNext(){
        return getRoomByListRoomIds.hasNext();
    }
    public void getNext(){
        this.getRoomByListRoomIds.getNextRooms();
    }
    @Override
    public void onFail() {

    }

    @Override
    public void onSuccess() {
        Log.e("SAVE BookMark","Thanh cong");
    }


    @Override
    public void returnRooms(List<Room> rooms) {
//        Log.e()
        //ToDo
//        for(Room room: rooms){
//            Log.e("PrintRoom",room.toString());
//        }
        roomsResult.returnRooms(rooms);
    }
}
