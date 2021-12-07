package com.ead.course.adapter.repository.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@Table(name = "TB_COURSES_USERS")
public class CourseUserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private CourseEntity course;

    @Column(nullable = false, name = "userId")
    private UUID userId;


}
