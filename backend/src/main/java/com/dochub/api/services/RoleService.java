package com.dochub.api.services;

import com.dochub.api.dtos.role.CreateRoleDTO;
import com.dochub.api.dtos.role.RoleResponseDTO;
import com.dochub.api.entity.Role;
import com.dochub.api.exceptions.EntityNotFoundByIdException;
import com.dochub.api.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final UserService userService;
    private final RoleRepository roleRepository;

    public RoleResponseDTO getById (final Integer id) {
        final Role role = roleRepository
            .findById(id)
            .orElseThrow(EntityNotFoundByIdException::new);

        return new RoleResponseDTO(role);
    }

    public List<RoleResponseDTO> getAll () {
        final List<Role> roles = roleRepository.findAll();

        return roles
            .stream()
            .map(RoleResponseDTO::new)
            .collect(Collectors.toList());
    }

    public Integer create (final String token, final CreateRoleDTO createRoleDTO) {
        final String initiatorUsername = userService.getByToken(token).username();
        final Role role = new Role(createRoleDTO, initiatorUsername);

        return roleRepository.save(role).getId();
    }
}