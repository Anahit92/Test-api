package com.oskelly.service;

import com.oskelly.BusinessLogic;
import com.oskelly.DataAccess;
import com.oskelly.MakeResponse;
import com.oskelly.model.Notification;
import org.springframework.stereotype.Service;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class NotificationServiceImpl implements NotificationService {

    private Connection con = new DataAccess().getConnection();

    @Override
    public List<Notification> getNotifications(int page, int size) {
        List<Notification> list = new ArrayList();
        try (Statement statement = con.createStatement();
             ResultSet resultSet = statement.executeQuery(
                    String.format("SELECT * FROM public.notification limit %d offset %d", size, page*size))) {
                while (resultSet.next()) {
                    Notification notification = new Notification(
                            (UUID) resultSet.getObject("id"),
                            (UUID) resultSet.getObject("comment_id"),
                            resultSet.getTimestamp("time"),
                            resultSet.getBoolean("delivered"));
                    list.add(notification);
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return MakeResponse.createResponseForNotifications(list, page, size);
    }

    @Override
    public Notification getNotificationByCommentId(UUID comment_id) {
        Notification notification = new Notification();
        try (Statement statement = con.createStatement();
             ResultSet resultSet = statement.executeQuery(String.format(
                    "SELECT * FROM public.notification where comment_id = '%s'", comment_id))) {
                while (resultSet.next()) {
                    notification.setId((UUID) resultSet.getObject("id"));
                    notification.setComment_id((UUID) resultSet.getObject("comment_id"));
                    notification.setTime(resultSet.getTimestamp("time"));
                    notification.setDelivered(resultSet.getBoolean("delivered"));
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notification;
    }

    @Override
    public void addNotification(UUID comment_id) {
        String query = "insert into public.notification values (?, ?, ?, ?)";
        UUID id = UUID.randomUUID();
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setObject(1, id);
            preparedStatement.setObject(2, comment_id);
            preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setBoolean(4, false);
            preparedStatement.execute();
            System.out.println("notification added with this id - "+ id);
            updateNotification(id, true);
            try {
                BusinessLogic.doSomeWorkOnNotification();
            }catch (Exception e) {
                System.out.println("this notification should be updated. id - "+id);
                updateNotification(id, false);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateNotification(UUID id, Boolean delivered) {
        try (Statement st = con.createStatement()){
            st.executeUpdate(String.format("update public.notification set delivered = %b where id = '%s'",delivered, id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteNotification(UUID comment_id) {
        try (Statement st = con.createStatement();){
            st.executeUpdate(String.format("delete from public.notification where comment_id = '%s'", comment_id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
