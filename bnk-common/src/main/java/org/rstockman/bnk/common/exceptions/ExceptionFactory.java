package org.rstockman.bnk.common.exceptions;

public final class ExceptionFactory {

	public static ClientErrorException resourceNotFound() {
		return new ResourceNotFoundException("Resource not found.");
	}

	public static ClientErrorException resourceConflict(String id) {
		return new ResourceConflictException(String.format("Unable to find linked object %s", id));
	}

}
