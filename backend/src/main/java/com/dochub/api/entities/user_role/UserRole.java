package com.dochub.api.entities.user_role;

import com.dochub.api.entities.AuditRecord;
import com.dochub.api.entities.Role;
import com.dochub.api.entities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "DOCHUB", name = "USUARIO_CARGO")
public class UserRole {
    @EmbeddedId
    private UserRolePK id;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "ID_CARGO", insertable = false, updatable = false)
    private Role role;

    @Embedded
    private AuditRecord auditRecord;

    public UserRole (final Integer idUser, final Integer idRole, final String initiatorUsername) {
        this.id = new UserRolePK(idUser, idRole);

        this.auditRecord = AuditRecord.builder()
            .insertionUser(initiatorUsername)
            .insertionDate(new Date())
            .build();
    }
}