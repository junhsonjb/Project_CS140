package project;

public class DivideByZeroException extends RuntimeException {

	//should these constructors be with or without parameters? //resolved
	public DivideByZeroException(String message) {
		super(message);
	}
	
}
