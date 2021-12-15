package com.ead.authuser.adapter.outbound.client;

import com.ead.authuser.adapter.inbound.controller.dto.CourseDTO;
import com.ead.authuser.adapter.inbound.controller.dto.ResponsePageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient("ead-course-service/ead-course")
public interface CourseClientFeign {

    @GetMapping("/courses")
    ResponseEntity<ResponsePageDTO<CourseDTO>> buscarTodos(@PageableDefault(page = 0, size = 10, sort = "courseId", direction = Sort.Direction.ASC) Pageable pageable,
                                                           @RequestParam(required = false) UUID userId);

}
