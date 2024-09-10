package com.zardab.propertyapp.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.zardab.propertyapp.model.Property;
import com.zardab.propertyapp.repository.PropertyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PropertyService implements IPropertyService {
    
    private final PropertyRepository propertyRepository; // ioc (inversion of control framework) injected into ioc container

    @Override // Signature of this method should also throw these same exceptions (in interface)
    public Property addNewProperty(MultipartFile file, String propertyType, BigDecimal propertyPrice) throws SerialException, SQLException, IOException {
        Property property = new Property();
        property.setPropertyPrice(propertyPrice);
        property.setPropertyType(propertyType);

        if (!file.isEmpty()) { 
            byte[] photoBytes = file.getBytes();
            Blob photoBlob = new SerialBlob(photoBytes); //convert bytes to blob
            property.setPhoto(photoBlob);
        }

        return propertyRepository.save(property);
    }
 
}
