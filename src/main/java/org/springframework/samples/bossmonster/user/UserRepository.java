package org.springframework.samples.bossmonster.user;

import java.util.List;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, String>{
    
    Page<User> findAllPaged(Pageable pageable);

    List<User> findAll();

}
