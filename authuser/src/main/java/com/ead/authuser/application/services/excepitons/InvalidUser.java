package com.ead.authuser.application.services.excepitons;

public class InvalidUser extends RuntimeException {
    public InvalidUser(String msg) {
        super(msg);
    }
}
