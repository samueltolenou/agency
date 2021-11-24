package com.agency.payload;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ApiResponse {
    private Boolean success;
    private String message;
    private Object object;
    private HttpStatus statusCode;
    private int statusCode1;


    /**
     *
     * @param success
     * @param message
     * @param statusCode
     * @param object
     */
    public ApiResponse(Boolean success, String message, HttpStatus statusCode, Object object) {
        this.success = success;
        this.message = message;
        this.statusCode = statusCode;
        this.object = object;
    }

    /**
     *
     * @param success
     * @param message
     * @param statusCode
     */
    public ApiResponse(Boolean success, String message, HttpStatus statusCode) {
        this.success = success;
        this.message = message;
        this.statusCode = statusCode;
        this.object = object;
    }



    /**
     *
     * @param success
     * @param message
     * @param statusCode
     * @param object
     */
    public ApiResponse(Boolean success, String message, int statusCode, Object object) {
        this.success = success;
        this.message = message;
        this.statusCode1 = statusCode;
        this.object = object;
    }

    /**
     *
     * @param success
     * @param message
     */
    public ApiResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
