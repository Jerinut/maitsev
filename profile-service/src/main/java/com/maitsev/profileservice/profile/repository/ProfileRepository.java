package com.maitsev.profileservice.profile.repository;

import com.maitsev.profileservice.profile.model.Profile;
import org.springframework.data.repository.CrudRepository;

public interface ProfileRepository extends CrudRepository<Profile,String> {

}
