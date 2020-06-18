package com.assessme.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;

/**
 * @author: monil
 * Created on: 2020-05-31
 */
public class BcryptPasswordEncoderUtil {

    private static final int encodingStrength = 10;

    public static String getbCryptPasswordFromPlainText(String plainTextPassword) {
        BCryptPasswordEncoder passwordEncoder =
                new BCryptPasswordEncoder(encodingStrength, new SecureRandom());
        return passwordEncoder.encode(plainTextPassword);
    }

    public static Boolean matchPassword(String plainTextPassword, String encodedPassword){
        BCryptPasswordEncoder passwordEncoder =
                new BCryptPasswordEncoder(encodingStrength, new SecureRandom());
        return passwordEncoder.matches(plainTextPassword, encodedPassword);
    }
}