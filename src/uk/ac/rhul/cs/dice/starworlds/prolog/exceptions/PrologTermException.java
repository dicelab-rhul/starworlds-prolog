package uk.ac.rhul.cs.dice.starworlds.prolog.exceptions;

public class PrologTermException extends PrologRunTimeException {

	private static final long serialVersionUID = -545739402250488317L;

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
