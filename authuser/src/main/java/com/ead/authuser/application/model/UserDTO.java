package com.ead.authuser.application.model;

import com.ead.authuser.adapter.repository.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL) // toda serialização atributos nulos serão ocultados na saída
public class UserDTO {

    private String userId;
    private String username;
    private String email;
    private String fullName;
    private String userStatus;
    private String userType;
    private String phoneNumber;
    private String cpf;
    private String imageUrl;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime creationDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime lastUpdateDate;

    private UserDTO(UserEntity u) {

        this.userId = u.getUserId().toString();
        this.username = u.getUsername();
        this.email = u.getEmail();
        this.fullName = u.getFullName();
        this.userStatus = u.getUserStatus().name();
        this.userType = u.getUserType().name();
        this.phoneNumber = u.getPhoneNumber();
        this.cpf = u.getCpf();
        this.imageUrl = u.getImageUrl();
        this.creationDate = u.getCreationDate();
        this.lastUpdateDate = u.getLastUpdateDate();

    }


    public static Page<UserDTO> convert(Page<UserEntity> userEntities) {

        List<UserDTO> userDTOS = userEntities.stream().map(userEntity -> new UserDTO(userEntity)).collect(Collectors.toList());


        Page<UserDTO> dtos = new PageImpl<>(userDTOS);

        return dtos;
    }

    public static Optional<UserDTO> convert(Optional<UserEntity> userEntity) {

        if (userEntity.isEmpty())
            return Optional.empty();

        return Optional.of(convert(userEntity.get()));

    }

    public static UserDTO convert(UserEntity userEntity) {
        return new UserDTO(userEntity);
    }

}
