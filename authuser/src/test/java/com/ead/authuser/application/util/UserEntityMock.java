package com.ead.authuser.application.util;

import com.ead.authuser.adapter.repository.entity.UserEntity;
import com.ead.authuser.application.model.enums.UserStatus;
import com.ead.authuser.application.model.enums.UserType;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

public class UserEntityMock {


    public static List<UserEntity> getAll() {

        UserEntity user = new UserEntity();
        user.setUsername("Vitor");
        user.setEmail("teste@teste.com");
        user.setPassword("123456");
        user.setOldPassword("123456");
        user.setFullName("Teste Full Name");
        user.setUserStatus(UserStatus.ACTIVE);
        user.setUserType(UserType.STUDENT);
        user.setPhoneNumber("+55011912345678");
        user.setCpf("120.123.321-07");
        user.setImageUrl("url-image-test");
        user.setLastUpdateDate(generateCurrentDate());
        user.setCreationDate(generateCurrentDate());

        return List.of(user);
    }

    private static LocalDateTime generateCurrentDate() {
        return LocalDateTime.now(ZoneId.of("UTC"));
    }


}
