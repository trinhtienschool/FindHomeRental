package com.trinhtien2212.mobilefindroomrental.presenter;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.trinhtien2212.mobilefindroomrental.dao.ConnectServer;
import com.trinhtien2212.mobilefindroomrental.dao.GetTotalRoomFilter;
import com.trinhtien2212.mobilefindroomrental.dao.GetTotalRoomSort;
import com.trinhtien2212.mobilefindroomrental.dao.RoomDB;
import com.trinhtien2212.mobilefindroomrental.dao.SaveLocationBehavior;
import com.trinhtien2212.mobilefindroomrental.model.Location;
import com.trinhtien2212.mobilefindroomrental.model.Room;
import com.trinhtien2212.mobilefindroomrental.ui.Util;
import com.trinhtien2212.mobilefindroomrental.ui.add_room.AddRoomActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
        this.room = new Room();
        this.connectServer = new SaveLocationBehavior(this);
        this.roomDB = RoomDB.getInstance();
    }
    public RoomPresenter(StatusResult statusResult) {
//        this.addRoomActivity = addRoomActivity;
        this.statusResult = statusResult;
        room = new Room();
        this.connectServer = new SaveLocationBehavior(this);
        this.roomDB = RoomDB.getInstance();
    }

    public RoomPresenter(RoomsResult roomsResult,StatusResult statusResult) {
//        this.addRoomActivity = addRoomActivity;
        this.statusResult = statusResult;
        this.roomsResult = roomsResult;
        this.room = new Room();
        this.connectServer = new SaveLocationBehavior(this);
        this.roomDB = RoomDB.getInstance();
    }
    public RoomPresenter(RoomsResult roomsResult,StatusResult statusResult,ITotalRoomResult totalRoomResult) {
//        this.addRoomActivity = addRoomActivity;
        this.totalRoomResult = totalRoomResult;
        this.room = new Room();
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
//    public void saveRoom() {
//        this.roomDB.addRoom(room, this);
//    }
public void setAddress(Map<String,Object> address){
    Log.e("Map",address.toString());
    String address_string = (String) address.get("address");
    Log.e("address_string",address_string);
    room.setAddress((String)address.get("address"));
    Log.e("Da qua address","Da qua address");
    room.setPhone((String)address.get("phone"));
    Log.e("Da qua phone","Da qua phone");

//        viewPager2.setCurrentItem(1);
}
    public void setInfo(Map<String,Object>info){
        room.setCost((int)info.get("costPerMonth"));
        room.setDeposit((int)info.get("dCost"));
        room.setEleCost((int)info.get("eCost"));
        room.setWatCost((int)info.get("wCost"));
        room.setArea((float)info.get("area"));
        room.setDescription((String)info.get("description"));
//        room.putAll(info);
        Log.e("Room",room.toString());
//        viewPager2.setCurrentItem(2);
    }
    public void saveRoom(Map<String,Object> utilities, boolean isUpdate){
        room.setUserCreatedId(FirebaseAuth.getInstance().getCurrentUser().getUid());
        room.setImages((List<String>) utilities.get("images"));

        List<String>utilitiesList = (List<String>) utilities.get("utilities");

        for(String utility : utilitiesList){
            switch (utility){
                case "Tivi":
                    room.setIsTivi(true);
                    break;
                case "Tủ đồ":
                    room.setIsWardrobe(true);
                    break;
                case "Wifi":
                    room.setIsWifi(true);
                    break;
                case "Tủ lạnh":
                    room.setIsFre(true);
                    break;
                case "Máy lạnh":
                    room.setIsAirCondition(true);
                    break;
                case "Gác lủng":
                    room.setIsAttic(true);
                    break;
                case "Máy nước nóng":
                    room.setIsHotWater(true);
                    break;
                case  "Nhà vệ sinh riêng":
                    room.setIsWC(true);
                    break;
                case "Tự do giờ giấc":
                    room.setFreeTime(true);
                    break;
                case "An ninh":
                    room.setIsFence(true);
                    break;
                case "Chỗ để xe riêng":
                    room.setIsPark(true );
                    break;
            }
        }
        room.setUserCreatedId(FirebaseAuth.getInstance().getCurrentUser().getUid());
        room.setUserDisplayName(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        room.setUserPhotoUrl(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString());
        if(isUpdate){
            Log.e("RoomId",(String)utilities.get("roomId"));
            this.room.setRoomID((String)utilities.get("roomId"));
            this.roomDB.updateRoom(room,this);
        }
        else this.roomDB.addRoom(room, this);
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
            Log.e("startDate",startDate.toString());
            Log.e("endDate",endDate.toString());
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
