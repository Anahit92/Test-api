package com.oskelly.service;

import com.oskelly.model.Notification;

import java.util.List;
import java.util.UUID;

public interface NotificationService {
    List<Notification> getNotifications(int page, int size);

    Notification getNotificationByCommentId(UUID id);

    void addNotification(UUID comment_id);

    void updateNotification(UUID id, Boolean delivered);

    void deleteNotification(UUID comment_id);
}
