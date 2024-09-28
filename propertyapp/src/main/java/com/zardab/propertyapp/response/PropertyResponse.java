package com.zardab.propertyapp.response;

import java.math.BigDecimal;
import java.util.List;
import java.util.Base64;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data //VERY IMPORTANT for an object of this class to be serialized into JSON format
@NoArgsConstructor
public class PropertyResponse {
    private Long id;
    private String propertyType;
    private BigDecimal propertyPrice;
    private Boolean isBooked = false;
    private String photo; //String of bytes from database

    private List<BookingResponse> bookings;// we want to return the bookedpropertyresponse with the propertyresponse

    // Constructors
    public PropertyResponse(Long id, String propertyType, BigDecimal propertyPrice) {
        this.id = id;
        this.propertyType = propertyType;
        this.propertyPrice = propertyPrice;
    }

    public PropertyResponse(Long id, String propertyType, BigDecimal propertyPrice, boolean isBooked,
                            byte[] photoBytes) {

        this.id = id;
        this.propertyType = propertyType;
        this.propertyPrice = propertyPrice;
        this.isBooked = isBooked;
        // if photoBytes contains string from database, we convert it into base64 photo to be displayed in the frontend
        this.photo = photoBytes != null ? Base64.getEncoder().encodeToString(photoBytes) : null; 
        //this.bookings = bookings List<BookingResponse> bookings;
    }
}
