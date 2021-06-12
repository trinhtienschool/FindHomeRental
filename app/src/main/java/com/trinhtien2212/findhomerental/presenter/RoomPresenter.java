package com.trinhtien2212.findhomerental.presenter;

import android.util.Log;

import com.trinhtien2212.findhomerental.dao.ConnectServer;
import com.trinhtien2212.findhomerental.dao.RoomDB;
import com.trinhtien2212.findhomerental.dao.SaveLocationBehavior;
import com.trinhtien2212.findhomerental.model.Location;
import com.trinhtien2212.findhomerental.model.Room;
import com.trinhtien2212.findhomerental.ui.add_room.AddRoomActivity;

public class RoomPresenter implements Presenter {
    AddRoomActivity addRoomActivity;
    ConnectServer connectServer;
    RoomDB roomDB;
    Room room;

    public RoomPresenter(AddRoomActivity addRoomActivity) {
        this.addRoomActivity = addRoomActivity;
        this.connectServer = new SaveLocationBehavior(this);
        this.roomDB = RoomDB.getInstance();


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

    public void deleteRoom(Room room) {

        //phai set isDeleted = true
        this.roomDB.deleteRoom(this.room.getRoomID(), this);
    }

    public void deleteLocation() {
        Location location = new Location(this.room.getAddress(), true, this.room.getRoomID());
        connectServer.connectServer(location, ConnectServer.DELETEROOM);
    }

    @Override
    public void onFail() {

    }

    @Override
    public void onSuccess() {
        addRoomActivity.notifyStatus("Lưu thành công");
    }
}
