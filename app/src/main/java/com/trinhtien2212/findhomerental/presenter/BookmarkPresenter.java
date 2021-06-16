package com.trinhtien2212.findhomerental.presenter;

import android.util.Log;

import com.trinhtien2212.findhomerental.dao.BookmarkDB;
import com.trinhtien2212.findhomerental.model.Bookmark;
import com.trinhtien2212.findhomerental.model.Room;
import com.trinhtien2212.findhomerental.ui.love.LoveFragment;

import java.util.List;

public class BookmarkPresenter implements StatusResult,RoomsResult {
    private BookmarkDB bookmarkDB;
    private GetRoomByListRoomIds getRoomByListRoomIds;
    private LoveFragment loveFragment;
    private Bookmark bookmark;
    public BookmarkPresenter(LoveFragment loveFragment){
        this.loveFragment = loveFragment;
        this.bookmark = new Bookmark();
        this.bookmarkDB = BookmarkDB.getInstance();
        getRoomByListRoomIds = new GetRoomByListRoomIds(this);
    }
    public void addBookmark(String roomId,String userUid){
        this.bookmarkDB.addBookmarks(roomId,userUid,this);
    }
    public void getAllBookmarks(String userUid){
        Log.e("getAllBookmarks","Dang vao");
        this.bookmarkDB.getAllBookmarks(bookmark,userUid,this);
    }

    public void removeRoom(String roomId, String userUid){
        String key = bookmark.getKey(roomId);
        if(key !=null){
            Log.e("Key: ",key);
            bookmarkDB.removeRoomOfBookmark(key,this,userUid);
        }else{
            loveFragment.showStatus("Lỗi: Không tồn tại phòng muốn xóa");
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
        loveFragment.showStatus("Thất bại");
    }

    @Override
    public void onSuccess() {
        loveFragment.showStatus("Thành công");


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
        loveFragment.returnRooms(rooms);
    }
}
