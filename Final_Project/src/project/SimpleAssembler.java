package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SimpleAssembler implements Assembler {

	private boolean readingCode = true;
	private int noArgCount = 0;
	
//	//constructor
//	public SimpleAssembler() {
//		noArgument = Arrays.asList("HALT", "NOP", "NOT");
//	}

	private Instruction makeCode(String[] parts) {

		if (noArgument.contains(parts[0])) {
			noArgCount++;
			int opPart = 8*Instruction.opcodes.get(parts[0]);
			opPart += Instruction.numOnes(opPart) % 2; //ensuring the parity is correct

			return new Instruction((byte)opPart, 0);
		}

		//if the above if clause fails, then the length of parts HAS to be 2
		//this is being assumed, so DON'T DO ANY CHECKING FOR IT (as per directions)

		int flags = 0;
//		System.out.println("HEY NOW"); //just for testing
		if (parts[1].charAt(0) == '#') {

			flags = 2;
			parts[1] = parts[1].substring(1);

		} else if (parts[1].charAt(0) == '@') {

			flags = 4;
			parts[1] = parts[1].substring(1);

		} else if (parts[1].charAt(0) == '&') {

			flags = 6;
			parts[1] = parts[1].substring(1);

		}

		int arg = Integer.parseInt(parts[1], 16);
		int opPart = 8 * Instruction.opcodes.get(parts[0]) + flags;
		opPart += Instruction.numOnes(opPart) % 2; //ensuring correctness of parity

		return new Instruction((byte)opPart, arg); 
	}

	private DataPair makeData(String[] parts) {
		//System.out.println(parts[0]); //for testing
		return new DataPair(Integer.parseInt(parts[0], 16), Integer.parseInt(parts[1], 16));
	}

	/**
	 * Method to assemble a file to its executable representation. 
	 * If the input has errors one or more of the errors will be reported 
	 * the StringBulder. The errors may not be the first error in 
	 * the code and will depend on the order in which instructions 
	 * are checked. There is no attempt to report all the errors.
	 * The line number of the last error that is reported 
	 * is returned as the value of the method. 
	 * A return value of 0 indicates that the code had no errors 
	 * and an output file was produced and saved. If the input or 
	 * output cannot be opened, the return value is -1.
	 * The unchecked exception IllegalArgumentException is thrown 
	 * if the error parameter is null, since it would not be 
	 * possible to provide error information about the source code.
	 * @param inputFileName the source assembly language file name
	 * @param outputFileName the file name of the executable version  
	 * of the program if the source program is correctly formatted
	 * @param error the StringBuilder to store the description 
	 * of the error or errors reported. It will be empty (length 
	 * zero) if no error is found.
	 * @return 0 if the source code is correct and the executable 
	 * is saved, -1 if the input or output files cannot be opened, 
	 * otherwise the line number of a reported error.
	 */
	@Override
	public int assemble(String inputFileName, String outputFileName, StringBuilder error) {
		int retValue = 0;
		
		Map<Boolean, List<String>> lists = null;
		try (Stream<String> lines = Files.lines(Paths.get(inputFileName))) {
			lists = lines
					.filter(line -> line.trim().length() > 0) // << CORRECTION <<
					.map(line -> line.trim()) // << CORRECTION <<
					.peek(line -> {if(line.toUpperCase().equals("DATA")) readingCode = false;})
					.map(line -> line.trim()) // << CORRECTION <<
					.collect(Collectors.partitioningBy(line -> readingCode));
			System.out.println("true List " + lists.get(true)); // these lines can be uncommented 
			System.out.println("false List " + lists.get(false)); // for checking the code
		} catch (IOException e) {
			e.printStackTrace();
		}
		lists.get(false).remove("DATA");

		List<Instruction> outputCode = lists.get(true).stream()
				.map(line -> line.split("\\s+"))
				.map(this::makeCode) // note how we use an instance method
				.collect(Collectors.toList());

		List<DataPair> outputData = lists.get(false).stream()
				.map(line -> line.split("\\s+"))
				.map(this::makeData)
				.collect(Collectors.toList());

		int bytesNeeded = noArgCount + 5 * (outputCode.size() - noArgCount) + 1 + 8 * ( outputData.size() );

		ByteBuffer buff = ByteBuffer.allocate(bytesNeeded);

		outputCode.stream()
		.forEach(instr -> {
			buff.put(instr.opcode);
			if(!Instruction.noArgument(instr)) {
				buff.putInt(instr.arg);
			}
		});

		buff.put((byte)-1);

		outputData.stream() 
		.forEach(pair -> {
			buff.putInt(pair.address);
			buff.putInt(pair.value);
		});

		//this is essential, it moves the current position of the byte buffer back to the start
		buff.rewind();

		boolean append = false;
		try (FileChannel wChannel = 
				new FileOutputStream(new File(outputFileName), append).getChannel()){
			wChannel.write(buff);
			wChannel.close();
		} catch (FileNotFoundException e) {
			// chose to check these two errors here instead of in FullAssembly. can change back if it doesn't work properly. it should though
			error.append("\nError: Unable to write the assembled program to the output file");
			retValue = -1;
		} catch (IOException e) {
			error.append("\nUnexplained IO Exception");
			retValue = -1;
		}

		return retValue;

	}

	public static void main(String[] args) {
		StringBuilder error = new StringBuilder();
		System.out.println("Enter the name of the file without extension: ");
		try (Scanner keyboard = new Scanner(System.in)) { 
			String filename = keyboard.nextLine();
			int i = new FullAssembler().assemble(filename + ".pasm", filename + ".pexe", error);
			System.out.println("result = " + i);
			System.out.println(error.toString());
		}
	}

}
