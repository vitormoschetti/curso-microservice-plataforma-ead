package com.ead.authuser.adapter.repository.entity;

import com.ead.authuser.application.model.UserAuthDTO;
import com.ead.authuser.application.model.enums.UserStatus;
import com.ead.authuser.application.model.enums.UserType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime creationDate;

    @Column(nullable = false, name = "lastUpdateDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime lastUpdateDate;

    @Column(name = "userscourses")
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<UserCourseEntity> usersCourses;

    public void newComplete() {
        this.setUserStatus(UserStatus.ACTIVE);
        this.setUserType(UserType.STUDENT);
        this.creationDate = generateCurrentDate();
        this.lastUpdateDate = generateCurrentDate();
        this.oldPassword = password;
    }

    private LocalDateTime generateCurrentDate() {
        return LocalDateTime.now(ZoneId.of("UTC"));
    }

    public void updateUser(UserAuthDTO userAuthDTO) {

        this.fullName = userAuthDTO.getFullName();
        this.phoneNumber = userAuthDTO.getPhoneNumber();
        this.cpf = userAuthDTO.getCpf();
        this.lastUpdateDate = generateCurrentDate();

    }

    public boolean invalidPassword(UserAuthDTO passwordPut) {
        return !(passwordPut.getOldPassword().equals(this.password));
    }

    public void updatePassword(UserAuthDTO passwordPut) {

        this.password = passwordPut.getPassword();
        this.oldPassword = passwordPut.getPassword();
        this.lastUpdateDate = generateCurrentDate();

    }

    public void updateImage(UserAuthDTO imagePut) {
        this.imageUrl = imagePut.getImageUrl();
    }
}
