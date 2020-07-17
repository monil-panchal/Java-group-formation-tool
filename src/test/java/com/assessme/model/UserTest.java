package com.assessme.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserTest {

    private final Logger logger = LoggerFactory.getLogger(UserTest.class);
    @Mock
    Course course;

    @Test
    public void DefaultUserConstructorTest() {
        User user = new User();

        Assertions.assertNull(user.getBannerId());
        Assertions.assertNull(user.getFirstName());
        Assertions.assertNull(user.getLastName());
        Assertions.assertNull(user.getEmail());
        Assertions.assertNull(user.getPassword());
        Assertions.assertNull(user.getActive());
    }

    @Test
    public void ParameterisedUserConstructorTest() {
        String bannerID = "B00881122";
        String firstName = "firstName";
        String lastName = "lastName";
        String email = "email@dal.ca";
        String password = "password";
        Boolean isActive = true;

        User user = new User(bannerID, firstName, lastName, email, password, isActive);

        Assertions.assertEquals(user.getBannerId(), bannerID);
        Assertions.assertEquals(user.getLastName(), lastName);
        Assertions.assertEquals(user.getFirstName(), firstName);
        Assertions.assertEquals(user.getEmail(), email);
        Assertions.assertEquals(user.getPassword(), password);
        Assertions.assertTrue(user.getActive());
    }

    @Test
    public void setUserIdTest() {
        Long userId = 1L;
        User user = new User();
        Assertions.assertNull(user.getUserId());

        user.setUserId(userId);
        Assertions.assertNotNull(user.getUserId());
        Assertions.assertEquals(user.getUserId(), userId);
    }

    @Test
    public void getUserIdTest() {
        Long userId = 1L;
        User user = new User();
        Assertions.assertNull(user.getUserId());

        user.setUserId(userId);
        Assertions.assertNotNull(user.getUserId());
        Assertions.assertEquals(user.getUserId(), userId);
    }

    @Test
    public void setBannerIdTest() {
        String bannerID = "B00881122";
        User user = new User();
        Assertions.assertNull(user.getBannerId());

        user.setBannerId(bannerID);
        Assertions.assertNotNull(user.getBannerId());
        Assertions.assertEquals(user.getBannerId(), bannerID);
    }

    @Test
    public void getBannerIdTest() {
        String bannerID = "B00881122";
        User user = new User();
        Assertions.assertNull(user.getBannerId());

        user.setBannerId(bannerID);
        Assertions.assertNotNull(user.getBannerId());
        Assertions.assertEquals(user.getBannerId(), bannerID);
    }

    @Test
    public void setFirstNameTest() {
        String firstName = "firstName";
        User user = new User();
        Assertions.assertNull(user.getFirstName());

        user.setFirstName(firstName);
        Assertions.assertNotNull(user.getFirstName());
        Assertions.assertEquals(user.getFirstName(), firstName);
    }

    @Test
    public void getFirstNameTest() {
        String firstName = "firstName";
        User user = new User();
        Assertions.assertNull(user.getFirstName());

        user.setFirstName(firstName);
        Assertions.assertNotNull(user.getFirstName());
        Assertions.assertEquals(user.getFirstName(), firstName);
    }

    @Test
    public void setLastNameTest() {
        String lastName = "lastName";
        User user = new User();
        Assertions.assertNull(user.getLastName());

        user.setLastName(lastName);
        Assertions.assertNotNull(user.getLastName());
        Assertions.assertEquals(user.getLastName(), lastName);
    }

    @Test
    public void getLastNameTest() {
        String lastName = "lastName";
        User user = new User();
        Assertions.assertNull(user.getLastName());

        user.setLastName(lastName);
        Assertions.assertNotNull(user.getLastName());
        Assertions.assertEquals(user.getLastName(), lastName);
    }

    @Test
    public void setEmailTest() {
        String email = "email@dal.ca";
        User user = new User();
        Assertions.assertNull(user.getEmail());

        user.setEmail(email);
        Assertions.assertNotNull(user.getEmail());
        Assertions.assertEquals(user.getEmail(), email);
    }

    @Test
    public void getEmailTest() {
        String email = "email@dal.ca";
        User user = new User();
        Assertions.assertNull(user.getEmail());

        user.setEmail(email);
        Assertions.assertNotNull(user.getEmail());
        Assertions.assertEquals(user.getEmail(), email);
    }

    @Test
    public void setPasswordTest() {
        String password = "password";
        User user = new User();
        Assertions.assertNull(user.getPassword());

        user.setPassword(password);
        Assertions.assertNotNull(user.getPassword());
        Assertions.assertEquals(user.getPassword(), password);
    }

    @Test
    public void getPasswordTest() {
        String password = "password";
        User user = new User();
        Assertions.assertNull(user.getPassword());

        user.setPassword(password);
        Assertions.assertNotNull(user.getPassword());
        Assertions.assertEquals(user.getPassword(), password);
    }

    @Test
    public void setActiveTest() {
        Boolean isActive = true;
        User user = new User();
        Assertions.assertNull(user.getActive());

        user.setActive(isActive);
        Assertions.assertNotNull(user.getActive());
        Assertions.assertEquals(user.getActive(), isActive);
    }

    @Test
    public void getActiveTest() {
        Boolean isActive = true;
        User user = new User();
        Assertions.assertNull(user.getActive());

        user.setActive(isActive);
        Assertions.assertNotNull(user.getActive());
        Assertions.assertEquals(user.getActive(), isActive);
    }

    @Test
    public void equalsTest() {
        User user = new User();
        User anotherUser = new User("B00881122", "firstName", "lastName", "email@dal.ca",
            "password", true);
        User anotherSameUser = new User("B00881122", "firstName", "lastName", "email@dal.ca",
            "password", true);
        anotherSameUser.setUserId(1L);
        anotherUser.setUserId(1L);

        Assertions.assertTrue(user.equals(user));
        Assertions.assertFalse(user.equals(course));
        Assertions.assertTrue(anotherSameUser.equals(anotherUser));
    }
}
