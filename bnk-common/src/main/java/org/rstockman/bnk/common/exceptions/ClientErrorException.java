package org.rstockman.bnk.common.exceptions;

public abstract class ClientErrorException extends IllegalArgumentException {

	private static final long serialVersionUID = -1293168291654060334L;

	ClientErrorException(String msg) {
		super(msg);
	}
}
