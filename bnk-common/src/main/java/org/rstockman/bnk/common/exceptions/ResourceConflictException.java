package org.rstockman.bnk.common.exceptions;

public class ResourceConflictException extends ClientErrorException {

	private static final long serialVersionUID = -7305054843109276604L;

	ResourceConflictException(String msg) {
		super(msg);
	}

}
