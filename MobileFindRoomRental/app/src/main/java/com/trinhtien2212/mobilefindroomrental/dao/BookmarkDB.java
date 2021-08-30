package com.trinhtien2212.mobilefindroomrental.dao;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.trinhtien2212.mobilefindroomrental.model.Bookmark;
import com.trinhtien2212.mobilefindroomrental.presenter.BookmarkPresenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookmarkDB extends ConnectDB{
    private static volatile BookmarkDB instance;

    public static synchronized BookmarkDB getInstance() {
        if (instance == null) {
            instance = new BookmarkDB();
        }
        return instance;
    }
    public void addBookmarks(String roomId,String userUid, BookmarkPresenter bookmarkPresenter) {
        Log.e("Dang vo addRoom", "Dang vo addroom");

        this.db.collection("bookmarks").document(userUid).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if(documentSnapshot.exists()){
                                Bookmark bookmark = new Bookmark();
//                                List<String> roomIds = bookmark.convertToRoomId(documentSnapshot.getData());
                                Map<String,Object> roomIds = documentSnapshot.getData();
                                Log.e("RoomIds",roomIds.size()+"");
                                String nextKey = getNextKey(roomIds,roomId);
                                if(nextKey !=null) {
                                    roomIds.put(nextKey + "", roomId);
                                    saveBookmark(roomIds, userUid, bookmarkPresenter);
                                }
                            }else{
                                Map<String,Object>roomIds = new HashMap<String, Object>();
                                roomIds.put(0+"",roomId);
                                saveBookmark(roomIds,userUid,bookmarkPresenter);
                            }
                        }
                    }
                });
    }
    private String getNextKey(Map<String,Object>roomIds,String roomId){
        int max = -1;
        for(String key: roomIds.keySet()){
            if(roomId.equalsIgnoreCase((String)roomIds.get(key))) return null;
            int key_num = Integer.parseInt(key);
            max = Math.max(max,key_num);
        }
        String keyReturn = String.valueOf(max+1);
        Log.e("Max",keyReturn);
        return keyReturn;
    }
    public void saveBookmark(Map<String,Object>bookmarks,String userUid,BookmarkPresenter bookmarkPresenter){
        this.db.collection("bookmarks").document(userUid).set(bookmarks).
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        bookmarkPresenter.onSuccess();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                bookmarkPresenter.onFail();
            }
        });
    }
    public void removeRoomOfBookmark(String key,BookmarkPresenter bookmarkPresenter,String userUid) {
        Map<String, Object> updates = new HashMap<>();
        updates.put(key, FieldValue.delete());
        Log.e("Map",key+":"+FieldValue.delete().toString());
        try {
            DocumentReference docRef = db.collection("bookmarks").document(userUid);
            docRef.update(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        bookmarkPresenter.onSuccess();
                    } else {
                        bookmarkPresenter.onFail();
                    }
                }
            });
        }catch (Exception e){
            bookmarkPresenter.onFail();
        }
    }
    public void getAllBookmarks(Bookmark bookmark,String userUid, BookmarkPresenter bookmarkPresenter){
        DocumentReference docRef = db.collection("bookmarks").document(userUid);
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

                        List<String> roomIds = bookmark.convertToRoomId(document.getData());
                        Log.e("RoomIDs",roomIds.toString());
                        bookmarkPresenter.setListRoomIds(roomIds);
                    } else {
                        Log.d("Error", "No such document");
                        bookmarkPresenter.returnRooms(null);
                    }
                } else {
                    Log.d("Error", "get failed with ", task.getException());
                    bookmarkPresenter.returnRooms(null);
                }
            }
        });
    }
}
