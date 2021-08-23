package com.trinhtien2212.findroomrentalmobile.dao;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.trinhtien2212.findroomrentalmobile.presenter.AdminUserPresenter;
import com.trinhtien2212.findroomrentalmobile.presenter.IUserResult;
import com.trinhtien2212.findroomrentalmobile.presenter.StatusResult;
import com.trinhtien2212.findroomrentalmobile.presenter.UserManagerPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserDB extends ConnectDB {
    private static volatile UserDB instance;

    public static synchronized UserDB getInstance() {
        if (instance == null) {
            instance = new UserDB();

        }
        return instance;
    }
    public void updateUserAdmin(Map<String,Object>userAdmins, StatusResult statusResult){
        DocumentReference docRef = db.collection("admin").document("KgdKfYaL6VfsaYvpCRug");

// Set the "isCapital" field of the city 'DC'
        docRef
                .update(userAdmins)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
//                        Log.d(TAG, "DocumentSnapshot successfully updated!");

                        statusResult.onSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
//                        Log.w(TAG, "Error updating document", e);
                        statusResult.onFail();
                    }
                });
    }
    public void getAllUserAdminIds(AdminUserPresenter adminUserPresenter){

        DocumentReference docRef = db.collection("admin").document("KgdKfYaL6VfsaYvpCRug");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        adminUserPresenter.returnUserAdminList(document);
                    } else {
                        adminUserPresenter.returnUserAdminList(null);
                        Log.d("ErrorCheckAdmin", "No such document");
//                        getRoomByListRoomIds.getRoom();
                    }
                } else {
                    Log.d("ErrorCheckAdmin", "get failed with ", task.getException());
                    adminUserPresenter.returnUserAdminList(null);
                }
            }
        });
    }

}
