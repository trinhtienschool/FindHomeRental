package com.trinhtien2212.mobilefindroomrental.dao;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.trinhtien2212.mobilefindroomrental.MainActivity;
import com.trinhtien2212.mobilefindroomrental.model.Room;
import com.trinhtien2212.mobilefindroomrental.model.User;
import com.trinhtien2212.mobilefindroomrental.presenter.GetRoomByListRoomIds;
import com.trinhtien2212.mobilefindroomrental.presenter.RoomPresenter;
import com.trinhtien2212.mobilefindroomrental.presenter.RoomsResult;
import com.trinhtien2212.mobilefindroomrental.ui.Util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RoomDB_Test extends ConnectDB {

    FirebaseStorage storage;
    StorageReference storageRef;
    List<String> imageInternalUri;
    List<String>imageStorageUrl;
    Random random = new Random();
    public RoomDB_Test() {
        this.storage = FirebaseStorage.getInstance();
        this.storageRef = storage.getReference();
    }


    private static volatile RoomDB_Test instance;

    public static synchronized RoomDB_Test getInstance() {
        if (instance == null) {
            instance = new RoomDB_Test();

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
    public void getAllRoomOfUser(String userUid,RoomPresenter roomPresenter){
        List<Room>rooms = new ArrayList<Room>();
        db.collection("rooms")
                .whereEqualTo("userCreatedId", userUid)
//                .whereEqualTo("roomID", userUid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if(!task.getResult().isEmpty()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Room room = new Room();
                                    room.setRoom(document);
                                    rooms.add(room);
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
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static Date between() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2021,0,1);
        Date start = calendar.getTime();

        calendar.set(2021,6,1);
        Date end = calendar.getTime();
        long startMillis = start.getTime();
        long endMillis = end.getTime();
        long randomMillisSinceEpoch = ThreadLocalRandom
                .current()
                .nextLong(startMillis, endMillis);

        return new Date(randomMillisSinceEpoch);
    }
    public void getRandomRooms(RoomsResult roomsResult){
        List<String>roomIds = new ArrayList<String>();
        db.collection("rooms")

                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Room room = document.toObject(Room.class);
//                                room.setUtilities(document);
                                roomIds.add(document.getId());
//                                Log.e("Room",roomIds.toString());

//                                Log.d("Room", document.getId() + " => " + document.getData());
                            }
                            //ToDo
                            List<User>user = new ArrayList<User>();
                            user.add(new User("Quang Tiến Trịnh","https://lh3.googleusercontent.com/a/AATXAJxOJxva-0nLh6nFY1Yj6roQ-8e7kvy9wG4KxVA3=s96-c","YteQEWwKOyZK6McOen4Q6IqEquj2"));
                            user.add(new User("Tiến Trịnh","https://lh3.googleusercontent.com/a/AATXAJyAP-1dDG2WUbKDB_BEqxJatMEu_chlS39p8Ae2=s96-c","gDDcWuWA9DgNtTuLrGEG7RVvFOi1"));
                            user.add(new User("Quang Tiến Trịnh","https://lh3.googleusercontent.com/a/AATXAJytLzWz1dr7uvFPbiLPsvrkx-r9ZQU4F_nQesdC=s96-c","h1PykH8RBNbaYi18K6jfnfaNV2x1"));
                            user.add(new User("Tâm Quyết","https://lh3.googleusercontent.com/a/AATXAJzRARmurWfjIsZrT6voenLlU5IF6m5Kuo9PpEr7=s96-c","iudgy5K9vFMS7RlgULYtDDPyAXf1"));
                            user.add(new User("Trịnh Quang Tiến","https://lh3.googleusercontent.com/a/AATXAJz_Bct5ZCfD6bJidIMH6eXqpqpXOSexZNqngECf=s96-c","npsT4ViIQeSGCqZ19gs8NMmCDQp2"));
                            user.add(new User("Quang Tiến Trịnh","https://lh3.googleusercontent.com/a/AATXAJzeH6VqIHq7TaX6Jswm29bDcyqbCPHeCL_1b1RW=s96-c","p1O3NUAmPpeGrlXkMtLmCpKjoSW2"));
                            user.add(new User("Nhuan DuongBa","https://lh3.googleusercontent.com/a/AATXAJwOn1EMYlD02jScEVeBLCwbEdla6y2VNY09Luw-=s96-c","4sqr2hpDw3UrHTEIJyN8llgEAv22"));
                            user.add(new User("Nhật Thy Trần","https://lh3.googleusercontent.com/a/AATXAJxkQHtGYlnkOCNeQvprSWluZOozgdcD4lUhiSG8=s96-c","KYUfmugBxgbEYAhrAvaEiz6OOlk2"));
                            user.add(new User("Đinh Thị Hằng","https://lh3.googleusercontent.com/a/AATXAJxdytKywG6qAmcYHX-quVmyPhIswCyKdnEtDUYr=s96-c","1iMxcgcReJdLzMkMecIORQrXj0g2"));

                            user.add(new User("Quỳnh Trần","https://lh3.googleusercontent.com/a/AATXAJyrk3_F5zwUIxvLt0KRa9_bGZD-P-hUWNkiFICKyg=s96-c","CWMs65Z5CEeQvn3Eh6E5rPtO6s53"));
                            user.add(new User("QUYNH TRAN TRUC","https://lh3.googleusercontent.com/a/AATXAJy1XKSlZNnX1UxZiC9DR-jIks2b-8xIDNmGOn2j=s96-c","GtDmZCR7IdO3HQUZksQsJh4rkeH2"));

                            user.add(new User("DuongBa Nhuan","https://lh3.googleusercontent.com/a/AATXAJxS6wLHV6Z0qbdagQdxUyvoMen-O13tToUyiReG=s96-c","JCYYUwBHXhOXFkmZ5fOVys69fUf1"));
                            user.add(new User("Tiến Trần Nhật","https://lh3.googleusercontent.com/a-/AOh14GgtGFq5tc4zhoc1x52XW1hIExq0DdbP2UzcE-zR=s96-c","NXdlRY2SMlWKajmcQdNPxQ7JcQJ3"));
                            user.add(new User("hang dinh", "https://lh3.googleusercontent.com/a/AATXAJw-Amx3oDtTLjh3Q_EVBXHO1kW10_fv63YyiH8i=s96-c","PBqLPTAVj3O4MGKwt3fcnerezmb2"));

                            user.add(new User( "nguyên Trần","https://lh3.googleusercontent.com/a/AATXAJyrXFsiZ9vdo6EJ_f5myCrT-86k8mIdb6w7VWgZ=s96-c","ahIbWSc1IPYdyCagoHJPhmKcvBf1"));
                            user.add(new User("Hằng MyTa IT","https://lh3.googleusercontent.com/a-/AOh14GiOI_s29qdhHYdwF04QGsgbo_zKm8m4i73deXeQ=s96-c","antiHmgTywU55fj81QnaeckmrBh1"));
                            user.add(new User("Nhật Thy Trần","https://lh3.googleusercontent.com/a-/AOh14GgK68OPMfSkdG0YXaojWBuD-q-QRjjhHdhDEFTu5Q=s96-c","bAWKUgETfNaAhdKDrxBI05wdvlD2"));
                            user.add(new User("Trần Nhật Thy","https://lh3.googleusercontent.com/a/AATXAJx0PgNekpS71XDCAVcnVs-pWqPxE5Jff8wSsrzZ=s96-c", "p86Hpcy18RcQLecZkNp55srUw943"));
                            user.add(new User("Hân Khả","https://lh3.googleusercontent.com/a/AATXAJwAddMvtbe_zqAU8YiklTkMPlULntPTKUtd_ntS=s96-c",  "ufMOxWqLNxV0Hu9iLv60lAz7QTW2"));


//                            getImagesOfRoom(rooms,-1,roomsResult);
                            int[] a = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
                            int a_user = roomIds.size()/user.size();
                            int last_user = roomIds.size()/user.size() + roomIds.size()%user.size();
                            Log.e("Tag","roomIDs: "+roomIds.size()+" users.size: "+user.size()+" a_user: "+a_user+" last_user: "+last_user);
                            update(roomIds,-1,user,a,a_user,last_user);
                        } else {
                            Log.d("Error", "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

//    Test
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void update(List<String>roomIds, int index, List<User>users,int[] currentRoomsOfAUser,int numOfRoomsOfAUser,int numOfRoomsOfLastUser){

        final int index2 = index+1;
        int user_index;
        while(true){
            user_index = new Random().nextInt(users.size());
            if(user_index == (users.size()-1)){
                if(currentRoomsOfAUser[user_index]<= numOfRoomsOfLastUser){
                    currentRoomsOfAUser[user_index]++;
                    break;
                }
            }else if(currentRoomsOfAUser[user_index]<=numOfRoomsOfAUser){
                currentRoomsOfAUser[user_index]++;
                break;
            }
        }
        User user = users.get(user_index);
        if(index2 == roomIds.size()) {
            Log.e("Xong","Da update xong");
            return;
        }
        Map<String,Object>map = new HashMap<String, Object>();
//        int cost = ThreadLocalRandom.current().nextInt(1000, 10000 + 1);
//
//        int deposit = (int)(cost*30/100);
//        cost = cost*1000;
//        deposit = deposit*1000;
        map.put("dateCreated",between());
//        map.put("cost",cost);
//        map.put("deposit",deposit);
        map.put("isDeleted",false);
        map.put("userPhotoUrl",user.getPhotoUrl());
        map.put("userCreatedId", user.getUserUid());
//        map.put("isAirCondition",new Random().nextBoolean());
        map.put("userDisplayName",user.getDisplayName());

        DocumentReference washingtonRef = db.collection("rooms").document(roomIds.get(index2));

// Set the "isCapital" field of the city 'DC'
        washingtonRef
                .update(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Succ", "DocumentSnapshot successfully updated!");
                        update(roomIds,index2,users,currentRoomsOfAUser,numOfRoomsOfAUser,numOfRoomsOfLastUser);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("ErrorUpdate", "Error updating document", e);
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
                        Log.d("Error", "No such document");
                        getImagesOfRoom(rooms,index2,roomsResult);
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
                        getRoomByListRoomIds.getRoom();
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
