package com.dochub.api.utils;

import com.dochub.api.dtos.user_roles.UserRoleResponseDTO;
import com.dochub.api.exceptions.InputStreamFileReadException;
import com.dochub.api.exceptions.InvalidTokenFormatException;
import com.dochub.api.exceptions.MultipartFileReadException;
import com.dochub.api.exceptions.PermissionDeniedException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.regex.Pattern;

public class Utils {
    private static final Pattern SPECIAL_CHARACTERS_PATTERN = Pattern.compile("[^a-zA-Z0-9]");
    private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\s");

    public static String encodePassword (final String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return passwordEncoder.encode(password);
    }

    public static byte[] readBytesFromMultipartFile (final MultipartFile multipartFile) {
        try {
            return multipartFile.getBytes();
        } catch (Exception e) {
            throw new MultipartFileReadException();
        }
    }

    public static byte[] readBytesFromResource (final ClassPathResource classPathResource) {
        try {
            return classPathResource.getInputStream().readAllBytes();
        } catch (Exception e) {
            throw new InputStreamFileReadException();
        }
    }

    public static String removeBearerPrefix (final String token) {
        if (Objects.nonNull(token) && token.startsWith("Bearer ")) {
            return token.substring(7);
        }

        throw new InvalidTokenFormatException();
    }

    public static Boolean containsSpecialCharacters (final String input) {
        if (input == null) {
            return false;
        }
        return SPECIAL_CHARACTERS_PATTERN.matcher(input).find();
    }

    public static Boolean containsWhitespace (final String input) {
        if (input == null) {
            return false;
        }
        return WHITESPACE_PATTERN.matcher(input).find();
    }

    public static Boolean containsSpacesOrSpecialCharacters (final String input) {
        return containsWhitespace(input) || containsSpecialCharacters(input);
    }

    public static void updateFieldIfPresent(final String fieldValue, final Consumer<String> updateAction) {
        if (Objects.nonNull(fieldValue) && !fieldValue.isBlank()) {
            updateAction.accept(fieldValue);
        }
    }

    public static void checkSystemPermission (final UserRoleResponseDTO userRoles, final String permission) {
        final boolean hasPermission = userRoles
            .roles()
            .stream()
            .anyMatch(role ->
                Objects.equals(Constants.ACTIVE, role.status()) &&
                role.systemPermissions()
                    .stream()
                    .anyMatch(sp -> Objects.equals(sp.description(), permission))
            );

        if (!hasPermission) {
            throw new PermissionDeniedException(String.format(Constants.PERMISSION_DENIED_EXCEPTION_MESSAGE, permission));
        }
    }
}