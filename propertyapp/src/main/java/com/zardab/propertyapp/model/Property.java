package com.zardab.propertyapp.model;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;


import org.apache.commons.lang3.RandomStringUtils;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@AllArgsConstructor
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String propertyType;
    private BigDecimal propertyPrice;
    private Boolean isBooked = false;

    @Lob
    private Blob photo;

    //Entity Relationships
    @OneToMany(mappedBy="property", fetch = FetchType.LAZY, cascade = CascadeType.ALL) // This indicates that all operations performed on the parent entity (like Property) should be cascaded to the related BookedProperty entities.
    private List<BookedProperty> bookings;
    

    //NoArgsConstructor
    public Property() {
        this.bookings = new ArrayList<>();
        // When a property object is instansiated, we need to avoid null pointer exception
    }

    //Methods
    public void addBooking(BookedProperty booking) {
        if (bookings == null) {
            bookings = new ArrayList<>();
        }
        bookings.add(booking);
        booking.setProperty(this); //Adding a booking with this property in parameters
        isBooked = true;
        String bookingCode = RandomStringUtils.randomNumeric(10); //generate booking code and assign it as bookingconfirmationcode
        booking.setBookingConfirmationCode(bookingCode);
    }
}
