package com.dochub.api.entity;

import com.dochub.api.dtos.RegisterUserDTO;
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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USUARIO")
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

    @Embedded
    private AuditRecord auditRecord;

    //region Constructors

    public User (RegisterUserDTO registerUserDTO) {
        this.name = registerUserDTO.name();
        this.email = registerUserDTO.email();
        this.username = registerUserDTO.username();
        this.password = Utils.encodePassword(registerUserDTO.password());
        this.avatar = registerUserDTO.avatar();

        this.auditRecord = AuditRecord
            .builder()
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

    @Override
    public String getUsername () {
        return email;
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