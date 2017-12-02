package project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Scanner;

public class Loader {
	public static String load(MachineModel model, File program) {
		if (model == null || program == null) return null;
		ByteBuffer buff;
		try (FileChannel fChan = new FileInputStream(program).getChannel()) {
			long fSize = fChan.size();
			buff = ByteBuffer.allocate((int) fSize);
			fChan.read(buff);
		}
		catch (FileNotFoundException e) {
			return("File " + program.getName() + " Not Found");
		}
		catch (IOException e) {
			return("Unexpected IO exception in loading " + program.getName());
		}
		
		if (buff != null) {
			buff.rewind();
			int codeIndex = 0;
			while (buff.hasRemaining()) {
				byte b = buff.get();
				if (b < 0) break;
				Instruction instr = new Instruction(b, 0);
				if (!Instruction.noArgument(instr)) instr = new Instruction(b, buff.getInt());
				model.setCode(codeIndex++, instr);
			}
			while (buff.hasRemaining()) {
				model.setData(buff.getInt(), buff.getInt());
			}
		}
		// TODO : this will probably get changed at some point. gotta check piazza every now and again.
		return "success";
	}
}
