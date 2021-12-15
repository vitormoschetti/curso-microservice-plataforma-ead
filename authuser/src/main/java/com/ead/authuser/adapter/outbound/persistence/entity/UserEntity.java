package com.ead.authuser.adapter.outbound.persistence.entity;

import com.ead.authuser.application.model.UserModel;
import com.ead.authuser.application.model.enums.UserStatus;
import com.ead.authuser.application.model.enums.UserType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@Table(name = "TB_USERS")
public class UserEntity implements Serializable {


    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "userId")
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

    @JsonIgnore
    @Column(nullable = false, length = 255, name = "oldPassword")
    private String oldPassword;

    @Column(nullable = false, length = 150, name = "fullName")
    private String fullName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "userStatus")
    private UserStatus userStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "userType")
    private UserType userType;

    @Column(length = 20, name = "phoneNumber")
    private String phoneNumber;

    @Column(length = 20)
    private String cpf;

    @Column(name = "imageUrl")
    private String imageUrl;

    @Column(nullable = false, name = "creationDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDateTime creationDate;

    @Column(nullable = false, name = "lastUpdateDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDateTime lastUpdateDate;

    @Column(name = "userscourses")
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<UserCourseEntity> usersCourses;

    public UserEntity(UserModel u) {
        this.username = u.getUsername();
        this.email = u.getEmail();
        this.password = u.getPassword();
        this.oldPassword = u.getOldPassword();
        this.fullName = u.getFullName();
        this.phoneNumber = u.getPhoneNumber();
        this.userStatus = u.getUserStatus();
        this.userType = u.getUserType();
        this.cpf = u.getCpf();
        this.creationDate = u.getCreationDate();
        this.lastUpdateDate = u.getLastUpdateDate();
    }

    public UserEntity update(UserModel u) {
        return build(u);
    }

    public UserEntity delete(UserModel u) {
        return build(u);
    }

    private UserEntity build(UserModel u) {
        this.userId = u.getUserId();
        this.username = u.getUsername();
        this.email = u.getEmail();
        this.password = u.getPassword();
        this.oldPassword = u.getOldPassword();
        this.fullName = u.getFullName();
        this.userStatus = u.getUserStatus();
        this.userType = u.getUserType();
        this.phoneNumber = u.getPhoneNumber();
        this.cpf = u.getCpf();
        this.imageUrl = u.getImageUrl();
        this.creationDate = u.getCreationDate();
        this.lastUpdateDate = u.getLastUpdateDate();
        return this;
    }

}
