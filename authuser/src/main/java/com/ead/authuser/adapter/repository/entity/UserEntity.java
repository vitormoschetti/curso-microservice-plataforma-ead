package com.ead.authuser.adapter.repository.entity;

import com.ead.authuser.application.model.enums.UserStatus;
import com.ead.authuser.application.model.enums.UserType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Data
@Entity
@Table(name = "TB_USERS")
public class UserEntity implements Serializable {

    // TODO regex para formatar phone e cpf

    private static final long serialVersionUID = 1L;

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID userId;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @JsonIgnore
    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, length = 150)
    private String fullName;

    @Column(nullable = false, name = "userStatus")
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @Column(nullable = false, name = "userType")
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column(length = 20)
    private String phoneNumber;

    @Column(length = 20)
    private String cpf;

    @Column
    private String imageUrl;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime creationDate;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime lastUpdateDate;

    public void newComplete() {
        this.setUserStatus(UserStatus.ACTIVE);
        this.setUserType(UserType.STUDENT);
        this.creationDate = LocalDateTime.now(ZoneId.of("UTC"));
        this.lastUpdateDate = LocalDateTime.now(ZoneId.of("UTC"));
    }
}
