package com.ead.authuser.application.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) // toda serialização atributos nulos serão ocultados na saída
public class UserAuthDTO {

    private String userId;
    private String username;
    private String email;
    private String password;
    private String oldPassword;
    private String fullName;
    private String userStatus;
    private String userType;
    private String phoneNumber;
    private String cpf;
    private String imageUrl;


}
