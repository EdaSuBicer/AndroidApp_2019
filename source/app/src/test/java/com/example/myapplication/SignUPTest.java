package com.example.myapplication;

import org.junit.Test;
import static org.junit.Assert.*;

public class SignUPTest {
    SignUP signUPActivity = new SignUP();

    @Test
    public void emailValidator_CorrectSimple() {
        assertTrue(signUPActivity .validEmail("name@email.com"));
    }
    @Test
    public void emailValidator_InvalidNoId() {
        assertFalse(signUPActivity .validEmail("name@email"));
    }
    @Test
    public void emailValidator_InvalidDoubleDot() {
        assertFalse(signUPActivity .validEmail("name@email..com"));
    }
    @Test
    public void emailValidator_InvalidNoUsername() {
        assertFalse(signUPActivity .validEmail("@email.com"));
    }
    @Test
    public void emailValidator_InvalidEmpty() {
        assertFalse(signUPActivity .validEmail(""));
    }
    @Test
    public void emailValidator_InvalidNull() {
        assertFalse(signUPActivity .validEmail(null));
    }

    @Test
    public void ageValidator_Null() {
        assertFalse(signUPActivity .validAge(null));
    }
    @Test
    public void ageValidator_Correct() {
        assertTrue(signUPActivity .validAge("20"));
    }
    @Test
    public void ageValidator_Empty() {
        assertFalse(signUPActivity .validAge(""));
    }
    @Test
    public void ageValidator_Negative() {
        assertFalse(signUPActivity .validAge("-20"));
    }
    @Test
    public void ageValidator_NotAllNumbers() {
        assertFalse(signUPActivity .validAge("1asd"));
    }
    @Test
    public void ageValidator_Zero() {
        assertFalse(signUPActivity .validAge("0"));
    }


    @Test
    public void nameValidator_Correct() {
        assertTrue(signUPActivity .validName("doga"));
    }
    @Test
    public void nameValidator_NotAllLetters() {
        assertFalse(signUPActivity .validName("dog1a"));
    }
    @Test
    public void nameValidator_Empty() {
        assertFalse(signUPActivity .validName(""));
    }
    @Test
    public void nameValidator_Null() {
        assertFalse(signUPActivity .validName(null));
    }


}


