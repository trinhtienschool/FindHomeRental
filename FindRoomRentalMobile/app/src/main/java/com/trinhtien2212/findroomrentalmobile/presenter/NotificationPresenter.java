package com.trinhtien2212.findroomrentalmobile.presenter;

import android.util.Log;

import com.trinhtien2212.findroomrentalmobile.MainActivity;
import com.trinhtien2212.findroomrentalmobile.dao.NotificationDB;
import com.trinhtien2212.findroomrentalmobile.model.Notification;

import java.util.ArrayList;
import java.util.List;

public class NotificationPresenter implements NotificationResult,StatusResult {
    NotificationDB notificationDB;
//    List<Notification>notifications;
    NotificationResult notificationResult;
    StatusResult statusResult;
    public NotificationPresenter(NotificationResult notificationResult) {
        this.notificationDB = NotificationDB.getInstance();
        this.notificationResult = notificationResult;
//        notifications = new ArrayList<Notification>();
    }
    public NotificationPresenter(StatusResult statusResult) {
        this.notificationDB = NotificationDB.getInstance();
        this.statusResult = statusResult;
//        notifications = new ArrayList<Notification>();
    }
    public void addNotification(Notification notification){
        notificationDB.addNot(notification,this);
    }
    public void getNotifications(String userUid){
        notificationDB.getAllNot(userUid,this);
    }



    @Override
    public void returnNotification(List<Notification> notifications) {

        notificationResult.returnNotification(notifications);
//        Log.e("Note",notifications.toString());
    }

    @Override
    public void onFail() {
        statusResult.onFail();
    }

    @Override
    public void onSuccess() {
        statusResult.onSuccess();
    }
}
