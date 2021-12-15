package com.ead.authuser.adapter.inbound.controller.dto;

import com.ead.authuser.adapter.inbound.controller.dto.validation.CPFConstraint;
import com.ead.authuser.adapter.inbound.controller.dto.validation.PhoneNumberConstraint;
import com.ead.authuser.adapter.inbound.controller.dto.validation.UsernameConstraint;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) // toda serialização atributos nulos serão ocultados na saída
public class UserAuthDTO {

    public interface UserView {
        public static interface RegistrationPost {
        }

        public static interface UserPut {
        }

        public static interface PasswordPut {
        }

        public static interface ImagePut {
        }
    }


    private String userId;

    @JsonView(UserView.RegistrationPost.class)
    @NotBlank(groups = UserView.RegistrationPost.class)
    @UsernameConstraint(groups = UserView.RegistrationPost.class)
    @Size(min = 4, max = 50, groups = UserView.RegistrationPost.class)
    private String username;

    @JsonView(UserView.RegistrationPost.class)
    @Email(groups = UserView.RegistrationPost.class)
    @NotBlank(groups = UserView.RegistrationPost.class)
    @Size(min = 10, max = 50, groups = UserView.RegistrationPost.class)
    private String email;

    @JsonView({UserView.RegistrationPost.class, UserView.PasswordPut.class})
    @NotBlank(groups = {UserView.RegistrationPost.class, UserView.PasswordPut.class})
    @Size(min = 6, max = 255, groups = {UserView.RegistrationPost.class, UserView.PasswordPut.class})
    private String password;

    @JsonView(UserView.PasswordPut.class)
    @NotBlank(groups = UserView.PasswordPut.class)
    @Size(min = 6, max = 255, groups = UserView.PasswordPut.class)
    private String oldPassword;

    @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
    @Size(min = 5, max = 150, groups = {UserView.RegistrationPost.class, UserView.UserPut.class})
    private String fullName;

    @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
    @PhoneNumberConstraint(groups = {UserView.RegistrationPost.class, UserView.UserPut.class})
    @Size(min = 8, max = 20, groups = {UserView.RegistrationPost.class, UserView.UserPut.class})
    private String phoneNumber;


    @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
    @CPFConstraint(groups = {UserView.RegistrationPost.class, UserView.UserPut.class})
    @Size(min = 11, max = 20, groups = {UserView.RegistrationPost.class, UserView.UserPut.class})
    private String cpf;

    @JsonView(UserView.ImagePut.class)
    @NotBlank(groups = UserView.ImagePut.class)
    private String imageUrl;


}
