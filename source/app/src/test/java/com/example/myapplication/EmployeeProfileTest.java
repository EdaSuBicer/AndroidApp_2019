package com.example.myapplication;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EmployeeProfileTest {
    EmployeeProfile EmployeeProfileActivity = new EmployeeProfile();

    @Test
    public void clinicNameValidator_CorrectSimple() {
        assertTrue(EmployeeProfileActivity.validClinicName("Cool Clinic"));
    }
    @Test
    public void clinicNameValidator_InvalidDot() {
        assertFalse(EmployeeProfileActivity .validClinicName("clinicname."));
    }
    @Test
    public void clinicNameValidator_InvalidAllNumbers() {
        assertFalse(EmployeeProfileActivity .validClinicName("12345"));
    }
    @Test
    public void clinicNameValidator_InvalidCharacters() {
        assertFalse(EmployeeProfileActivity .validClinicName("%?&*"));
    }

    @Test
    public void clinicNameValidator_InvalidEmpty() {
        assertFalse(EmployeeProfileActivity .validClinicName(""));
    }
    @Test
    public void clinicNameValidator_InvalidNull() {
        assertFalse(EmployeeProfileActivity .validClinicName(null));
    }




    @Test
    public void insuranceTypeValidator_Null() {
        assertFalse(EmployeeProfileActivity .validInsuranceType(null));
    }
    @Test
    public void insuranceTypeValidator_Correct() {
        assertTrue(EmployeeProfileActivity .validInsuranceType("UHIP"));
    }
    @Test
    public void insuranceTypeValidator_Correct1() {
        assertTrue(EmployeeProfileActivity .validInsuranceType("OHIP"));
    }
    @Test
    public void insuranceTypeValidator_Correct2() {
        assertTrue(EmployeeProfileActivity .validInsuranceType("Private Insurence"));
    }
    @Test
    public void insuranceTypeValidator_InvalidEmpty() {
        assertFalse(EmployeeProfileActivity .validInsuranceType(""));
    }
    @Test
    public void insuranceTypeValidator_InvalidAllNumbers() {
        assertFalse(EmployeeProfileActivity .validInsuranceType ("12345"));
    }
    @Test
    public void insuranceTypeValidator_InvalidCharacters() {
        assertFalse(EmployeeProfileActivity .validInsuranceType("%&?*"));
    }



    @Test
    public void paymentMethodValidator_Correct() {
        assertTrue(EmployeeProfileActivity .validPaymentMethod("Cash"));
    }
    @Test
    public void paymentMethodValidator_Correct1() {
        assertTrue(EmployeeProfileActivity .validPaymentMethod("Debit"));
    }
    @Test
    public void paymentMethodValidator_Correct2() {
        assertTrue(EmployeeProfileActivity .validPaymentMethod("Card"));
    }
    @Test
    public void paymentMethodValidator_Null() {
        assertFalse(EmployeeProfileActivity .validPaymentMethod(null));
    }
    @Test
    public void paymentMethodValidator_Empty() {
        assertFalse(EmployeeProfileActivity .validPaymentMethod(""));
    }
    @Test
    public void paymentMethodValidator_InvalidAllNumbers() {
        assertFalse(EmployeeProfileActivity .validPaymentMethod("12345"));
    }
    @Test
    public void paymentMethodValidator_InvalidCharacters() {
        assertFalse(EmployeeProfileActivity .validPaymentMethod("%?&*"));
    }


    @Test
    public void phoneNumberValidator_Correct() {
        assertTrue(EmployeeProfileActivity .validPhoneNumber("1 (123) 456-7890" ));
    }
    @Test
    public void phoneNumberValidator_Null() {
        assertFalse(EmployeeProfileActivity .validPhoneNumber(null));
    }
    @Test
    public void phoneNumberValidator_Empty() {
        assertFalse(EmployeeProfileActivity .validPhoneNumber(""));
    }
    @Test
    public void phoneNumberValidator_InvalidNotAllNumbers() {
        assertFalse(EmployeeProfileActivity .validPhoneNumber("1sdf2"));
    }
    @Test
    public void phoneNumberValidator_InvalidCharacters() {
        assertFalse(EmployeeProfileActivity .validPhoneNumber("%?&*"));
    }



    @Test
    public void addressValidator_Correct() {
        assertTrue(EmployeeProfileActivity .validAddress("550 Cumberland St, Ottawa, ON K1N 6N8" ));
    }
    @Test
    public void addressValidator_Null() {
        assertTrue(EmployeeProfileActivity .validAddress(null));
    }
    @Test
    public void addressValidator_Empty() {
        assertTrue(EmployeeProfileActivity .validAddress(""));
    }
    @Test
    public void addressValidator_InvalidCharacters() {
        assertTrue(EmployeeProfileActivity .validAddress("%?&*"));
    }


}

