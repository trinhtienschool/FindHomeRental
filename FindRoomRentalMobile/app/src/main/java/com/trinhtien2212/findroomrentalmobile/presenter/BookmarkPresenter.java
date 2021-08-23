package com.trinhtien2212.findroomrentalmobile.presenter;

import android.util.Log;

import com.trinhtien2212.findroomrentalmobile.dao.BookmarkDB;
import com.trinhtien2212.findroomrentalmobile.model.Bookmark;
import com.trinhtien2212.findroomrentalmobile.model.Room;
import com.trinhtien2212.findroomrentalmobile.ui.love.LoveFragment;

import java.util.List;

public class BookmarkPresenter implements StatusResult,RoomsResult {
    private BookmarkDB bookmarkDB;
    private GetRoomByListRoomIds getRoomByListRoomIds;
    private StatusResult statusResult;
    private RoomsResult roomsResult;

    private Bookmark bookmark;
    public BookmarkPresenter(StatusResult statusResult,RoomsResult roomsResult){
        this.statusResult = statusResult;
        this.roomsResult = roomsResult;
        this.bookmark = new Bookmark();
        this.bookmarkDB = BookmarkDB.getInstance();
        getRoomByListRoomIds = new GetRoomByListRoomIds(this);
    }
    public void addBookmark(String roomId,String userUid){
        bookmark.addRoom(roomId);
        this.bookmarkDB.addBookmarks(roomId,userUid,this);
    }
    public void getAllBookmarks(String userUid){
        Log.e("getAllBookmarks","Dang vao");
        this.bookmarkDB.getAllBookmarks(bookmark,userUid,this);
    }

    public void removeRoom(String roomId, String userUid){
        String key = bookmark.removeRoom(roomId);
        if(key !=null){
            Log.e("Key: ",key);
            bookmarkDB.removeRoomOfBookmark(key,this,userUid);
        }else{
            statusResult.onFail();
        }
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
        statusResult.onFail();
    }

    @Override
    public void onSuccess() {
        statusResult.onSuccess();


        Log.e("SAVE BookMark","Thanh cong");
    }
    public int getTotalPage(){
        int totalItems = getRoomByListRoomIds.getTotalItems();
        if(totalItems%10 == 0) return totalItems/10;
        else return totalItems/10+1;
    }
    public int getTotalResults(){
        return getRoomByListRoomIds.getTotalItems();
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
