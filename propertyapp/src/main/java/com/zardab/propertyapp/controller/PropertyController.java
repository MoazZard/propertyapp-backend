package com.zardab.propertyapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.zardab.propertyapp.model.Property;
import com.zardab.propertyapp.response.PropertyResponse;
import com.zardab.propertyapp.service.IPropertyService;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/properties")
public class PropertyController {

    private final IPropertyService propertyService; // RequiredArgsConstructor annotation for injecting the propertyservice dependency into class

    // Adding into the database
    @PostMapping("/add/new-property")
    public ResponseEntity<PropertyResponse> addNewProperty(
    @RequestParam("photo") MultipartFile photo,
    @RequestParam("propertyType") String propertyType,
    @RequestParam("propertyPrice") BigDecimal propertyPrice) throws SerialException, SQLException, IOException {

        Property savedProperty = propertyService.addNewProperty(photo, propertyType, propertyPrice);
        // Response is what we want sent to the frontend (returned in this method)
        PropertyResponse response = new PropertyResponse(savedProperty.getId(), savedProperty.getPropertyType(), savedProperty.getPropertyPrice());

        return ResponseEntity.ok(response);
    }
}
