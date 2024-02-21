package com.cat.util.exception;

import com.cat.util.exception.model.ErrorResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_GATEWAY;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.GATEWAY_TIMEOUT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_IMPLEMENTED;
import static org.springframework.http.HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;
import static org.springframework.http.HttpStatus.UNSUPPORTED_MEDIA_TYPE;


@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class AbstractAppException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -1L;

    protected static final Map<Integer, String> exceptionMapping;

    static {
        exceptionMapping = new HashMap<>();
        exceptionMapping.put(BAD_REQUEST.value(), "Incorrect parameter in the request");
        exceptionMapping.put(UNAUTHORIZED.value(), "Missing authentication in the request");
        exceptionMapping.put(FORBIDDEN.value(), "Request for a restricted resource");
        exceptionMapping.put(NOT_FOUND.value(), "Request for a non-existent resource (empty lists should return 200 OK or 206 Partial Content)");
        exceptionMapping.put(METHOD_NOT_ALLOWED.value(), "Use of a method not allowed for the requested resource (but handled otherwise)");
        exceptionMapping.put(NOT_ACCEPTABLE.value(), "Missing or unexpected content type or content encoding in the request");
        exceptionMapping.put(UNSUPPORTED_MEDIA_TYPE.value(), "Request for a non-existent range of an actual resource");
        exceptionMapping.put(UNPROCESSABLE_ENTITY.value(), "Incorrect entity in the request");
        exceptionMapping.put(INTERNAL_SERVER_ERROR.value(), "Unrecoverable error from the service (not related to an upstream service called by it)");
        exceptionMapping.put(NOT_IMPLEMENTED.value(), "Use of a method not handled for any resource");
        exceptionMapping.put(BAD_GATEWAY.value(), "Error from an essential upstream service called by the main service");
        exceptionMapping.put(GATEWAY_TIMEOUT.value(), "Timeout from an essential upstream service called by the main service");
        exceptionMapping.put(REQUESTED_RANGE_NOT_SATISFIABLE.value(), "Range not satisfied");
    }

    @JsonIgnore
    private Integer httpStatus; //NOSONAR

    @JsonIgnore
    private String mainResource; //NOSONAR

    @JsonProperty("errors")
    private List<ErrorResponse> errors;

    protected AbstractAppException(int httpStatus, String mainResource) {
        super(eventCode(httpStatus));
        this.httpStatus = httpStatus;
        this.mainResource = mainResource;
    }

    private static String eventCode(Integer httpStatus) {
        return exceptionMapping.computeIfAbsent(httpStatus, val -> "HTTP-" + httpStatus);
    }

}

