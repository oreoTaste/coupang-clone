package com.quick.spring.project.exception;

import org.springframework.stereotype.Component;

@Component
public class DuplicateMemberException extends IllegalStateException{
    String msg;
    public DuplicateMemberException() {

    }
    public DuplicateMemberException(String msg) {
        this.msg = msg;
    }

    public String getMessage() {
        return this.msg;
    }
}
