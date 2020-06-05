//package com.assessme.controller;
//
//import com.assessme.model.User;
//import com.assessme.service.UserService;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.RequestBuilder;
//
//import java.util.Optional;
//
///**
// * @author: monil
// * Created on: 2020-05-29
// */
//
//@SpringBootTest
//@WebMvcTest
//public class UserControllerTests {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private UserService userService;
//
//    @MockBean
//    private User user;
//
//
//    @Test
//    public void getUserFromEmailTest() throws Exception {
//
//        String email = "monil.panchal14@gmail.com";
//
//        //MockBean
//        Optional<User> mockUserBean = Optional.of(user);
//
//        Mockito.when(userService.getUserWithEmail(email)).thenReturn(mockUserBean);
//
//    }
//
//
//}
