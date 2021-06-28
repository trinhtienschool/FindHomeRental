package com.trinhtien2212.findhomerental.presenter;

import android.util.Log;

import com.trinhtien2212.findhomerental.dao.ConnectServer;
import com.trinhtien2212.findhomerental.dao.GetTotalRoomFilter;
import com.trinhtien2212.findhomerental.dao.GetTotalRoomSort;
import com.trinhtien2212.findhomerental.dao.RoomDB;
import com.trinhtien2212.findhomerental.dao.SaveLocationBehavior;
import com.trinhtien2212.findhomerental.model.Location;
import com.trinhtien2212.findhomerental.model.Room;
import com.trinhtien2212.findhomerental.ui.Util;
import com.trinhtien2212.findhomerental.ui.add_room.AddRoomActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RoomPresenter implements StatusResult, RoomsResult {
    ConnectServer connectServer;
    RoomDB roomDB;
    Room room;
    RoomsResult roomsResult;
    StatusResult statusResult;
    ITotalRoomResult totalRoomResult;
    private int startPrice=1000000, endPrice=1200000;
    private int rangePrice = 100000;
    //Khoang nho, phan trang
    private Date startDate;
    private Date endDate;
    private int day_range = 5;
    //khoang lon
    private Date dateStart;
    private Date dateEnd;
    private boolean isLoading = false;
    private ConnectServer getTotalRoomFilter;
    private ConnectServer getTotalRoomSort;
    private boolean isASC;
    public RoomPresenter(RoomsResult roomsResult) {
//        this.addRoomActivity = addRoomActivity;
        this.roomsResult = roomsResult;
        this.connectServer = new SaveLocationBehavior(this);
        this.roomDB = RoomDB.getInstance();
    }
    public RoomPresenter(StatusResult statusResult) {
//        this.addRoomActivity = addRoomActivity;
        this.statusResult = statusResult;

        this.connectServer = new SaveLocationBehavior(this);
        this.roomDB = RoomDB.getInstance();
    }

    public RoomPresenter(RoomsResult roomsResult,StatusResult statusResult) {
//        this.addRoomActivity = addRoomActivity;
        this.statusResult = statusResult;
        this.roomsResult = roomsResult;
        this.connectServer = new SaveLocationBehavior(this);
        this.roomDB = RoomDB.getInstance();
    }
    public RoomPresenter(RoomsResult roomsResult,StatusResult statusResult,ITotalRoomResult totalRoomResult) {
//        this.addRoomActivity = addRoomActivity;
        this.totalRoomResult = totalRoomResult;
        this.statusResult = statusResult;
        this.roomsResult = roomsResult;
        this.connectServer = new SaveLocationBehavior(this);
        this.getTotalRoomFilter  = new GetTotalRoomFilter(this);
        this.getTotalRoomSort  = new GetTotalRoomSort(this);
        this.roomDB = RoomDB.getInstance();
    }
    public RoomPresenter(StatusResult statusResult,Room room) {
//        this.addRoomActivity = addRoomActivity;
        this.roomsResult = roomsResult;
        this.statusResult = statusResult;
        this.room = room;
        this.connectServer = new SaveLocationBehavior(this);
        this.roomDB = RoomDB.getInstance();
    }
    public void getARoomByRoomId(String roomId){
        roomDB.getRoomById(roomId,this);
    }
    public void getAllRoomsOfUser(String uid){
        roomDB.getAllRoomOfUser(uid,this);
    }
    public void getRandomRooms(){
        roomDB.getRandomRooms(this);
    }
    public void setRoom(Room room){
        this.room = room;
    }
    public void saveRoom() {
        this.roomDB.addRoom(room, this);
    }

    public void saveLocation(String roomID) {
        this.room.setRoomID(roomID);
        Log.e("ROOMID", roomID);
        Location location = new Location(this.room.getAddress(), false, roomID);
        connectServer.connectServer(location, ConnectServer.ADDROOM);

    }

    public void saveImage() {
//          room.setImages(imagesStorageUri);
        roomDB.uploadImage(room, this);
    }

    public void updateRoom() {

        this.roomDB.updateRoom(room, this);
    }

    public void updateLocation() {
        Location location = new Location(this.room.getAddress(), false, this.room.getRoomID());
        connectServer.connectServer(location, ConnectServer.UPDATEROOM);
    }

    public void deleteRoom() {

        //phai set isDeleted = true
        this.roomDB.deleteRoom(this.room.getRoomID(), this);
    }

    public void deleteLocation() {
        Location location = new Location(this.room.getAddress(), true, this.room.getRoomID());
        connectServer.connectServer(location, ConnectServer.DELETEROOM);
    }

    public RoomsResult getRoomsResult() {
        return roomsResult;
    }

    public void setRoomsResult(RoomsResult roomsResult) {
        this.roomsResult = roomsResult;
    }

    public StatusResult getStatusResult() {
        return statusResult;
    }

    public void setStatusResult(StatusResult statusResult) {
        this.statusResult = statusResult;
    }

    public void sortRoom(boolean isASC){
        getTotalRoomSort.connectServer(null,0);
        if(!isASC) {
            startPrice = 10000000;
            endPrice = startPrice - rangePrice;
            roomDB.getRoomForSort(this,isASC,endPrice,startPrice);
        }else{
            startPrice = 1000000;
            endPrice = startPrice + rangePrice;
            roomDB.getRoomForSort(this,isASC,startPrice,endPrice);
        }
        this.isASC =isASC;
        startPrice = endPrice;
    }
    public void getNextSortRoom(){
        if(!isASC) {

            endPrice = startPrice - rangePrice;
            roomDB.getRoomForSort(this,isASC,endPrice,startPrice);
        }else{

            endPrice = startPrice + rangePrice;
            roomDB.getRoomForSort(this,isASC,startPrice,endPrice);
        }
        startPrice = endPrice;
    }

    public void filterRoom(int month_start,int month_end){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-month_end);
        Date start = calendar.getTime();
        calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-month_start);
        Date end = calendar.getTime();

        dateStart = start;
        dateEnd = end;
        String[] dates = {Util.formatDateConnectServer(dateStart),Util.formatDateConnectServer(dateEnd)};
        getTotalRoomFilter.connectServer(dates,0);

        startDate = dateStart;
        calendar.setTime(startDate);
        calendar.add(Calendar.DAY_OF_MONTH,5);
        endDate = calendar.getTime();
        if(endDate.getTime()<=dateEnd.getTime()) {
            Log.e("dateStart", start.toString());
            Log.e("dateEnd", end.toString());
            roomDB.filterByDateCreated(this, startDate, endDate);
            startDate = endDate;
        }else{
            returnRooms(null);
        }


    }
    public void getRoomOfAUser(String userId){
        roomDB.getAllRoomOfUser(userId,this);
    }
    public void getNextFilterRoom(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DAY_OF_MONTH,5);
        endDate = calendar.getTime();
        if(endDate.getTime()<=dateEnd.getTime()){
            roomDB.filterByDateCreated(this,startDate,endDate);
            startDate = endDate;
        }else{
            Log.e("GETFilter","End");
            returnRooms(null);
        }
    }
    @Override
    public void returnRooms(List<Room> rooms) {
//        for(Room room: rooms){
//            Log.e("Room",room.toString());
//        }
        roomsResult.returnRooms(rooms);

    }


    @Override
    public void onFail() {
        statusResult.onFail();
    }

    @Override
    public void onSuccess() {
        Log.e("Thanh cong","Xóa thành công");
        statusResult.onSuccess();
    }

    public void returnTotalRoom(String total) {
        totalRoomResult.returnTotalRoom(total);
    }
}
