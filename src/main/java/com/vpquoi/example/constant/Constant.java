package com.vpquoi.example.constant;

public class Constant {

    public static final String REDIS_SET_ACTIVE_SUBJECTS = "active-subjects";

    public static class EndPoint {
        public static class Auth {
            private static final String BASE = "/auth";
            public static final String TOKEN = BASE + "/token";
        }
    }

    // Access token valid for 10 minutes.
    public static final long JWT_ACCESS_TOKEN_VALIDITY = 1 * 10 * 60;
    // Refresh token valid for 60 minutes.
    public static final long JWT_REFRESH_TOKEN_VALIDITY = 1 * 60 * 60;
}
