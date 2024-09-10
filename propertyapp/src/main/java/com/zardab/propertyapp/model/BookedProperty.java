package com.zardab.propertyapp.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookedProperty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    @Column(name = "check_In")
    private LocalDate checkInDate;

    @Column(name = "check_Out")
    private LocalDate checkOutDate;

    @Column(name = "guest_FullName")
    private String guestFullName;

    @Column(name = "guest_Email")
    private String guestEmail;

    @Column(name = "adults")
    private int NumOfAdults;

    @Column(name = "children")
    private int NumOfChildren;

    @Column(name = "total_Guests")
    private int totalNumOfGuests;

    @Column(name = "confirmation_Code")
    private String bookingConfirmationCode;




    
    // Entity Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_Id") // creates a table for itself. (join this table to its property)
    private Property property; 
    
    




    // Method  
    public void calculateTotalGuests() {
        this.totalNumOfGuests = this.NumOfAdults + this.NumOfChildren;
    }

    // Setters
    public void setNumOfAdults(int NumOfAdults) {
        this.NumOfAdults = NumOfAdults;
        calculateTotalGuests();
    }

    public void setNumOfChildren(int NumOfChildren) {
        this.NumOfChildren = NumOfChildren;
        calculateTotalGuests();
    }

    public void setBookingConfirmationCode(String bookingConfirmationCode) {
        this.bookingConfirmationCode = bookingConfirmationCode;
    }

}
