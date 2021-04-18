package com.mercadolibre.desafio_quality.config;

import javax.servlet.http.HttpServletRequest;

import com.mercadolibre.desafio_quality.exceptions.ApiError;
import com.mercadolibre.desafio_quality.exceptions.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<ApiError> noHandlerFoundException(HttpServletRequest req, NoHandlerFoundException ex) {
		ApiError apiError = new ApiError("route_not_found", String.format("Route %s not found", req.getRequestURI()), HttpStatus.NOT_FOUND.value());
		return ResponseEntity.status(apiError.getStatus())
							 .body(apiError);
	}

	@ExceptionHandler(value = { ApiException.class })
	protected ResponseEntity<ApiError> handleApiException(ApiException e) {
		ApiError apiError = new ApiError(e.getCode(), e.getDescription(), e.getStatusCode());
		return ResponseEntity.status(apiError.getStatus())
							 .body(apiError);
	}

	@ExceptionHandler(value = { Exception.class })
	protected ResponseEntity<ApiError> handleUnknownException(Exception e) {
		ApiError apiError = new ApiError("internal_error", "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR.value());
		return ResponseEntity.status(apiError.getStatus())
							 .body(apiError);
	}
}