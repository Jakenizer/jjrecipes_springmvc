package se.jjrecipes.exception;

public class ImageException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	private final String type = "ImageError";
	
	public ImageException(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public String getType() {
		return type;
	}
}
