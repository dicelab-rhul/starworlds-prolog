package uk.ac.rhul.cs.dice.starworlds.prolog.prolog.exception;

import uk.ac.rhul.cs.dice.starworlds.exceptions.StarWorldsRuntimeException;

public class PrologTermException extends StarWorldsRuntimeException {

	private static final long serialVersionUID = 420797281340642852L;

	public PrologTermException() {
		super();
	}

	public PrologTermException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PrologTermException(String message, Throwable cause) {
		super(message, cause);
	}

	public PrologTermException(String message) {
		super(message);
	}

	public PrologTermException(Throwable cause) {
		super(cause);
	}

}
