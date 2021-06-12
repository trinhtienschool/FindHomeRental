package com.trinhtien2212.findhomerental.dao;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.trinhtien2212.findhomerental.model.Notification;
import com.trinhtien2212.findhomerental.presenter.NotificationPresenter;

import java.util.ArrayList;
import java.util.List;

public class NotificationDB extends ConnectDB {
    private static volatile NotificationDB instance;

    public static synchronized NotificationDB getInstance() {
        if (instance == null) {
            instance = new NotificationDB();
        }
        return instance;
    }

    public void addNot(Notification noti, NotificationPresenter notificationPresenter) {
        Log.e("Dang vo addRoom", "Dang vo addroom");

        this.db.collection("notifications").document(noti.getUserUid()).collection("notList")
                .add(noti.convertToMap())
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.e("Note","Thanh cong") ;
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("Note","That bai");
            }
        });

    }
    public void getAllNot(String userUid,NotificationPresenter notificationPresenter){
        this.db.collection("notifications").document(userUid).collection("notList").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<Notification>notifications = new ArrayList<Notification>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("GetAllNot", document.getId() + " => " + document.getData());
                                Notification notification = new Notification();
                                notification.setNotification(document.getData());
                                notifications.add(notification);
                                Log.e("noti: ",notification.toString());
                            }
                            notificationPresenter.setNotForList(notifications);
                        } else {
                            Log.d("GetAllNot", "Error getting documents: ", task.getException());
                        }
                    }
                });

    }
}
