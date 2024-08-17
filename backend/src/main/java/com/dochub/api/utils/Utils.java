package com.dochub.api.utils;

import com.dochub.api.exceptions.InputStreamFileReadException;
import com.dochub.api.exceptions.InvalidTokenFormatException;
import com.dochub.api.exceptions.MultipartFileReadException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Objects;

public class Utils {
    public static String encodePassword (String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return passwordEncoder.encode(password);
    }

    public static byte[] readBytesFromMultipartFile (MultipartFile multipartFile) {
        try {
            return multipartFile.getBytes();
        } catch (Exception e) {
            throw new MultipartFileReadException();
        }
    }

    public static byte[] readBytesFromResource (ClassPathResource classPathResource) {
        try {
            return classPathResource.getInputStream().readAllBytes();
        } catch (Exception e) {
            throw new InputStreamFileReadException();
        }
    }

    public static String removeBearerPrefix(String token) {
        if (Objects.nonNull(token) && token.startsWith("Bearer ")) {
            return token.substring(7);
        }

        throw new InvalidTokenFormatException();
    }
}