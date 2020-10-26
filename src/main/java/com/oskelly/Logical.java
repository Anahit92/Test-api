package com.oskelly;

import com.oskelly.model.Comment;
import com.oskelly.service.CommentServiceImpl;
import com.oskelly.service.NotificationServiceImpl;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class Logical {

    @Autowired
    private NotificationServiceImpl notificationServiceImpl;

    @Autowired
    private CommentServiceImpl commentServiceImpl;

    @AfterReturning(value = "execution(* com.oskelly.controller.CommentController.addComment(..))", returning = "comment")
    public void afterReturningAddComment(JoinPoint joinPoint, Comment comment) {
	try {
            BusinessLogic.doSomeWorkOnCommentCreation();
	} catch (Exception e) {
            notificationServiceImpl.deleteNotification(comment.getId());
            System.out.println("notification deleted");
            commentServiceImpl.deleteComment(comment.getId());
            System.out.println("comment deleted");
            throw new RuntimeException();
        }
    }
}
