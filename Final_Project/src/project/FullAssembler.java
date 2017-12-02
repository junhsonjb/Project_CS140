package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FullAssembler implements Assembler {
	
	@Override
	public int assemble(String inputFileName, String outputFileName, StringBuilder error) {
		// make sure the error buffer is okay before checking for file errors
		if(error == null) throw new IllegalArgumentException("Coding error: the error buffer is null");
		
		ArrayList<String> sourceFile = new ArrayList<>();
		int retValue = 0;
		
		// Read each line of the source file into the sourceFile ArrayList
		try (Scanner s = new Scanner(new File(inputFileName))) {
			while (s.hasNextLine()) {
				sourceFile.add(s.nextLine());
			}
		} catch (FileNotFoundException e) {
			error.append("\nUnable to open the source file");
			retValue = -1;
		}
		
		// variables required for error checking
		boolean blankLine = false;
		int blankLineValue = 0;
		
		boolean readingCode = true;
		
		
		// check file for all errors presented in piazza
		for (int i = 0; i < sourceFile.size(); i++) {
			
			String line = sourceFile.get(i);
			
			// 1.) Checks for any blank lines in the file followed by a legitimate instruction.
			if (blankLine && line.trim().length() > 0) {
				error.append("\nError on line " + (blankLineValue+1) + ": illegal blank line");
				retValue = blankLineValue + 1;
				blankLine = false;
			}
			if (!blankLine && line.trim().length() == 0) {
				blankLine = true;
				blankLineValue = i;
			}
			
			// 2.) Check to see if there is a white space before an instruction
			if (line.trim().length() > 0) {
				if (line.charAt(0) == ' ' || line.charAt(0) == '\t') {
					error.append("\nError on line " + (i+1) + ": line starts with illegal white space");
					retValue = i + 1;
				}
			}
			
			// 3.) Checks to see that the DATA separator (if present) is written correctly
			if (line.trim().toUpperCase().equals("DATA")) {
				if (!line.trim().equals("DATA")) {
					error.append("\nError on line " + (i+1) + ": DATA does not appear in upper case");
					retValue = i + 1;
				}
				else if (!readingCode) {
					error.append("\nError on line " + (i+1) + ": more than two DATA separators appear");
					retValue = i + 1;
				}
				readingCode = false;
			}
			
			// splits the instruction up into multiple parts
			String[] parts = line.trim().split("\\s+");
			
			// 4.) Checks to make sure all opcodes are valid and upper case
			if (readingCode && Instruction.opcodes.keySet().contains(parts[0].toUpperCase())) {
				if (!Instruction.opcodes.keySet().contains(parts[0])) {
					error.append("\nError on line " + (i+1) + ": mnemonic must be upper case");
					retValue = i + 1;
				}
			}
			else if (readingCode && parts[0].length() > 0) {
				error.append("\nError on line " + (i+1) + ": illegal mnemonic");
				retValue = i + 1;
			}
			
			// 5.) Check to see if there are the correct amount of parts in the instruction
			if (readingCode && noArgument.contains(parts[0])) {
				if (parts.length != 1) {
					error.append("\nError on line " + (i+1) + ": this mnemonic cannot take arguments");
					retValue = i + 1;
				}
			}
			else if (readingCode && parts[0].length() > 0 && Instruction.opcodes.keySet().contains(parts[0])) {
				if (parts.length < 2) {
					error.append("\nError on line " + (i+1) + ": this mnemonic is missing an argument");
					retValue = i + 1;
				}
				else if (parts.length > 2) {
					error.append("\nError on line " + (i+1) + ": this mnemonic has too many arguments");
					retValue = i + 1;
				}
			}
			
			// 6.) Checks to make sure the arguments is a valid hex number
			if (readingCode && parts.length > 1) {
				String arg = parts[1];
				if (arg.substring(0, 1).equals("#") || arg.substring(0, 1).equals("@") || arg.substring(0, 1).equals("&")) {
					arg = arg.substring(1);
				}
				try {
					Integer.parseInt(arg,16);
				} catch(NumberFormatException e) {
					error.append("\nError on line " + (i+1) + ": argument is not a hex number");
					retValue = i + 1;				
				}
			}
			
			// 7.) Checks to make sure data values are all correct
			if (!readingCode && !line.trim().toUpperCase().equals("DATA") && line.trim().length() > 0) {
				try {
					Integer.parseInt(parts[0],16);
				} catch(NumberFormatException e) {
					error.append("\nError on line " + (i+1) + ": data has non-numeric memory address");
					retValue = i + 1;				
				}
			}
			if (parts.length > 1 && !readingCode && !line.trim().toUpperCase().equals("DATA") && line.trim().length() > 0) {
				try {
					Integer.parseInt(parts[1],16);
				} catch(NumberFormatException e) {
					error.append("\nError on line " + (i+1) + ": data has non-numeric memory value");
					retValue = i + 1;				
				}
			}
		}
		
		if (retValue == 0) {
			// no error is found, continue on with assembly
			return new SimpleAssembler().assemble(inputFileName, outputFileName, error);
		}
		return retValue;
	}
}
