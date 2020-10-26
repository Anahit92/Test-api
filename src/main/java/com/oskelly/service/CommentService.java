package com.oskelly.service;

import com.oskelly.model.Comment;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public interface CommentService {
    public abstract List<Comment> getComments(int page, int size);
    public abstract Comment getCommentById(UUID id);
    public abstract void addComment(Comment comment);
}
