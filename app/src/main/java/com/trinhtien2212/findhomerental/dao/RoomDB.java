package com.trinhtien2212.findhomerental.dao;

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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.trinhtien2212.findhomerental.model.Room;
import com.trinhtien2212.findhomerental.presenter.GetRoomByListRoomIds;
import com.trinhtien2212.findhomerental.presenter.RoomPresenter;
import com.trinhtien2212.findhomerental.ui.Util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void addRoom(Room room, RoomPresenter roomPresenter) {
        Log.e("Dang vo addRoom", "Dang vo addroom");

        this.db.collection("rooms").add(room.convertToMap())
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        String id = documentReference.getId();
                        roomPresenter.saveLocation(id);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //ToDo
                        Log.e("SAVEFAILE", "Save failse");
                    }
                });

    }
    public void getAllRoomOfUser(String userUid){
        List<Room>rooms = new ArrayList<Room>();
        db.collection("rooms")
                .whereEqualTo("userCreatedId", userUid)
//                .whereEqualTo("roomID", userUid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Room room = document.toObject(Room.class);
                                room.setUtilities(document);
                                rooms.add(room);
                                Log.e("Room",room.toString());

                                Log.d("Room", document.getId() + " => " + document.getData());
                            }
                            //ToDo
                            getImagesOfRoom(rooms,-1);
                        } else {
                            Log.d("Error", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
    public void getRandomRooms(){
        List<Room>rooms = new ArrayList<Room>();
        db.collection("rooms")
                .orderBy("cost")
//                .whereEqualTo("roomID", userUid)
                .limit(30)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Room room = document.toObject(Room.class);
                                room.setUtilities(document);
                                rooms.add(room);
                                Log.e("Room",room.toString());

                                Log.d("Room", document.getId() + " => " + document.getData());
                            }
                            //ToDo
                            getImagesOfRoom(rooms,-1);
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
                       Room room = document.toObject(Room.class);
                       room.setUtilities(document);
                       Log.e("Room: ",room.toString());
                       getRoomByListRoomIds.addRoom(room);
                    } else {
                        Log.d("Error", "No such document"+roomID);
                    }
                } else {
                    Log.d("Error", "get failed with ", task.getException());

                }
            }
        });
    }
    public void getImagesOfRoom(List<Room>rooms,int index){
        final int index2 = index+1;
        if(index2 == rooms.size()){
            //ToDo
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
                       getImagesOfRoom(rooms,index2);
                    } else {
                        Log.d("Error", "No such document");
                    }
                } else {
                    Log.d("Error", "get failed with ", task.getException());

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
                        Log.d("Error", "No such document");
                    }
                } else {
                    Log.d("Error", "get failed with ", task.getException());

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
                        roomPresenter.deleteLocation();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
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
