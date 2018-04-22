package uk.ac.rhul.cs.dice.starworlds.prolog.exceptions;

import uk.ac.rhul.cs.dice.starworlds.exceptions.StarWorldsException;

public class PrologException extends StarWorldsException {

	private static final long serialVersionUID = -4670058858763179270L;

	public PrologException() {
		super();
	}

	public PrologException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PrologException(String message, Throwable cause) {
		super(message, cause);
	}

	public PrologException(String message) {
		super(message);
	}

	public PrologException(Throwable cause) {
		super(cause);
	}

}
