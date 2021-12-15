package com.ead.course.adapter.outbound.clients;

import com.ead.course.adapter.inbound.controller.dto.CourseUserDTO;
import com.ead.course.adapter.inbound.controller.dto.ResponsePageDTO;
import com.ead.course.adapter.inbound.controller.dto.UserDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
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
public class AuthUserClient {

    private final RestTemplate restTemplate;

    private final String requestUri;

    public AuthUserClient(RestTemplate restTemplate, @Value("${ead.api.url.authuser}") String requestUri) {
        this.restTemplate = restTemplate;
        this.requestUri = requestUri;
    }

    public PageImpl<UserDTO> buscaTodosUsuarioPorCurso(UUID cursoId, Pageable pageable) {

        List<UserDTO> userDTOS = null;

        String url = requestUri + "/users?courseId=" + cursoId + "&page=" + pageable.getPageNumber() + "&size=" + pageable.getPageSize() +
                "&sort=" + pageable.getSort().toString().replaceAll(": ", ",");

        log.debug("Request URL: {}", url);
        log.info("Request URL: {}", url);

        try {
            final ParameterizedTypeReference<ResponsePageDTO<UserDTO>> responseType = new ParameterizedTypeReference<>() {
            };

            ResponseEntity<ResponsePageDTO<UserDTO>> result = restTemplate.exchange(url, HttpMethod.GET, null, responseType);

            log.debug("Número de elementos retornados: {}", result.getBody().getContent().size());

            userDTOS = result.getBody().getContent();

        } catch (HttpStatusCodeException e) {
            log.error("Error request /users {}", e);
        }

        log.info("Fim request /users com o cursoId: {}", cursoId);

        return new PageImpl<>(userDTOS);
    }

    public UserDTO buscarUmUsuarioPeloId(UUID usuarioId) {

        String url = requestUri + "/users/" + usuarioId;

        log.debug("Request URL: {}", url);
        log.info("Request URL: {}", url);

        return restTemplate.exchange(url, HttpMethod.GET, null, UserDTO.class).getBody();

    }


    public void postSubscriptionUserInCourse(UUID cursoId, UUID usuarioId) {
        String url = requestUri + "/users/" + usuarioId + "/courses/subscription";

        CourseUserDTO courseUserDTO = new CourseUserDTO(cursoId);

        restTemplate.postForObject(url, courseUserDTO, String.class);

    }
}
