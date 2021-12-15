package com.ead.authuser.application.model;

import com.ead.authuser.adapter.inbound.controller.dto.UserAuthDTO;
import com.ead.authuser.adapter.outbound.persistence.entity.UserCourseEntity;
import com.ead.authuser.adapter.outbound.persistence.entity.UserEntity;
import com.ead.authuser.application.model.enums.UserStatus;
import com.ead.authuser.application.model.enums.UserType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
public class UserModel {

    private UUID userId;
    private String username;
    private String email;
    private String password;
    private String oldPassword;
    private String fullName;
    private UserStatus userStatus;
    private UserType userType;
    private String phoneNumber;
    private String cpf;
    private String imageUrl;
    private LocalDateTime creationDate;
    private LocalDateTime lastUpdateDate;
    private Set<UserCourseEntity> usersCourses;

    public UserModel(){}

    public UserModel(UserEntity u) {
        this.userId = u.getUserId();
        this.username = u.getUsername();
        this.email = u.getEmail();
        this.fullName = u.getFullName();
        this.userStatus = u.getUserStatus();
        this.userType = u.getUserType();
        this.phoneNumber = u.getPhoneNumber();
        this.cpf = u.getCpf();
        this.imageUrl = u.getImageUrl();
        this.creationDate = u.getCreationDate();
        this.lastUpdateDate = u.getLastUpdateDate();
        this.password = u.getPassword();
        this.oldPassword = u.getOldPassword();
    }

    public UserModel(UserAuthDTO u) {
        this.username = u.getUsername();
        this.email = u.getEmail();
        this.fullName = u.getFullName();
        this.phoneNumber = u.getPhoneNumber();
        this.cpf = u.getCpf();
        this.password = u.getPassword();
        this.userStatus = UserStatus.ACTIVE;
        this.userType = UserType.STUDENT;
        this.creationDate = generateCurrentDate();
        this.lastUpdateDate = generateCurrentDate();
        this.oldPassword = u.getPassword();

    }

    public UserEntity toEntity(){
        return new UserEntity(this);
    }

    public static Page<UserModel> convert(Page<UserEntity> userEntities) {

        List<UserModel> userModels = userEntities.getContent().stream().map(UserModel::new).collect(Collectors.toList());


        return new PageImpl<>(userModels);
    }

    public void newComplete() {

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

    public UserCourseModel converterParaUserCourse(UUID courseId) {
        return new UserCourseModel(this, courseId);
    }

    public void promoveParaInstrutor() {
        this.lastUpdateDate = generateCurrentDate();
        this.userType = UserType.INSTRUCTOR;
    }

    private LocalDateTime generateCurrentDate() {
        return LocalDateTime.now(ZoneId.of("UTC"));
    }
}
