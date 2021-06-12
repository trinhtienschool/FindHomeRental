package com.trinhtien2212.findhomerental.dao;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.trinhtien2212.findhomerental.model.Bookmark;
import com.trinhtien2212.findhomerental.presenter.BookmarkPresenter;

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
                                roomIds.put(roomIds.size()+"",roomId);
//                                roomIds.add(roomId);
                                saveBookmark(roomIds,userUid,bookmarkPresenter);
                            }else{
                                Map<String,Object>roomIds = new HashMap<String, Object>();
                                roomIds.put(0+"",roomId);
                                saveBookmark(roomIds,userUid,bookmarkPresenter);
                            }
                        }
                    }
                });
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
    public void getAllBookmarks(String userUid, BookmarkPresenter bookmarkPresenter){
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
                        Bookmark bookmark = new Bookmark();
                        List<String> roomIds = bookmark.convertToRoomId(document.getData());
                        Log.e("RoomIDs",roomIds.toString());
                        bookmarkPresenter.setListRoomIds(roomIds);
                    } else {
                        Log.d("Error", "No such document");
                    }
                } else {
                    Log.d("Error", "get failed with ", task.getException());

                }
            }
        });
    }
}
