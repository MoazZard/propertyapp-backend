package com.zardab.propertyapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.zardab.propertyapp.exception.PhotoRetrievalException;
import com.zardab.propertyapp.model.BookedProperty;
import com.zardab.propertyapp.model.Property;
import com.zardab.propertyapp.repository.BookedPropertyRepository;
import com.zardab.propertyapp.response.BookingResponse;
import com.zardab.propertyapp.response.PropertyResponse;
import com.zardab.propertyapp.service.BookedPropertyService;
import com.zardab.propertyapp.service.IPropertyService;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Base64;

import javax.sql.rowset.serial.SerialException;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("/properties")
public class PropertyController { // these are all different end points in the backend

    private final IPropertyService propertyService; // RequiredArgsConstructor annotation for injecting the
                                                    // propertyservice dependency into class
    private final BookedPropertyService bookingService;

    // Adding into the database
    @PostMapping("/add/new-property")
    public ResponseEntity<PropertyResponse> addNewProperty(
            @RequestParam("photo") MultipartFile photo,
            @RequestParam("propertyType") String propertyType,
            @RequestParam("propertyPrice") BigDecimal propertyPrice) throws SerialException, SQLException, IOException {

        Property savedProperty = propertyService.addNewProperty(photo, propertyType, propertyPrice);
        // Response is what we want sent to the frontend (returned in this method)
        PropertyResponse response = new PropertyResponse(savedProperty.getId(), savedProperty.getPropertyType(),
                savedProperty.getPropertyPrice());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/all-property")
    @Transactional
    public ResponseEntity<List<PropertyResponse>> getAllProperties() throws SQLException {

        // GETS ALL PROPERTIES, AND FOR EACH PROPERTY IT CRAFTS THE PROPERTY RESPONSE
        // THEN PUTS IT INTO RESPONSES
        List<Property> allProperties = propertyService.getAllProperties();
        List<PropertyResponse> responses = new ArrayList<>();

        /* Retrieve booking history and photos of each property */
        for (Property property : allProperties) {
            byte[] photoBytes = propertyService.getPropertyPhotoById(property.getId()); // retrieve photo from every
                                                                                        // property in database

            if (photoBytes != null && photoBytes.length > 0) {
                String base64Photo = Base64.getEncoder().encodeToString(photoBytes); // turn bytes into base64

                PropertyResponse propertyResponse = getPropertyResponse(property); // booking history of each property
                propertyResponse.setPhoto(base64Photo);
                responses.add(propertyResponse);
            }
        }

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/property-types")
    public List<String> getAllPropertyTypes() {
        return propertyService.getAllPropertyTypes();
    }

    /* Helper functions */

    private PropertyResponse getPropertyResponse(Property property) {
        // List<BookedProperty> bookings =
        // bookingService.getAllBookingsByPropertyId(property.getId());
        /*
         * List<BookingResponse> bookingInfo = bookings // convert bookedProperty to
         * bookingResponse (A list of bookingresponses from corresponding bookings)
         * .stream()
         * .map(booking -> new BookingResponse(booking.getBookingId(),
         * booking.getCheckInDate(),
         * booking.getCheckOutDate(), booking.getBookingConfirmationCode()))
         * .toList(); // "booking" is one from bookings
         */

        // Convert into photoBytes from photoBlob

        byte[] photoBytes = null;
        Blob photoBlob = property.getPhoto();
        if (photoBlob != null) {
            try {
                photoBytes = photoBlob.getBytes(1, (int) photoBlob.length());
            } catch (SQLException e) {
                throw new PhotoRetrievalException("Error retrieving photo" + e.getMessage());
            }
        }

        return new PropertyResponse(property.getId(), property.getPropertyType(),
                property.getPropertyPrice(),
                property.getIsBooked(),
                photoBytes);
    }
}
