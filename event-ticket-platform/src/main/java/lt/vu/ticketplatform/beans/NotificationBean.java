package lt.vu.ticketplatform.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lt.vu.ticketplatform.dao.NotificationDAO;
import lt.vu.ticketplatform.entities.Notification;

import java.util.List;

@Named
@RequestScoped
public class NotificationBean {

    @Inject
    private NotificationDAO notificationDAO;

    public List<Notification> getNotifications() {
        return notificationDAO.findAll();
    }
}