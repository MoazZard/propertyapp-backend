package com.zardab.propertyapp.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.io.IOException;
import java.util.List;


import javax.sql.rowset.serial.SerialException;

import org.springframework.web.multipart.MultipartFile;

import com.zardab.propertyapp.model.Property;

public interface IPropertyService {

    Property addNewProperty(MultipartFile photo, String propertyType, BigDecimal propertyPrice) throws SerialException, SQLException, IOException;

    void deleteProperty(Long propertyId);

    List<Property> getAllProperties();

    List<String> getAllPropertyTypes();

    byte[] getPropertyPhotoById(Long propertyId) throws SQLException;
}
