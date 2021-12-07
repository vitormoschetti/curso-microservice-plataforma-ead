package com.ead.authuser.adapter.clients;

import com.ead.authuser.application.model.CourseDTO;
import com.ead.authuser.application.model.ResponsePageDTO;
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
public class CourseClient {

    private final RestTemplate restTemplate;

    public PageImpl<CourseDTO> getAllCoursesByUser(UUID userId, Pageable pageable) {

        List<CourseDTO> courseDTOS = null;

        String REQUEST_URI = "http://localhost:8082";

        String url = REQUEST_URI + "/courses?userId=" + userId + "&page=" + pageable.getPageNumber() + "&size=" + pageable.getPageSize() +
                "&sort=" + pageable.getSort().toString().replaceAll(": ", ",");

        log.debug("Request URL: {}", url);
        log.info("Request URL: {}", url);

        try {
            final ParameterizedTypeReference<ResponsePageDTO<CourseDTO>> responseType = new ParameterizedTypeReference<>() {
            };

            ResponseEntity<ResponsePageDTO<CourseDTO>> result = restTemplate.exchange(url, HttpMethod.GET, null, responseType);

            log.debug("Response number of elements: {}", result.getBody().getContent().size());

            courseDTOS = result.getBody().getContent();

        } catch (HttpStatusCodeException e) {
            log.error("Error request /courses {}", e);
        }

        log.info("Ending request /courses userId {}", userId);

        return new PageImpl<>(courseDTOS);
    }


}
