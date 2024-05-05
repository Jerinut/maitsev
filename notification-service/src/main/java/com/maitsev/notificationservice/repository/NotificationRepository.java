package com.maitsev.notificationservice.repository;

import com.maitsev.notificationservice.model.Notification;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface NotificationRepository extends CrudRepository<Notification,String> {


    List<Notification> findByUserId(String userId);
}
