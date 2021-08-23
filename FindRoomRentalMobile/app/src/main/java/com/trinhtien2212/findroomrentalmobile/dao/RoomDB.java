package com.trinhtien2212.findroomrentalmobile.dao;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.trinhtien2212.findroomrentalmobile.model.Room;
import com.trinhtien2212.findroomrentalmobile.presenter.GetRoomByListRoomIds;
import com.trinhtien2212.findroomrentalmobile.presenter.RoomPresenter;
import com.trinhtien2212.findroomrentalmobile.ui.Util;
import com.trinhtien2212.findroomrentalmobile.presenter.RoomsResult;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RoomDB extends ConnectDB {

    FirebaseStorage storage;
    StorageReference storageRef;
    List<String> imageInternalUri;
    List<String>imageStorageUrl;

    public RoomDB() {
        this.storage = FirebaseStorage.getInstance();
        this.storageRef = storage.getReference();
    }


    private static volatile RoomDB instance;

    public static synchronized RoomDB getInstance() {
        if (instance == null) {
            instance = new RoomDB();

        }
        return instance;
    }
    //Sort by price and limit form start to end
    public void getRoomForSort(RoomsResult roomsResult,boolean isASC, int start,int end){
        List<Room>rooms = new ArrayList<Room>();

        Query query = this.db.collection("rooms").whereGreaterThan("cost",start).whereLessThanOrEqualTo("cost",end);

        if(isASC) query = query.orderBy("cost", Query.Direction.ASCENDING);
        else query = query.orderBy("cost", Query.Direction.DESCENDING);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if(!task.getResult().isEmpty()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Room room = new Room();
                            room.setRoom(document);
                            if(!room.isDeleted()) {
                                rooms.add(room);
                            }
                            Log.e("Room", room.toString());

                            Log.d("Room", document.getId() + " => " + document.getData());
                        }
                        //ToDo
                        getImagesOfRoom(rooms, -1, roomsResult);
                    }else{
                        roomsResult.returnRooms(null);
                    }
                } else {
                    roomsResult.returnRooms(null);
                    Log.d("Error", "Error getting documents: ", task.getException());
                }
            }
        });
    }

    //Filter by dateCreated
    public void filterByDateCreated(RoomsResult roomsResult, Date start, Date end){
        List<Room>rooms = new ArrayList<Room>();
        Query query = this.db.collection("rooms").whereGreaterThan("dateCreated",start).whereLessThanOrEqualTo("dateCreated",end);

        query = query.orderBy("dateCreated", Query.Direction.ASCENDING);


        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if(!task.getResult().isEmpty()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Room room = new Room();
                            room.setRoom(document);
                            if(!room.isDeleted()) {
                                rooms.add(room);
                            }
                            Log.e("Room", room.toString());

                            Log.d("Room", document.getId() + " => " + document.getData());
                        }
                        //ToDo
                        getImagesOfRoom(rooms, -1, roomsResult);
                    }else{
                        roomsResult.returnRooms(null);
                    }
                } else {
                    roomsResult.returnRooms(null);
                    Log.d("Error", "Error getting documents: ", task.getException());
                }
            }
        });
    }

    //getNotificationOfOneRoom


    public void addRoom(Room room, RoomPresenter roomPresenter) {
        Log.e("Dang vo addRoom", "Dang vo addroom");

        this.db.collection("rooms").add(room.convertToMap())
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        String id = documentReference.getId();
                        Log.e("roomID",id);
                        roomPresenter.saveLocation(id);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //ToDo
                        roomPresenter.onFail();
                        Log.e("SAVEFAILE", "Save failse");
                    }
                });

    }
    public void getRoomById(String roomId,RoomsResult roomsResult){
        List<Room>rooms = new ArrayList<>();
        DocumentReference docRef = db.collection("rooms").document(roomId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("GetARoom", "DocumentSnapshot data: " + document.getData());
                        Room room = new Room();
                        room.setRoom(document);

                        rooms.add(room);
                        getImagesOfRoom(rooms,-1,roomsResult);
                    } else {
                        Log.d("GetARoom", "No such document");
                        roomsResult.returnRooms(null);
                    }
                } else {
                    Log.d("GetARoom", "get failed with ", task.getException());
                    roomsResult.returnRooms(null);
                }
            }
        });
    }
    public void getAllRoomOfUser(String userUid,RoomPresenter roomPresenter){
        List<Room>rooms = new ArrayList<Room>();
        db.collection("rooms")
                .whereEqualTo("userCreatedId", userUid)

                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if(!task.getResult().isEmpty()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Room room = new Room();
                                    room.setRoom(document);
                                    if(!room.isDeleted()) {
                                        rooms.add(room);
                                    }
                                    Log.e("Room", room.toString());

                                    Log.d("Room", document.getId() + " => " + document.getData());
                                }
                                //ToDo
                                getImagesOfRoom(rooms, -1, roomPresenter);
                            }else{
                                roomPresenter.returnRooms(null);
                            }
                        } else {
                            roomPresenter.returnRooms(null);
                            Log.d("Error", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
    public void getRandomRooms(RoomsResult roomsResult){
        List<Room>rooms = new ArrayList<Room>();
        db.collection("rooms")
//
//                .orderBy("cost")
                .limit(70)

                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Random random = new Random();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Room room = new Room();
                                room.setRoom(document);
                                if(!room.isDeleted()) {
                                    if(random.nextBoolean()) {
                                        rooms.add(room);
                                    }
                                    if(rooms.size()==20){
                                        getImagesOfRoom(rooms,-1,roomsResult);
                                        return;
                                    }
                                }
                                Log.e("Room",room.toString());
                                Log.d("Room", document.getId() + " => " + document.getData());
                            }
                            //ToDo
                            getImagesOfRoom(rooms,-1,roomsResult);
                        } else {
                            Log.d("Error", "Error getting documents: ", task.getException());
                        }
                    }
                });

    }
    public void getRoom(String roomID, GetRoomByListRoomIds getRoomByListRoomIds){
        DocumentReference docRef = db.collection("rooms").document(roomID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.e("field",document.get("area")+"");
                       Room room = new Room();
                       room.setRoom(document);
                       Log.e("Room: ",room.toString());
                       getRoomByListRoomIds.addRoom(room);
                    } else {
                        Log.d("Error", "No such document"+roomID);
                        getRoomByListRoomIds.addRoom(null);
                    }
                } else {
                    Log.d("Error", "get failed with ", task.getException());
                    getRoomByListRoomIds.addRoom(null);
                }
            }
        });
    }
    public void getImagesOfRoom(List<Room>rooms, int index,RoomsResult roomsResult){
        final int index2 = index+1;
        if(index2 == rooms.size()){
            //ToDo
            roomsResult.returnRooms(rooms);
            return;
        }
        Room room = rooms.get(index2);
        DocumentReference docRef = db.collection("images").document(room.getRoomID());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
//                        Map<String,Object>data = document.getData();
//                        Log.e("map: ",data.toString());
//                        List<String>images = new ArrayList<String>();
//                        for(String key: data.keySet()){
//                            images.add((String) data.get(key));
//                        }
                        room.setImagesMap(document.getData());
                       getImagesOfRoom(rooms,index2, roomsResult);
                    } else {
                        room.setImages(null);
                        Log.d("Error", "No such document getImageOfRooms" +room.getRoomID());
                        getImagesOfRoom(rooms,index2,roomsResult);
                    }
                } else {
                    Log.d("Error", "get failed with "+ task.getException().toString()+";"+room.getRoomID());
                    room.setImages(null);
                    getImagesOfRoom(rooms,index2,roomsResult);
                }
            }
        });
    }
    public void getImages(Room room, GetRoomByListRoomIds getRoomByListRoomIds){
        DocumentReference docRef = db.collection("images").document(room.getRoomID());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
//                        Map<String,Object>data = document.getData();
//                        Log.e("map: ",data.toString());
//                        List<String>images = new ArrayList<String>();
//                        for(String key: data.keySet()){
//                            images.add((String) data.get(key));
//                        }
                        room.setImagesMap(document.getData());
                        getRoomByListRoomIds.getRoom();
                    } else {
                        room.setImages(null);
                        Log.d("Error", "No such document getImages"+room.getRoomID());
                        getRoomByListRoomIds.getRoom();
                    }
                } else {
                    Log.d("Error", "get failed with ", task.getException());
                    room.setImages(null);
                    Log.d("Error", "No such document "+room.getRoomID());
                    getRoomByListRoomIds.getRoom();
                }
            }
        });
    }
    public void updateRoom(Room room, RoomPresenter roomPresenter) {
        Log.e("Dang vo addRoom", "Dang vo addroom");

        this.db.collection("rooms").document(room.getRoomID())
                .update(room.convertToMap())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        roomPresenter.updateLocation();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //ToDo
                Log.e("UpdateRoom fail","fail");
                roomPresenter.onFail();
            }
        });

    }
    public void deleteRoom(String roomID, RoomPresenter roomPresenter) {
        Map<String,Object>map = new HashMap<String,Object>();
        map.put("isDeleted",true);
        this.db.collection("rooms").document(roomID)
                .update(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e("RoomDeleted","Da deleted");
                        roomPresenter.deleteLocation();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                roomPresenter.onFail();
                //ToDo
            }
        });
    }

    public void addImage(Room room, RoomPresenter roomPresenter) {

        this.db.collection("images").document(room.getRoomID()).set(room.convertImagesListToMap()).
                addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        roomPresenter.onSuccess();
                        Log.e("Hoan thanh","HOAN THANH");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        roomPresenter.onFail();
                        //ToDo
                        Log.e("errorAddLoc", e.getMessage());
                    }
                });
    }
    private void uploadAnImage(Room room, RoomPresenter roomPresenter,int index){
        final int index2 = index+1;
        if(index2 == imageInternalUri.size()){
            room.setImages(imageStorageUrl);
            addImage(room,roomPresenter);
            return;
        }
        String linkImage = imageInternalUri.get(index2);
        if(linkImage.startsWith("http")){
            imageStorageUrl.add(linkImage);
            uploadAnImage(room,roomPresenter,index2);
            return;
        }
        Uri file = Uri.fromFile(new File(imageInternalUri.get(index2)));
        StorageReference riversRef = storageRef.child("images/" + file.getLastPathSegment());


        Bitmap bMap = BitmapFactory.decodeFile(imageInternalUri.get(index2));
        bMap = Util.getResizedBitmap(bMap,512,"");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bMap.compress(Bitmap.CompressFormat.JPEG, 72, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = riversRef.putBytes(data);

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return riversRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    imageStorageUrl.add(downloadUri.toString());
                    Log.e("Path", downloadUri.toString());

                    uploadAnImage(room,roomPresenter,index2);
                } else {
                    // Handle failures
                    // ...
                }
            }
        });
    }
    public void uploadImage(Room room, RoomPresenter roomPresenter) {
        imageInternalUri = room.getImages();
       imageStorageUrl = new ArrayList<String>();
       uploadAnImage(room,roomPresenter,-1);
        Log.e("Upload","Dang cho save image");


    }


}
