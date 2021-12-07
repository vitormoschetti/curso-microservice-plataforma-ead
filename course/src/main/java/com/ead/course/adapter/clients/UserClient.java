package com.ead.course.adapter.clients;

import com.ead.course.application.model.ResponsePageDTO;
import com.ead.course.application.model.UserDTO;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Log4j2
@Component
@AllArgsConstructor
public class UserClient {

    private final RestTemplate restTemplate;

    public PageImpl<UserDTO> getAllUsersByCourse(UUID courseId, Pageable pageable) {

        List<UserDTO> userDTOS = null;

        String REQUEST_URI = "http://localhost:8087";

        String url = REQUEST_URI + "/users?courseId=" + courseId + "&page=" + pageable.getPageNumber() + "&size=" + pageable.getPageSize() +
                "&sort=" + pageable.getSort().toString().replaceAll(": ", ",");

        log.debug("Request URL: {}", url);
        log.info("Request URL: {}", url);

        try {
            final ParameterizedTypeReference<ResponsePageDTO<UserDTO>> responseType = new ParameterizedTypeReference<>() {};

            ResponseEntity<ResponsePageDTO<UserDTO>> result = restTemplate.exchange(url, HttpMethod.GET, null, responseType);

            log.debug("Response number of elements: {}", result.getBody().getContent().size());

            userDTOS = result.getBody().getContent();

        } catch (HttpStatusCodeException e) {
            log.error("Error request /users {}", e);
        }

        log.info("Ending request /users courseId {}", courseId);

        return new PageImpl<>(userDTOS);
    }


}
