package com.sparta.academy.enums;

public enum UserRoleEnum {
    MANAGER(Authority.MANAGER),
    STAFF(Authority.STAFF);

    private final String authority;

    UserRoleEnum(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }

    public static class Authority{
        public static final String MANAGER = "ROLE_MANAGER";
        public static final String STAFF = "ROLE_STAFF";
    }
}
