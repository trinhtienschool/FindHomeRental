package com.trinhtien2212.mobilefindroomrental.presenter;

import com.trinhtien2212.mobilefindroomrental.model.Notification;

import java.util.List;

public interface NotificationResult {
    void returnNotification(List<Notification>notifications);
}
