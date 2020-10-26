package com.oskelly;

import com.oskelly.model.Comment;
import com.oskelly.model.Link;
import com.oskelly.model.Notification;
import java.util.ArrayList;
import java.util.List;

public class MakeResponse {

    public static List createResponseForComments(List<Comment> list, int page, int size) {
        Link link = new Link();
        String prev;
        String next;
        if(page != 0){
            prev = String.format("/comments?page=%d&size=%d", page-1, size);
            link.setPrev(prev);
        }
        next = String.format("/comments?page=%d&size=%d", page+1, size);
        link.setNext(next);
        List l = new ArrayList();
        l.add(list);
        l.add(link);
        return l;
    }

    public static List createResponseForNotifications(List<Notification> list, int page, int size) {
        System.out.println(list);
        Link link = new Link();
        String prev;
        String next;
        if(page != 0){
            prev = String.format("/notifications?page=%d&size=%d", page-1, size);
            link.setPrev(prev);
        }
        next = String.format("/notifications?page=%d&size=%d", page+1, size);
        link.setNext(next);
        List l = new ArrayList();
        l.add(list);
        l.add(link);
        return l;
    }

}
