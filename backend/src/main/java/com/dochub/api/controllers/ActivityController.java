package com.dochub.api.controllers;

import com.dochub.api.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/activities")
@RequiredArgsConstructor
public class ActivityController {
    private final JwtService jwtService;
    private final UserService userService;
    private final UserRoleService userRoleService;
    private final ActivityService activityService;


}