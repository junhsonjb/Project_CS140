package project;

import java.util.IllegalFormatFlagsException;

public class ParityCheckException extends IllegalFormatFlagsException {
	
	public ParityCheckException(String flags) {
		super(flags);
	}

}
