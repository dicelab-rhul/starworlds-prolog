package uk.ac.rhul.cs.dice.starworlds.prolog.exceptions;

import uk.ac.rhul.cs.dice.starworlds.exceptions.StarWorldsRuntimeException;

public class PrologRunTimeException extends StarWorldsRuntimeException {

	private static final long serialVersionUID = -2901865141208725530L;

	public PrologRunTimeException() {
		super();
	}

	public PrologRunTimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PrologRunTimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public PrologRunTimeException(String message) {
		super(message);
	}

	public PrologRunTimeException(Throwable cause) {
		super(cause);
	}

}
