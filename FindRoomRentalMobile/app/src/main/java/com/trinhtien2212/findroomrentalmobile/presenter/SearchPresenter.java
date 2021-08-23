package com.trinhtien2212.findroomrentalmobile.presenter;

import android.util.Log;

import com.trinhtien2212.findroomrentalmobile.dao.SearchLocationBehavior;
import com.trinhtien2212.findroomrentalmobile.model.Location;
import com.trinhtien2212.findroomrentalmobile.model.Room;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SearchPresenter implements StatusResult, RoomsResult {
    List<Location> locations =new ArrayList<Location>();

    GetRoomByListRoomIds getRoomByListRoomIds;
    RoomsResult roomsResult;
    StatusResult statusResult;
    public SearchPresenter(RoomsResult roomsResult, StatusResult statusResult){
        this.roomsResult = roomsResult;
        this.statusResult = statusResult;
    }
    public void searchLocation(String address){
        locations = new ArrayList<Location>();
        SearchLocationBehavior searchLocationBehavior = new SearchLocationBehavior(this);
        searchLocationBehavior.connectServer(new Location(address),10);
        getRoomByListRoomIds = new GetRoomByListRoomIds(this);
    }
    public void parseJson(String json) throws JSONException {
        if(json.equalsIgnoreCase("{\"roomIDs\":[{}]}")){
           returnRooms(null);
            return;
        }

        JSONObject jsonObject = new JSONObject(json);
        JSONArray jsonArray = jsonObject.getJSONArray("roomIDs");
        for(int i=0;i<jsonArray.length();i++){
            JSONObject searchRoomResult = jsonArray.getJSONObject(i);
            Location location = new Location();
            location.setLocation(searchRoomResult);
            locations.add(location);
        }
        Log.e("locations size: ",locations.size()+"");

       setUpGetRoomByRoomIds(locations);
    }
    private void setUpGetRoomByRoomIds(List<Location>locations){
        getRoomByListRoomIds.setLocations(locations);
        getRoomByListRoomIds.setRoomIds(getRoomIds(locations));
        getRoomByListRoomIds.setCountGetRoom(0);
        getNext();
    }
    private List<String>getRoomIds(List<Location>locations){
        List<String>roomIds = new ArrayList<String>();
        for(Location l: locations){
            roomIds.add(l.getRoomID());
        }
        return roomIds;
    }
    public int getTotalPage(){
        int totalItems = getRoomByListRoomIds.getTotalItems();
        if(totalItems%10 == 0) return totalItems/10;
        else return totalItems/10+1;
    }
    public int getTotalResults(){
        return getRoomByListRoomIds.getTotalItems();
    }
    public boolean hasNext(){
       return getRoomByListRoomIds.hasNext();
    }
    public void getNext(){
        if(!hasNext()) {
            Log.e("HasNext","Dang vao !hasNext searchPresenter");
            roomsResult.returnRooms(null);
        }
        else this.getRoomByListRoomIds.getNextRooms();
    }
    //    public List<Room> next(){
//        nextLastItemIndex = countGetRoom + numOfItems;
//        getRoom();
//    }
//
//    public void getRoom(){
//        Log.e("Size: ",locations.size()+"");
//       if(countGetRoom == nextLastItemIndex) return;
//        roomDB.getRoom(locations.get(countGetRoom).getRoomID(),this);
//    }
//    public void addRoom(Room room){
//        room.setRoomID(locations.get(countGetRoom).getRoomID());
//        this.rooms.add(room);
//        roomDB.getImages(room,this);
//        Log.e("RoomClass",this.rooms.get(countGetRoom).toString());
//        countGetRoom++;
////        Log.e("Room",room.getRoomID());
////        getRoom();
//    }


    @Override
    public void onFail() {
        Log.e("OnFail","Dang vao onFail Search presenter");
        statusResult.onFail();
    }

    @Override
    public void onSuccess() {

    }

//    @Override
//    public void continueAction(List<Room> rooms) {
//        //ToDo
//        for(Room room: rooms){
//            Log.e("PrintRoom",room.toString());
//        }
//    }

    @Override
    public void returnRooms(List<Room> rooms) {
        roomsResult.returnRooms(rooms);
    }

   public void sortIncrease(){
       Collections.sort(locations, new Comparator<Location>() {
           @Override
           public int compare(Location o1, Location o2) {
               return (int)(o1.getDistance()*1000-o2.getDistance()*1000);
           }
       });
       Log.e("Lo after sort inc",locations.toString());
       setUpGetRoomByRoomIds(locations);
   }
    public void sortDecrease(){
        Collections.sort(locations, new Comparator<Location>() {
            @Override
            public int compare(Location o1, Location o2) {
                return (int)(o2.getDistance()*1000-o1.getDistance()*1000);
            }
        });
        Log.e("Lo after sort dec",locations.toString());
        setUpGetRoomByRoomIds(locations);
    }
}
