package com.ead.course.adapter.specification;

import com.ead.course.adapter.repository.entity.CourseEntity;
import com.ead.course.adapter.repository.entity.CourseUserEntity;
import com.ead.course.adapter.repository.entity.LessonEntity;
import com.ead.course.adapter.repository.entity.ModuleEntity;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.UUID;

public class SpecificationTemplate {


    @And(value = {
            @Spec(path = "courseLevel", spec = Equal.class),
            @Spec(path = "courseStatus", spec = Equal.class),
            @Spec(path = "name", spec = Like.class)
    })
    public interface CourseSpec extends Specification<CourseEntity> {
    }

    @Spec(path = "title", spec = Like.class)
    public interface ModuleSpec extends Specification<ModuleEntity> {
    }

    @Spec(path = "title", spec = Like.class)
    public interface LessonSpec extends Specification<LessonEntity> {
    }

    public static Specification<ModuleEntity> moduleCourseId(final UUID courseId) {
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);
            Root<ModuleEntity> moduleEntity = root;
            Root<CourseEntity> courseEntity = query.from(CourseEntity.class);
            Expression<Collection<ModuleEntity>> coursesModules = courseEntity.get("modules");
            return criteriaBuilder.and(criteriaBuilder.equal(courseEntity.get("courseId"), courseId), criteriaBuilder.isMember(moduleEntity, coursesModules));
        };
    }

    public static Specification<LessonEntity> lessonModuleId(final UUID moduleId) {
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);
            Root<LessonEntity> lessonEntity = root;
            Root<ModuleEntity> moduleEntity = query.from(ModuleEntity.class);
            Expression<Collection<LessonEntity>> moduleLessons = moduleEntity.get("lessons");
            return criteriaBuilder.and(criteriaBuilder.equal(moduleEntity.get("moduleId"), moduleId), criteriaBuilder.isMember(lessonEntity, moduleLessons));
        };
    }

    public static Specification<CourseEntity> courseUserId(final UUID courseId) {
        return (root, query, cb) -> {
            query.distinct(true);
            Join<CourseEntity, CourseUserEntity> usersCourses = root.join("coursesUsers");
            return cb.equal(usersCourses.get("userId"), courseId);
        };
    }

}
