package project;

public class IllegalInstructionException extends RuntimeException {

	//should these constructors be with or without parameters? //resolved
	public IllegalInstructionException(String message) {
		super(message);
	}
	
}
