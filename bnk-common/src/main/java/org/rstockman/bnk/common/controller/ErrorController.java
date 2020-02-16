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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ErrorController.class);

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
		var error = new ErrorInfo(e.getMessage(), UUID.randomUUID().toString(), Instant.now().toString(), status,
				req.getRequestURI(), parameters);
		LOGGER.info("Handling {}: Consumer response: {}: Caused by: {}", status, error, e.toString());
		return new ResponseEntity<>(error, status);
	}

	static class ErrorInfo {
		private String path;
		private Map<String, List<String>> parameters;
		private String refId;
		private String message;
		private String timestamp;
		private HttpStatus status;

		public ErrorInfo(String message, String refId, String timestamp, HttpStatus status, String path,
				Map<String, List<String>> parameters) {
			super();
			this.path = path;
			this.parameters = parameters;
			this.refId = refId;
			this.message = message;
			this.timestamp = timestamp;
			this.status = status;
		}

		public ErrorInfo() {
			super();
		}

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}

		public Map<String, List<String>> getParameters() {
			return parameters;
		}

		public void setParameters(Map<String, List<String>> parameters) {
			this.parameters = parameters;
		}

		public String getRefId() {
			return refId;
		}

		public void setRefId(String refId) {
			this.refId = refId;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public String getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(String timestamp) {
			this.timestamp = timestamp;
		}

		public HttpStatus getStatus() {
			return status;
		}

		public void setStatus(HttpStatus status) {
			this.status = status;
		}

	}
}
