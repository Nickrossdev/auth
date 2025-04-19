package com.nickross.auth.util;

import com.nickross.auth.dto.ApiResponse;

public class ApiResponseUtil {
    public static <T,E> ApiResponse<T,E> success() {
        ApiResponse<T,E> response = new ApiResponse<>();
        response.setSuccess(true);
        response.setMessage("Operación Exitosa");
        return response;
    }
    public static <T,E> ApiResponse<T,E> success(String message) {
        ApiResponse<T,E> response = new ApiResponse<>();
        response.setSuccess(true);
        response.setMessage(message);
        return response;
    }
    public static <T,E> ApiResponse<T,E> success(T data) {
        ApiResponse<T,E> response = new ApiResponse<>();
        response.setSuccess(true);
        response.setMessage("Operación Exitosa");
        response.setData(data);
        return response;
    }
    public static <T,E>ApiResponse<T,E> success(String message, T data) {
        ApiResponse<T,E> response = new ApiResponse<>();
        response.setSuccess(true);
        response.setMessage(message);
        response.setData(data);
        return response;
    }
    public static <T,E>ApiResponse<T,E> failed() {
        ApiResponse<T,E> response = new ApiResponse<>();
        response.setSuccess(false);
        response.setMessage("Operación Fallida");
        return response;
    }
    public static <T,E>ApiResponse<T,E>failed(String message) {
        ApiResponse<T,E>response = new ApiResponse<>();
        response.setSuccess(false);
        response.setMessage(message);
        return response;
    }
    public static <T,E>ApiResponse<T,E> failed(E errors) {
        ApiResponse<T,E> response = new ApiResponse<>();
        response.setSuccess(false);
        response.setMessage("Operación Fallida");
        response.setDetails(errors);
        return response;
    }
    public static <T,E>ApiResponse<T,E> failed(String message, E errors) {
        ApiResponse<T,E> response = new ApiResponse<>();
        response.setSuccess(false);
        response.setMessage(message);
        response.setDetails(errors);
        return response;
    }
}
