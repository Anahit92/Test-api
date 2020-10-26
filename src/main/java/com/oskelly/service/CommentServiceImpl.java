package com.oskelly.service;

import com.oskelly.DataAccess;
import com.oskelly.MakeResponse;
import com.oskelly.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CommentServiceImpl implements CommentService {

    private final Connection con = new DataAccess().getConnection();

    @Autowired
    private NotificationServiceImpl notificationServiceImpl;

    public List<Comment> getComments(int page, int size) {
        List<Comment> list = new ArrayList();
        try (Statement statement = con.createStatement();
             ResultSet resultSet = statement.
                    executeQuery(String.format("SELECT * FROM public.comment limit %d offset %d", size, page*size))) {
                while (resultSet.next()) {
                    Comment comment = new Comment(
                            (UUID) resultSet.getObject("id"),
                            resultSet.getString("comment"),
                            resultSet.getTimestamp("time"));
                    list.add(comment);
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return MakeResponse.createResponseForComments(list, page, size);
    }

    @Override
    public Comment getCommentById(UUID id) {
        Comment comment = new Comment();
        try (Statement statement = con.createStatement();
             ResultSet resultSet = statement.executeQuery(String.format(
                     "SELECT * FROM public.comment where id = '%s'", id))) {
            while (resultSet.next()) {
                comment.setId((UUID) resultSet.getObject("id"));
                comment.setComment(resultSet.getString("comment"));
                comment.setTime(resultSet.getTimestamp("time"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comment;
    }

    @Override
    public void addComment(Comment comment) {
        String query = "insert into public.comment values (?, ?, ?)";
        UUID id = comment.getId();
        if (comment.getId() == null)
            id = UUID.randomUUID();

        Timestamp time = new Timestamp(System.currentTimeMillis());
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setObject(1, comment.getId());
            preparedStatement.setString(2, comment.getComment());
            preparedStatement.setTimestamp(3, time);
            preparedStatement.execute();
            System.out.println("comment added with this id - " + id);
            notificationServiceImpl.addNotification(comment.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteComment(UUID id) {
        try (Statement st = con.createStatement()) {
            st.executeUpdate(String.format("delete from public.comment where id = '%s'", id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}