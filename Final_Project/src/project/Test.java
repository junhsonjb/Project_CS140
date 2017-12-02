package project;

public class Test {
	public static void main(String[] args) {
		
		//test instruction
		Instruction test = new Instruction((byte)0b11111100, 12);
		
		//test if checkParity works
		Instruction.checkParity( test );
		
		//printing text to see where we got in the code
		System.out.println("done with 1st test");
		
		//this should give an error
		test = new Instruction((byte)0b10000000, 11);
		//Instruction.checkParity(test); //may be commented out for testing
		
		System.out.println("done with 2nd test");
		
		test = new Instruction((byte)0b11000000, 1);
		Instruction.checkParity(test);
		
		System.out.println("done with 3rd test");
		
	}
}
