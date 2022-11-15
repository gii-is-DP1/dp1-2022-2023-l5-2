package org.springframework.samples.bossmonster.user;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends  CrudRepository<User, String>{

    List<User> findAll();

}
