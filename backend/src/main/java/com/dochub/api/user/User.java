package com.dochub.api.user;

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

    @Column(name = "DATA_ULTIMO_ACESSO")
    private Date lastAccess;

    // region UserDetails Methods

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities () {
        return List.of();
    }

    @Override
    public String getUsername () {
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