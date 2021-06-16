package com.trinhtien2212.findhomerental.presenter;

import com.trinhtien2212.findhomerental.model.Notification;

import java.util.List;

public interface NotificationResult {
    void returnNotification(List<Notification>notifications);
}
