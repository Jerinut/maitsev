package com.maitsev.notificationservice.repository;

import com.maitsev.notificationservice.model.Notification;
import org.springframework.data.repository.CrudRepository;

public interface NotificationRepository extends CrudRepository<Notification,String> {

}
