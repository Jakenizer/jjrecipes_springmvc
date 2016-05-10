package se.jjrecipes.exception;

public class JJRuntimeException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Exception cause;
	
	public JJRuntimeException(Exception cause) {
		this.cause = cause;
	}
	
	public Exception getCause() {
		return cause;
	}
}
