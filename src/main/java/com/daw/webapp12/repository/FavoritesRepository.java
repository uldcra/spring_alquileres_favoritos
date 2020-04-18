package com.daw.webapp12.repository;

import com.daw.webapp12.entity.Users;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FavoritesRepository extends JpaRepository<Users, Long> {
   
}
