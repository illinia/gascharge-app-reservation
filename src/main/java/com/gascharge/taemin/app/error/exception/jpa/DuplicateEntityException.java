package com.gascharge.taemin.app.error.exception.jpa;

public class DuplicateEntityException extends RuntimeException {
    public DuplicateEntityException() {
        super("이미 존재하는 엔티티입니다.");
    }

    public DuplicateEntityException(String message) {
        super(message);
    }

    public DuplicateEntityException(Class clazz, String key) {
        super(clazz.getName() + "의 클래스에 " + key + "에 해당하는 값이 존재합니다.");
    }
}
