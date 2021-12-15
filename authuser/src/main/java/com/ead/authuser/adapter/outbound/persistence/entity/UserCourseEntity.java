package com.ead.authuser.adapter.outbound.persistence.entity;

import com.ead.authuser.application.model.UserCourseModel;
import com.ead.authuser.application.model.UserModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TB_USERS_COURSES")
public class UserCourseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private UserEntity user;

    @Column(nullable = false, name = "courseId")
    private UUID courseId;


    public UserCourseEntity(UserCourseModel userCourseModel) {
        UserModel userModel = userCourseModel.getUserModel();
        UserEntity userEntity = userModel.toEntity();

        this.user = userEntity;
        this.courseId = userCourseModel.getCourseId();

    }

    public static List<UserCourseEntity> convert(List<UserCourseModel> userCourseModels) {
        return userCourseModels.stream().map(UserCourseEntity::new).collect(Collectors.toList());
    }
}
