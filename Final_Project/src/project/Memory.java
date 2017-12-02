package project;

import java.util.Arrays;

public class Memory {
	
	public static final int DATA_SIZE = 512;
	public static final int CODE_SIZE = 256;
	private int[] data = new int[DATA_SIZE];
	private Instruction[] code = new Instruction[CODE_SIZE];
	
	private int changedDataIndex = -1;
	private int programSize = 0;
	
	//package private (for JUnit testing)
	int[] getData() {
		return this.data;
	}
	
	int getData(int index) {
		return this.data[index];
	}
	
	void setData(int index, int value) {
		this.data[index] = value;
	}
	
	void clearData() {
		for (int x : this.data) {
			x = 0;
		}
		changedDataIndex = -1;
	}
	
	int[] getData(int min, int max) {
		return Arrays.copyOfRange(data, min, max);
	}
	
	// NEW - getter method for changedDataIndex
	int getChangedDataIndex() {
		return changedDataIndex;
	}
	
	// NEW - getter method for programSize
	int getProgramSize() {
		return programSize;
	}

	// NEW - getter method for code
	Instruction[] getCode() {
		return code;
	}
	
	// NEW - uses Arrays.copyOfRange to return the part of code from index min to index max
	Instruction[] getCode(int min, int max) {
		return Arrays.copyOfRange(code, min, max);
	}
	
	// NEW - return the value stored in code at index 
	Instruction getCode(int index) {
		// this line is for future use as instructed by piazza
		//if(index < 0 || index >= CODE_SIZE) throw new CodeAccessException("Illegal access to code");
		return this.code[index];
	}
	
	// NEW - sets the value of code at index and also sets programSize to Math.max(programSize, index)
	void setCode(int index, Instruction value) {
		this.code[index] = value;
	}
	
	// NEW - sets every position in code to null << CORRECTION << (DOES NOTE set programSize to -1)
	// It says "DOES NOTE set programSize to -1". I think we will use the setter for programSize and he doesn't want to change it in here.
	void clearCode() {
		for (int i = 0; i < code.length; i++) code[i] = null;
	}
	
	// NEW - setter method for programSize << CORRECTION << 
	void setProgramSize(int programSize) {
		this.programSize = programSize;
	}
}
