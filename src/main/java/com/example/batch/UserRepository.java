package com.example.batch;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by alokkulkarni on 30/10/16.
 */
public interface UserRepository extends JpaRepository<UserDetails, Long> {


}
