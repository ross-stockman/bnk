package org.rstockman.bnk.common.controller;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.rstockman.bnk.common.exceptions.ResourceConflictException;
import org.rstockman.bnk.common.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ErrorController {

	@ExceptionHandler
	public ResponseEntity<?> exception(ResourceNotFoundException e, HttpServletRequest req) {
		return handleError(e, req, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler
	public ResponseEntity<?> exception(ResourceConflictException e, HttpServletRequest req) {
		return handleError(e, req, HttpStatus.CONFLICT);
	}

	private ResponseEntity<?> handleError(RuntimeException e, HttpServletRequest req, HttpStatus status) {
		var parameters = new HashMap<String, List<String>>();
		req.getParameterMap().forEach((k, v) -> {
			parameters.put(k, Arrays.asList(v));
		});
		var error = ErrorInfo.builder().message(e.getMessage()).refId(UUID.randomUUID().toString())
				.timestamp(Instant.now().toString()).status(status).path(req.getRequestURI()).parameters(parameters)
				.build();
		log.info("Handling {}: Consumer response: {}: Caused by: {}", status, error, e.toString());
		return new ResponseEntity<>(error, status);
	}

	@Data
	@Builder
	static class ErrorInfo {
		private String path;
		private Map<String, List<String>> parameters;
		private String refId;
		private String message;
		private String timestamp;
		private HttpStatus status;
	}
}
