package com.zardab.propertyapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zardab.propertyapp.model.BookedProperty;

public interface BookedPropertyRepository extends JpaRepository<BookedProperty, Long> {

}
