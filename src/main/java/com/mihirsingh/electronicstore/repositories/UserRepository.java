package com.mihirsingh.electronicstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mihirsingh.electronicstore.entities.User;

public interface UserRepository extends JpaRepository<User, String> {

    
} 
