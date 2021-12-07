package com.ead.authuser.adapter.repository.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Data
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

}
