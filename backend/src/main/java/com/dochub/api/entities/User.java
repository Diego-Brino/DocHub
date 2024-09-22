package com.dochub.api.entities;

import com.dochub.api.dtos.user.CreateUserDTO;
import com.dochub.api.entities.user_role.UserRole;
import com.dochub.api.utils.Constants;
import com.dochub.api.utils.Utils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "DOCHUB", name = "USUARIO")
public class User implements UserDetails {
    @Id
    @Column(name = "ID_USUARIO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NOME", length = 256)
    private String name;

    @Column(name = "EMAIL", length = 128)
    private String email;

    @Column(name = "USERNAME", length = 256)
    private String username;

    @Column(name = "SENHA", length = 32)
    private String password;

    @Column(name = "AVATAR")
    private byte[] avatar;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA_ULTIMO_ACESSO")
    private Date lastAccess;

    @OneToMany(mappedBy = "user")
    private List<UserRole> userRoles;

    @Embedded
    private AuditRecord auditRecord;

    //region Constructors

    public User (final CreateUserDTO createUserDTO) {
        this.name = createUserDTO.name();
        this.email = createUserDTO.email();
        this.username = createUserDTO.username();
        this.password = Utils.encodePassword(createUserDTO.password());

        if (Objects.nonNull(createUserDTO.avatar())) {
            this.avatar = Utils.readBytesFromMultipartFile(createUserDTO.avatar());
        }

        this.auditRecord = AuditRecord.builder()
            .insertionUser(Constants.SYSTEM_NAME)
            .insertionDate(new Date())
            .build();
    }

    //endregion

    // region UserDetails Methods

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities () {
        return List.of();
    }

    // Retorna o email por conta do Spring Security, utilizar o getRealUsername para obter o username
    @Override
    public String getUsername () {
        return email;
    }

    public String getRealUsername () {
        return username;
    }

    @Override
    public String getPassword () {
        return password;
    }

    @Override
    public boolean isAccountNonExpired () {
        return true;
    }

    @Override
    public boolean isAccountNonLocked () {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired () {
        return true;
    }

    @Override
    public boolean isEnabled () {
        return true;
    }

    //endregion
}