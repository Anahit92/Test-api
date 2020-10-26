package com.oskelly.service;

import com.oskelly.model.Comment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface CommentService {
    List<Comment> getComments(int page, int size);

    Comment getCommentById(UUID id);

    void addComment(Comment comment);
}
