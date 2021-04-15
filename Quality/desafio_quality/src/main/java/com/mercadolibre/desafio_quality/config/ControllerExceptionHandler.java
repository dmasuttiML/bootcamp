package com.mercadolibre.desafio_quality.config;

import javax.servlet.http.HttpServletRequest;
import com.mercadolibre.desafio_quality.dtos.ErrorDTO;
import com.mercadolibre.desafio_quality.exceptions.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ControllerExceptionHandler {
	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<ErrorDTO> noHandlerFoundException(HttpServletRequest req, NoHandlerFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
							 	 .body(new ErrorDTO(String.format("Route %s not found", req.getRequestURI()), HttpStatus.NOT_FOUND.value()));
	}

	@ExceptionHandler(value = { ApiException.class })
	protected ResponseEntity<ErrorDTO> handleApiException(ApiException e) {
		return ResponseEntity.status(e.getStatusCode())
							 .body(new ErrorDTO(e.getDescription(), e.getStatusCode()));
	}

	@ExceptionHandler(value = { Exception.class })
	protected ResponseEntity<ErrorDTO> handleUnknownException(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
							     .body(new ErrorDTO("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR.value()));
	}
}