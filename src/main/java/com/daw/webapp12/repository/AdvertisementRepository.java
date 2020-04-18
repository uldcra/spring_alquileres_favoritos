package com.daw.webapp12.repository;

import java.util.List;

import com.daw.webapp12.entity.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
    List<Advertisement> findByLocation(String string);

    @Query(value = "SELECT * FROM Advertisement WHERE square_meters >= ?1 AND type = ?2 AND bathrooms >= ?3 AND location = ?4 AND price <=?5 AND rooms >= ?6 LIMIT ?7",nativeQuery = true)
    List<Advertisement> findPreferences(int square_meters, String type, int bathrooms, String location, int price, int rooms, int limit);

    
}