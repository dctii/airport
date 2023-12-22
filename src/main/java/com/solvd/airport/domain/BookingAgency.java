package com.solvd.airport.domain;

import com.solvd.airport.util.StringFormatters;

public class BookingAgency {
    private int bookingAgencyId;
    private String agencyName;
    private int addressId;
    private int contactInfoId;

    public BookingAgency() {
    }

    public BookingAgency(int bookingAgencyId, String agencyName, int addressId, int contactInfoId) {
        this.bookingAgencyId = bookingAgencyId;
        this.agencyName = agencyName;
        this.addressId = addressId;
        this.contactInfoId = contactInfoId;
    }

    public int getBookingAgencyId() {
        return bookingAgencyId;
    }

    public void setBookingAgencyId(int bookingAgencyId) {
        this.bookingAgencyId = bookingAgencyId;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getContactInfoId() {
        return contactInfoId;
    }

    public void setContactInfoId(int contactInfoId) {
        this.contactInfoId = contactInfoId;
    }

    @Override
    public String toString() {
        Class<?> currClass = this.getClass();
        String[] fieldNames = {
                "bookingAgencyId",
                "agencyName",
                "addressId",
                "contactInfoId"
        };

        String fieldsString = StringFormatters.buildFieldsString(this, fieldNames);
        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}


/*
CREATE TABLE
    booking_agencies (
        booking_agency_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
        agency_name VARCHAR(45) NOT NULL,
        address_id INT UNSIGNED NOT NULL,
        contact_info_id INT UNSIGNED NOT NULL,
        PRIMARY KEY(booking_agency_id)
    );
    ALTER TABLE booking_agencies
    ADD
        FOREIGN KEY(address_id) references addresses(address_id),
    ADD
        FOREIGN KEY(contact_info_id) references contact_info(contact_info_id);
*/
