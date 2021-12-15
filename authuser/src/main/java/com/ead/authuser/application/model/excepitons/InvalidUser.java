package com.ead.authuser.application.model.excepitons;

public class InvalidUser extends RuntimeException {
    public InvalidUser(String msg) {
        super(msg);
    }
}
