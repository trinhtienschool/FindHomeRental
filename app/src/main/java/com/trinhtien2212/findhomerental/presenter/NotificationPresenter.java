package com.trinhtien2212.findhomerental.presenter;

import android.util.Log;

import com.trinhtien2212.findhomerental.dao.NotificationDB;
import com.trinhtien2212.findhomerental.model.Notification;

import java.util.ArrayList;
import java.util.List;

public class NotificationPresenter implements Presenter{
    NotificationDB notificationDB;
    List<Notification>notifications;
    public NotificationPresenter() {
        notificationDB = NotificationDB.getInstance();
        notifications = new ArrayList<Notification>();
    }
    public void addNotification(Notification notification){
        notificationDB.addNot(notification,this);
    }
    public void getNotifications(String userUid){
        notificationDB.getAllNot(userUid,this);
    }
    public void setNotForList(List<Notification>notifications){
        this.notifications = notifications;
        Log.e("Note",notifications.toString());
    }
    @Override
    public void onFail() {

    }

    @Override
    public void onSuccess() {

    }
}
