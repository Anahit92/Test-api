package com.oskelly.controller;

import com.oskelly.model.Notification;
import com.oskelly.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@Controller
public class NotificationController {
    @Autowired
    NotificationService notificationService;

    @RequestMapping(method = RequestMethod.GET, value="/notifications")
    @ResponseBody
    public List<Notification> getNotifications(@RequestParam("page") int page, @RequestParam("size") int size) {
        return notificationService.getNotifications(page, size);
    }

    @RequestMapping(method = RequestMethod.GET, value="/notification")
    @ResponseBody
    public Notification getNotificationByCommentId(@RequestParam UUID comment_id) {
        return notificationService.getNotificationByCommentId(comment_id);
    }

}
