package com.trinhtien2212.findhomerental.presenter;

import android.util.Log;

import com.trinhtien2212.findhomerental.dao.RoomDB;
import com.trinhtien2212.findhomerental.model.Location;
import com.trinhtien2212.findhomerental.model.Room;

import java.util.ArrayList;
import java.util.List;

public class GetRoomByListRoomIds {
    List<String> roomIds;
    List<Room> rooms;
    RoomDB roomDB;
    RoomReturnResult roomReturnResult;
    int countGetRoom,numOfItems,nextLastItemIndex;
    List<Location> locations;
    public GetRoomByListRoomIds(List<String>roomIds){
        this.roomIds = roomIds;
        rooms = new ArrayList<Room>();
        roomDB = RoomDB.getInstance();
        countGetRoom = 0;
        numOfItems = 10;
        nextLastItemIndex = 10;
    }
    public GetRoomByListRoomIds(RoomReturnResult roomReturnResult, List<Location>locations){
        rooms = new ArrayList<Room>();
        roomDB = RoomDB.getInstance();
        countGetRoom = 0;
        numOfItems = 10;
        nextLastItemIndex = 0;
        this.roomReturnResult = roomReturnResult;
        this.locations = locations;
    }
    public GetRoomByListRoomIds(RoomReturnResult roomReturnResult){
        rooms = new ArrayList<Room>();
        roomDB = RoomDB.getInstance();
        countGetRoom = 0;
        numOfItems = 10;
        nextLastItemIndex = 0;
        this.roomReturnResult = roomReturnResult;
        this.locations = locations;
    }
    public boolean hasNext(){
        if(this.countGetRoom == roomIds.size())
             return true;
        else return false;
    }
    public void getNextRooms(){
        this.rooms = new ArrayList<Room>();
        nextLastItemIndex += numOfItems;
        getRoom();
    }
    public void getRoom(){
        Log.e("Size: ",roomIds.size()+"");
        if(countGetRoom == nextLastItemIndex || countGetRoom == roomIds.size()){
            roomReturnResult.continueAction(this.rooms);
            return;
        }
        roomDB.getRoom(roomIds.get(countGetRoom),this);
    }
    public void addRoom(Room room){
        if(locations !=null) room.setLocation(locations.get(countGetRoom));
        room.setRoomID(roomIds.get(countGetRoom));
        this.rooms.add(room);
        roomDB.getImages(room,this);
        Log.e("RoomClass",this.rooms.get(countGetRoom).toString());
        countGetRoom++;
    }

    public List<String> getRoomIds() {
        return roomIds;
    }

    public void setRoomIds(List<String> roomIds) {
        this.roomIds = roomIds;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public RoomDB getRoomDB() {
        return roomDB;
    }

    public void setRoomDB(RoomDB roomDB) {
        this.roomDB = roomDB;
    }

    public int getCountGetRoom() {
        return countGetRoom;
    }

    public void setCountGetRoom(int countGetRoom) {
        this.countGetRoom = countGetRoom;
    }

    public int getNumOfItems() {
        return numOfItems;
    }

    public void setNumOfItems(int numOfItems) {
        this.numOfItems = numOfItems;
    }

    public int getNextLastItemIndex() {
        return nextLastItemIndex;
    }

    public void setNextLastItemIndex(int nextLastItemIndex) {
        this.nextLastItemIndex = nextLastItemIndex;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }
}
