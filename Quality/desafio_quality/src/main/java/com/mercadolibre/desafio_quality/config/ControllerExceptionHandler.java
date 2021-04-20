package com.mercadolibre.desafio_quality.config;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.mercadolibre.desafio_quality.exceptions.ApiError;
import com.mercadolibre.desafio_quality.exceptions.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/*
* Class for handling global exceptions.
* */
@ControllerAdvice
public class ControllerExceptionHandler {

	/*
	* Class for handling exceptions of type ApiException.
	* */
	@ExceptionHandler(value = { ApiException.class })
	protected ResponseEntity<ApiError> handleApiException(ApiException e) {
		ApiError apiError = new ApiError(e.getCode(), e.getDescription(), e.getStatusCode());
		return ResponseEntity.status(apiError.getStatus())
							 .body(apiError);
	}

	/*
	 * Class for handling unknown exceptions.
	 * */
	@ExceptionHandler(value = { Exception.class })
	protected ResponseEntity<ApiError> handleUnknownException(Exception e) {
		ApiError apiError = new ApiError("internal_error",
									  "Internal server error",
				                               HttpStatus.INTERNAL_SERVER_ERROR.value());
		return ResponseEntity.status(apiError.getStatus())
							 .body(apiError);
	}
}