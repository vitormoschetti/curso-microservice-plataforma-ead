package com.ead.authuser.adapter.specifications;

import com.ead.authuser.adapter.repository.entity.UserCourseEntity;
import com.ead.authuser.adapter.repository.entity.UserEntity;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import java.util.UUID;

public class SpecificationTemplate {

    @And({
            @Spec(path = "email", spec = Like.class),
            @Spec(path = "fullName", spec = Like.class),
            @Spec(path = "userType", spec = Equal.class),
            @Spec(path = "userStatus", spec = Equal.class)
    })
    public interface UserSpec extends Specification<UserEntity> {
    }

    public static Specification<UserEntity> userCourseId(final UUID courseId) {
        return (root, query, cb) -> {
            query.distinct(true);
            Join<UserEntity, UserCourseEntity> usersCourses = root.join("usersCourses");
            return cb.equal(usersCourses.get("courseId"), courseId);
        };
    }


}
