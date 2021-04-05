package com.stackhawk.vuln.soap.repo;

import com.stackhawk.vuln.soap.bean.User;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User findUserByUsername(String username);
    List<User> findAllBy();
}