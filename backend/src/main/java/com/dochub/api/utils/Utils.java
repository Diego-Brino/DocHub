package com.dochub.api.utils;

import com.dochub.api.dtos.user_roles.UserRoleResponseDTO;
import com.dochub.api.enums.RoleStatus;
import com.dochub.api.exceptions.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.regex.Pattern;

public class Utils {
    private static final Pattern SPECIAL_CHARACTERS_PATTERN = Pattern.compile("[^a-zA-Z0-9]");
    private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\s");

    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%&*()_+-=[]|,./?><";
    private static final String ALL_CHARACTERS = UPPERCASE + LOWERCASE + DIGITS + SPECIAL_CHARACTERS;
    private static final int PASSWORD_LENGTH = 12;

    public static String encodePassword (final String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return passwordEncoder.encode(password);
    }

    public static String generateRandomPassword () {
        final SecureRandom random = new SecureRandom();
        final StringBuilder password = new StringBuilder(PASSWORD_LENGTH);

        password.append(UPPERCASE.charAt(random.nextInt(UPPERCASE.length())));
        password.append(LOWERCASE.charAt(random.nextInt(LOWERCASE.length())));
        password.append(DIGITS.charAt(random.nextInt(DIGITS.length())));
        password.append(SPECIAL_CHARACTERS.charAt(random.nextInt(SPECIAL_CHARACTERS.length())));

        for (int i = 4; i < PASSWORD_LENGTH; i++) {
            password.append(ALL_CHARACTERS.charAt(random.nextInt(ALL_CHARACTERS.length())));
        }

        return new String(password);
    }

    public static byte[] readBytesFromMultipartFile (final MultipartFile multipartFile) {
        try {
            return multipartFile.getBytes();
        } catch (Exception e) {
            throw new MultipartFileReadException();
        }
    }

    public static byte[] convertInputStreamToByteArray (final InputStream inputStream) {
        try {
            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            final byte[] buffer = new byte[1024];

            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new InputStreamReadException();
        }
    }

    public static String removeBearerPrefix (final String token) {
        if (Objects.nonNull(token) && token.startsWith("Bearer ")) {
            return token.substring(7);
        }

        throw new InvalidTokenFormatException();
    }

    public static Boolean containsSpacesOrSpecialCharacters (final String input) {
        return containsWhitespace(input) || containsSpecialCharacters(input);
    }

    private static Boolean containsSpecialCharacters (final String input) {
        if (input == null) {
            return false;
        }
        return SPECIAL_CHARACTERS_PATTERN.matcher(input).find();
    }

    private static Boolean containsWhitespace (final String input) {
        if (input == null) {
            return false;
        }
        return WHITESPACE_PATTERN.matcher(input).find();
    }

    public static void updateFieldIfPresent(final String fieldValue, final Consumer<String> updateAction) {
        if (Objects.nonNull(fieldValue) && !fieldValue.isBlank()) {
            updateAction.accept(fieldValue);
        }
    }

    public static void validateImageType (final MultipartFile file) {
        if (Objects.isNull(file)) {
            return;
        }

        final String contentType = file.getContentType();

        if (Objects.isNull(contentType) ||
            (!contentType.equals("image/jpeg") && !contentType.equals("image/png") && !contentType.equals("image/jpg"))) {
            throw new InvalidFileTypeException();
        }
    }

    public static String formatDate (final Date date) {
        if (Objects.nonNull(date)) {
            final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            return sdf.format(date);
        }

        return "";
    }

    public static void checkPermission (final UserRoleResponseDTO userRoles, final String permission) {
        final boolean hasPermission = checkSystemPermission(userRoles, permission);

        if (!hasPermission) {
            throw new PermissionDeniedException(String.format(Constants.PERMISSION_DENIED_EXCEPTION_MESSAGE, permission));
        }
    }

    public static void checkPermission (final UserRoleResponseDTO userRoles, final Integer groupId,
                                        final String permission) {
        final boolean hasPermission =
            checkSystemPermission(userRoles, permission) ||
            checkGroupPermission(userRoles, groupId, permission);

        if (!hasPermission) {
            throw new PermissionDeniedException(String.format(Constants.PERMISSION_DENIED_EXCEPTION_MESSAGE, permission));
        }
    }

    public static void checkPermission (final UserRoleResponseDTO userRoles,
                                        final Integer groupId, final Integer resourceId,
                                        final String permission) {
        final boolean hasPermission =
            checkSystemPermission(userRoles, permission) ||
            checkGroupPermission(userRoles, groupId, permission) ||
            checkResourcePermission(userRoles, groupId, resourceId, permission);

        if (!hasPermission) {
            throw new PermissionDeniedException(String.format(Constants.PERMISSION_DENIED_EXCEPTION_MESSAGE, permission));
        }
    }

    private static boolean checkSystemPermission (final UserRoleResponseDTO userRoles, final String permission) {
        return userRoles
            .roles()
            .stream()
            .anyMatch(role ->
                RoleStatus.ACTIVE.getCode().equals(role.status()) &&
                role.systemPermissions()
                    .stream()
                    .anyMatch(sp -> Objects.equals(sp.description(), permission))
            );
    }

    private static boolean checkGroupPermission (final UserRoleResponseDTO userRoles, final Integer groupId,
                                                 final String permission) {
        return userRoles
            .roles()
            .stream()
            .anyMatch(role ->
                RoleStatus.ACTIVE.getCode().equals(role.status()) &&
                role.groupPermissions()
                    .stream()
                    .anyMatch(gp ->
                        Objects.equals(gp.group().id(), groupId) &&
                        gp.permissions()
                          .stream()
                          .anyMatch(p -> Objects.equals(p.description(), permission))
                    )
            );
    }

    private static boolean checkResourcePermission (final UserRoleResponseDTO userRoles,
                                                    final Integer groupId, final Integer resourceId,
                                                    final String permission) {
        return userRoles
            .roles()
            .stream()
            .anyMatch(role ->
                RoleStatus.ACTIVE.getCode().equals(role.status()) &&
                    role.resourcePermissions()
                        .stream()
                        .anyMatch(rp ->
                            Objects.equals(rp.resource().group().id(), groupId) &&
                            Objects.equals(rp.resource().id(), resourceId) &&
                            rp.permissions()
                                .stream()
                                .anyMatch(p -> Objects.equals(p.description(), permission))
                            )
            );
    }

    public static byte[] getImageFromClasspath (final String path) {
        final ClassPathResource classPathResource = new ClassPathResource(path);

        return _readBytesFromResource(classPathResource);
    }

    private static byte[] _readBytesFromResource (final ClassPathResource classPathResource) {
        try {
            return classPathResource.getInputStream().readAllBytes();
        } catch (Exception e) {
            throw new InputStreamFileReadException();
        }
    }
}