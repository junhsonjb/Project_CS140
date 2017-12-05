package project;

import static project.Instruction.mnemonics;

import java.util.Map;
import java.util.TreeMap;

public class Instruction {
	
	byte opcode;
	int arg;
	
	public static Map<String, Integer> opcodes = new TreeMap<>();
	public static Map<Integer, String> mnemonics = new TreeMap<>();
	static {
		//NOT, HALT, LOD, STO, ADD, SUB, MUL, DIV, AND, JUMP, JMPZ, CMPL, CMPZ
		opcodes.put("NOP", 0);
		opcodes.put("NOT", 1);
		opcodes.put("HALT", 2);
		opcodes.put("LOD", 3);
		opcodes.put("STO", 4);
		opcodes.put("ADD", 5);
		opcodes.put("SUB", 6);
		opcodes.put("MUL", 7);
		opcodes.put("DIV", 8);
		opcodes.put("AND", 9);
		opcodes.put("JUMP", 10);
		opcodes.put("JMPZ", 11);
		opcodes.put("CMPL", 12);
		opcodes.put("CMPZ", 13);
		
		//populating the mnemonics Map
		for(String str : opcodes.keySet()) 
			mnemonics.put(opcodes.get(str), str);
	}
	
	public Instruction(byte operation, int argument) {
		this.opcode = operation;
		this.arg = argument;
	}
	
	public static boolean noArgument(Instruction instr) {
		if (instr.opcode < 24) {
			return true;
		}
		
		return false;
		
	}
	
	// this method will be package private for now
	static int numOnes(int k) {
		int num = 0;
		String str = Integer.toUnsignedString(k, 2);
		
		for (char c : str.toCharArray()) {
			if (c == '1') {
				num++;
			}
		}
		
		return num;
		
	}
	
	//this method will be package private for now
	static void checkParity(Instruction instr) {
		int result = numOnes(instr.opcode);
		
		//throw this exception if result is even
		if (result % 2 != 0) {
			throw new ParityCheckException("This instruction is corrupted.");
		}
		
		//no exception is thrown if result is even (which is the desired outcome)
	}
	
	// returns the instruction in coding language format
	public String getText() {
		StringBuilder buff = new StringBuilder(mnemonics.get(opcode/8) + "  ");
		if ((opcode & 6) == 2) buff.append("#");
		else if ((opcode & 6) == 4) buff.append("@");
		else if ((opcode & 6) == 6) buff.append("&");
		buff.append(Integer.toString(arg, 16));
		return buff.toString().toUpperCase();
	}
	
	// not totally sure what this does, i guess we will see. i think it returns the instruction in hex or something
	public String getBinHex() {
		//StringBuilder buff = new StringBuilder("00000000"); //this is the line that you put in, Jake
		//but the directions said to put in the length of "00000000" - 8, so I changed that ~Junhson
		StringBuilder buff = new StringBuilder("00000000".length()-8);
		buff.append(Integer.toString(opcode,2));
		buff.append("  ");
		buff.append(Integer.toHexString(arg));
		return buff.toString().toUpperCase();
	}
	
	// returns a description of the current instruction object
	@Override
	public String toString() {
		return "Instruction [" + Integer.toString(opcode,2) + ", " + Integer.toString(arg, 16)+"]";
	}
}
