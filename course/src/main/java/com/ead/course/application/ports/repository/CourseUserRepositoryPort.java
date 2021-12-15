package com.ead.course.application.ports.repository;

import com.ead.course.application.model.CourseModel;
import com.ead.course.application.model.CourseUserModel;

import java.util.List;
import java.util.UUID;

public interface CourseUserRepositoryPort {

    List<CourseUserModel> buscarTodosCursosPelo(UUID cursoId);

    void deletarTodos(List<CourseUserModel> courseUserModels);

    boolean existeUsuarioNoCurso(CourseModel courseModel, UUID userId);

    void salvar(CourseUserModel courseUserModel);
}
