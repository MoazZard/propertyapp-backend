package com.zardab.propertyapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zardab.propertyapp.model.Property;

public interface PropertyRepository extends JpaRepository<Property, Long>{
    
}
