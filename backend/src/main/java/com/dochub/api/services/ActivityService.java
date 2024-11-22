package com.dochub.api.services;

import com.dochub.api.dtos.activity.ActivityResponseDTO;
import com.dochub.api.dtos.activity.CreateActivityDTO;
import com.dochub.api.dtos.activity.UpdateActivityDTO;
import com.dochub.api.dtos.user_roles.UserRoleResponseDTO;
import com.dochub.api.entities.Activity;
import com.dochub.api.exceptions.CannotDeleteActivityException;
import com.dochub.api.exceptions.EntityNotFoundByIdException;
import com.dochub.api.repositories.ActivityRepository;
import com.dochub.api.utils.Constants;
import com.dochub.api.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityService {
    private final ActivityRepository activityRepository;

    public Activity getById (final Integer activityId) {
        return activityRepository
            .findById(activityId)
            .orElseThrow(EntityNotFoundByIdException::new);
    }

    public ActivityResponseDTO getDtoById (final Integer activityId) {
        final Activity activity = getById(activityId);

        return new ActivityResponseDTO(activity);
    }

    public List<ActivityResponseDTO> getAll () {
        final List<Activity> activities = activityRepository.findAll();

        return activities
            .stream()
            .map(ActivityResponseDTO::new)
            .collect(Collectors.toList());
    }

    public Integer create (final UserRoleResponseDTO userRoles, final CreateActivityDTO createActivityDTO) {
        Utils.checkPermission(userRoles, Constants.CREATE_ACTIVITY);

        final Activity activity = new Activity(createActivityDTO, userRoles.user().username());

        return activityRepository.save(activity).getId();
    }

    public void update (final UserRoleResponseDTO userRoles, final Integer activityId, final UpdateActivityDTO updateActivityDTO) {
        Utils.checkPermission(userRoles, Constants.EDIT_ACTIVITY);

        final Activity activity = getById(activityId);

        Utils.updateFieldIfPresent(updateActivityDTO.description(), activity::setDescription);

        _logAuditForChange(activity, userRoles.user().username());

        activityRepository.save(activity);
    }

    public void delete (final UserRoleResponseDTO userRoles, final Integer activityId,
                        final Function<Activity, Boolean> hasFlowsAssignedToActivityFunc) {
        Utils.checkPermission(userRoles, Constants.DELETE_ACTIVITY);

        final Activity activity = getById(activityId);

        final Boolean hasProcessAssignedToService = hasFlowsAssignedToActivityFunc.apply(activity);

        if (hasProcessAssignedToService) {
            throw new CannotDeleteActivityException();
        }

        activityRepository.delete(activity);
    }

    private void _logAuditForChange (final Activity activity, final String actor) {
        activity.getAuditRecord().setInsertionUser(actor);
        activity.getAuditRecord().setInsertionDate(new Date());
    }
}