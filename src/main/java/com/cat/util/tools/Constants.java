package com.cat.util.tools;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

    /**
     * Common Headers
     */

    public static final String X_CORRELATION_ID = "X-Correlation-ID";
    public static final String X_FORWARDED_FOR = "X-Forwarded-For";
    public static final String X_SESSION_ID = "X-Session-ID";
    public static final String X_REQUEST_ID = "X-Request-ID";
    public static final String USER_AGENT_HEADER = "User-Agent";
    public static final String TRACE_ID_HEADER = "traceId";

    public static final String UNKNOWN = "Unknown";
}
