package com.oskelly.controller;

import com.oskelly.model.Comment;
import com.oskelly.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@Controller
public class CommentController {

    @Autowired
    CommentService commentService;

    @RequestMapping(method = RequestMethod.GET, value="/comments")
    @ResponseBody
    public List<Comment> getComments(@RequestParam("page") int page, @RequestParam("size") int size) {
        return commentService.getComments(page, size);
    }

    @RequestMapping(method = RequestMethod.GET, value="/comment")
    @ResponseBody
    public Comment getNotificationByCommentId(@RequestParam UUID id) {
        return commentService.getCommentById(id);
    }

    @RequestMapping(method = RequestMethod.POST, value="/comment", consumes = "application/json")
    @ResponseBody
    public Comment addComment(@RequestBody Comment comment) {
        commentService.addComment(comment);
        return comment;
    }

}
