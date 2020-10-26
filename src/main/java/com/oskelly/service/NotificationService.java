package com.oskelly.service;

import com.oskelly.model.Notification;
import java.util.List;
import java.util.UUID;

public interface NotificationService {
    public abstract List<Notification> getNotifications(int page, int size);
    public abstract Notification getNotificationByCommentId(UUID id);
    public abstract void addNotification(UUID comment_id);
    public abstract void updateNotification(UUID id, Boolean delivered);
    public abstract void deleteNotification(UUID comment_id);
}
