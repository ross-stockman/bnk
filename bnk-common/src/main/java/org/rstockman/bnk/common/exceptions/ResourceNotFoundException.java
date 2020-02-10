package org.rstockman.bnk.common.exceptions;

public class ResourceNotFoundException extends ClientErrorException {

	private static final long serialVersionUID = -4345083488182514374L;

	ResourceNotFoundException(String msg) {
		super(msg);
	}
}
