package com.trinhtien2212.findhomerental.presenter;

import android.util.Log;

import com.trinhtien2212.findhomerental.dao.SearchLocationBehavior;
import com.trinhtien2212.findhomerental.model.Location;
import com.trinhtien2212.findhomerental.model.Room;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchPresenter implements Presenter, RoomReturnResult {
    List<Location> locations =new ArrayList<Location>();
    GetRoomByListRoomIds getRoomByListRoomIds;
    public void searchLocation(String address){
        SearchLocationBehavior searchLocationBehavior = new SearchLocationBehavior(this);
        searchLocationBehavior.connectServer(new Location(address),10);
        getRoomByListRoomIds = new GetRoomByListRoomIds(this);
    }
    public void parseJson(String json) throws JSONException {
        if(json.equalsIgnoreCase("{\"roomIDs\":[{}]}"))
            return;
        JSONObject jsonObject = new JSONObject(json);
        JSONArray jsonArray = jsonObject.getJSONArray("roomIDs");
        for(int i=0;i<jsonArray.length();i++){
            JSONObject searchRoomResult = jsonArray.getJSONObject(i);
            Location location = new Location();
            location.setLocation(searchRoomResult);
            locations.add(location);
        }
       List<String>roomIds = new ArrayList<String>();
        for(Location l: locations){
            roomIds.add(l.getRoomID());
        }
        getRoomByListRoomIds.setLocations(locations);
        getRoomByListRoomIds.setRoomIds(roomIds);
        getNext();
    }
    public boolean hasNext(){
       return getRoomByListRoomIds.hasNext();
    }
    public void getNext(){
        this.getRoomByListRoomIds.getNextRooms();
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

    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void continueAction(List<Room> rooms) {
        //ToDo
        for(Room room: rooms){
            Log.e("PrintRoom",room.toString());
        }
    }
}
