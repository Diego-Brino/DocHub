package com.dochub.api.services;

import com.dochub.api.dtos.service.CreateServiceDTO;
import com.dochub.api.dtos.service.ServiceResponseDTO;
import com.dochub.api.dtos.service.UpdateServiceDTO;
import com.dochub.api.dtos.user_roles.UserRoleResponseDTO;
import com.dochub.api.exceptions.EntityNotFoundByIdException;
import com.dochub.api.exceptions.ServiceHasProcessesInProgressException;
import com.dochub.api.repositories.ServiceRepository;
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
public class ServiceService {
    private final ServiceRepository serviceRepository;

    public com.dochub.api.entities.Service getById (final Integer serviceId) {
        return serviceRepository
            .findById(serviceId)
            .orElseThrow(EntityNotFoundByIdException::new);
    }

    public ServiceResponseDTO getDtoById (final Integer serviceId) {
        final com.dochub.api.entities.Service service = getById(serviceId);

        return new ServiceResponseDTO(service);
    }

    public List<ServiceResponseDTO> getAll () {
        final List<com.dochub.api.entities.Service> services = serviceRepository.findAll();

        return services
            .stream()
            .map(ServiceResponseDTO::new)
            .collect(Collectors.toList());
    }

    public Integer create (final UserRoleResponseDTO userRoles, final CreateServiceDTO createServiceDTO) {
        Utils.checkPermission(userRoles, Constants.CREATE_SERVICE_PERMISSION);

        final com.dochub.api.entities.Service service = new com.dochub.api.entities.Service(createServiceDTO, userRoles.user().username());

        return serviceRepository.save(service).getId();
    }

    public void update (final UserRoleResponseDTO userRoles,
                        final Integer serviceId, final UpdateServiceDTO updateServiceDTO,
                        final Function<com.dochub.api.entities.Service, Boolean> hasRequestInProgressAssignedToServiceFunc) {
        Utils.checkPermission(userRoles, Constants.EDIT_SERVICE_PERMISSION);

        final com.dochub.api.entities.Service service = getById(serviceId);
        final Boolean hasRequestInProgressAssignedToService = hasRequestInProgressAssignedToServiceFunc.apply(service);

        if (hasRequestInProgressAssignedToService) {
            throw new ServiceHasProcessesInProgressException();
        }

        Utils.updateFieldIfPresent(updateServiceDTO.description(), service::setDescription);

        _logAuditForChange(service, userRoles.user().username());

        serviceRepository.save(service);
    }

    public void delete (final UserRoleResponseDTO userRoles, final Integer serviceId,
                        final Function<com.dochub.api.entities.Service, Boolean> hasRequestInProgressAssignedToServiceFunc) {
        Utils.checkPermission(userRoles, Constants.DELETE_SERVICE_PERMISSION);

        final com.dochub.api.entities.Service service = getById(serviceId);
        final Boolean hasRequestInProgressAssignedToService = hasRequestInProgressAssignedToServiceFunc.apply(service);

        if (hasRequestInProgressAssignedToService) {
            throw new ServiceHasProcessesInProgressException();
        }

        serviceRepository.delete(service);
    }

    private void _logAuditForChange (final com.dochub.api.entities.Service service, final String actor) {
        service.getAuditRecord().setAlterationUser(actor);
        service.getAuditRecord().setAlterationDate(new Date());
    }
}