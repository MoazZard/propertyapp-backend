package com.zardab.propertyapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.zardab.propertyapp.model.Property;

public interface PropertyRepository extends JpaRepository<Property, Long>{
    
    @Query("SELECT DISTINCT p.propertyType FROM Property p")
    List<String> findDistinctPropertyTypes();
}
