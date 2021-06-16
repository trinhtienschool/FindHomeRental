package com.trinhtien2212.findhomerental.dao;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.trinhtien2212.findhomerental.model.User;
import com.trinhtien2212.findhomerental.presenter.IUserResult;
import com.trinhtien2212.findhomerental.presenter.UserManagerPresenter;

import java.util.ArrayList;
import java.util.Map;

public class CheckAdmin extends ConnectDB {
    private static volatile CheckAdmin instance;

    public static synchronized CheckAdmin getInstance() {
        if (instance == null) {
            instance = new CheckAdmin();

        }
        return instance;
    }
    public void checkAdmin(String userUid, IUserResult userResult){
        DocumentReference docRef = db.collection("admin").document("KgdKfYaL6VfsaYvpCRug");
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
                        if(checkAdmin(userUid,document.getData())){
                            Log.e("CheckAdmin: ","Co");
                            userResult.returnUser(new ArrayList<>());
                        }else userResult.returnUser(null);

                    } else {
                        userResult.returnUser(null);
                        Log.d("ErrorCheckAdmin", "No such document");
//                        getRoomByListRoomIds.getRoom();
                    }
                } else {
                    Log.d("ErrorCheckAdmin", "get failed with ", task.getException());
                    userResult.returnUser(null);
                }
            }
        });
    }
    public boolean checkAdmin(String userUid, Map<String,Object>map){
        for(String key: map.keySet()){
            if(userUid.equals(map.get(key))){
                return true;
            }
        }
        return false;
    }
}
