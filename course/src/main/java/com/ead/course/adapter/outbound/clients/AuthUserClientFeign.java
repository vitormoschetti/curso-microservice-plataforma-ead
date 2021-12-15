package com.ead.course.adapter.outbound.clients;

import com.ead.course.adapter.inbound.controller.dto.SubscriptionDTO;
import com.ead.course.adapter.inbound.controller.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@FeignClient("ead-authuser-service/ead-authuser/users")
public interface AuthUserClientFeign {

    @GetMapping
    ResponseEntity<?> getAllUsers(@PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable,
                                  @RequestParam(required = false) UUID courseId);

    @GetMapping("/{userId}")
    ResponseEntity<UserDTO> getUser(@PathVariable UUID userId);

    @PostMapping("/{userId}/courses/subscription")
    public ResponseEntity<String> saveSubscriptionUserInCourse(@PathVariable(value = "userId") UUID userId,
                                                          @RequestBody @Valid SubscriptionDTO subscriptionDTO);

}
