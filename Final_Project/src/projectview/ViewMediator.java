package projectview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.util.Observable;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import project.DivideByZeroException;
import project.IllegalInstructionException;
import project.MachineModel;
import project.Memory;
import project.ParityCheckException;
import project.CodeAccessException;

public class ViewMediator extends Observable {
	
	private MachineModel model; //some small change for another repo push
	private JFrame frame;
	StepControl stepControl;
	CodeViewPanel codeViewPanel;
	MemoryViewPanel memoryViewPanel1;
	MemoryViewPanel memoryViewPanel2;
	MemoryViewPanel memoryViewPanel3;
	ControlPanel controlPanel;
	ProcessorViewPanel processorPanel;
	//"we are missing 3 fields that will come later"
	States currentState = States.NOTHING_LOADED; // I think this is one of them
	private FilesMgr filesMgr; // Here are the next two
	private MenuBarBuilder menuBuilder;
	
	//the setCurrentState() method
	public void setCurrentState(States s) {
		if(s == States.PROGRAM_HALTED) stepControl.setAutoStepOn(false);		
		currentState = s;
		s.enter();		
		setChanged();
		notifyObservers();
	}
	
	//and the getter
	public States getCurrentState() {
		return this.currentState;
	}
	
	public void step() { 
		while (currentState != States.PROGRAM_HALTED && 
				currentState != States.NOTHING_LOADED) {
			try {
				model.step();
			} catch (CodeAccessException e) {
				JOptionPane.showMessageDialog(frame, 
					"Illegal access to code from line " + model.getPC() + "\n"
							+ "Exception message: " + e.getMessage(),
							"Run time error",
							JOptionPane.OK_OPTION);
				System.out.println("Illegal access to code from line " + model.getPC()); // just for debugging
				System.out.println("Exception message: " + e.getMessage());			
			} catch(ArrayIndexOutOfBoundsException e) {
				JOptionPane.showMessageDialog(frame, 
						"Array index out of bounds from line " + model.getPC() + "\n"
								+ "Exception message: " + e.getMessage(),
								"Run time error",
								JOptionPane.OK_OPTION);
					System.out.println("Array index out of bounds from line " + model.getPC()); // just for debugging
					System.out.println("Exception message: " + e.getMessage());	
			} catch(NullPointerException e) {
				JOptionPane.showMessageDialog(frame, 
						"Null pointer exception from line " + model.getPC() + "\n"
								+ "Exception message: " + e.getMessage(),
								"Run time error",
								JOptionPane.OK_OPTION);
					System.out.println("Null pointer exception from line " + model.getPC()); // just for debugging
					System.out.println("Exception message: " + e.getMessage());
			} catch(ParityCheckException e) {
				JOptionPane.showMessageDialog(frame, 
						"Parity check exception from line " + model.getPC() + "\n"
								+ "Exception message: " + e.getMessage(),
								"Run time error",
								JOptionPane.OK_OPTION);
					System.out.println("Parity check exception from line " + model.getPC()); // just for debugging
					System.out.println("Exception message: " + e.getMessage());
			} catch(IllegalInstructionException e) {
				JOptionPane.showMessageDialog(frame, 
						"Illegal instruction from line " + model.getPC() + "\n"
								+ "Exception message: " + e.getMessage(),
								"Run time error",
								JOptionPane.OK_OPTION);
					System.out.println("Illegal instruction from line " + model.getPC()); // just for debugging
					System.out.println("Exception message: " + e.getMessage());
			} catch(IllegalArgumentException e) {
				JOptionPane.showMessageDialog(frame, 
						"Illegal argument from line " + model.getPC() + "\n"
								+ "Exception message: " + e.getMessage(),
								"Run time error",
								JOptionPane.OK_OPTION);
					System.out.println("Illegal argument from line " + model.getPC()); // just for debugging
					System.out.println("Exception message: " + e.getMessage());
			} catch(DivideByZeroException e) {
				JOptionPane.showMessageDialog(frame, 
						"Divided by zero on line " + model.getPC() + "\n"
								+ "Exception message: " + e.getMessage(),
								"Run time error",
								JOptionPane.OK_OPTION);
					System.out.println("Divided by zero on line " + model.getPC()); // just for debugging
					System.out.println("Exception message: " + e.getMessage());
			}
			setChanged();
			notifyObservers();
		}
	}
	
	public MachineModel getModel() {
		return model;
	}
	
	public void setModel(MachineModel model) {
		this.model = model;
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
	public void makeReady(String s) {
		stepControl.setAutoStepOn(false);
		setCurrentState(States.PROGRAM_LOADED_NOT_AUTOSTEPPING);
		currentState.enter();
		setChanged();
		notifyObservers(s);
		model.setProgramSize(0);
	}

	public void execute() {
		while (currentState != States.PROGRAM_HALTED && 
				currentState != States.NOTHING_LOADED) {
			try {
				model.step();
			} catch (CodeAccessException e) {
				JOptionPane.showMessageDialog(frame, 
					"Illegal access to code from line " + model.getPC() + "\n"
							+ "Exception message: " + e.getMessage(),
							"Run time error",
							JOptionPane.OK_OPTION);
				System.out.println("Illegal access to code from line " + model.getPC()); // just for debugging
				System.out.println("Exception message: " + e.getMessage());			
			} catch(ArrayIndexOutOfBoundsException e) {
				JOptionPane.showMessageDialog(frame, 
						"Array index out of bounds from line " + model.getPC() + "\n"
								+ "Exception message: " + e.getMessage(),
								"Run time error",
								JOptionPane.OK_OPTION);
					System.out.println("Array index out of bounds from line " + model.getPC()); // just for debugging
					System.out.println("Exception message: " + e.getMessage());	
			} catch(NullPointerException e) {
				JOptionPane.showMessageDialog(frame, 
						"Null pointer exception from line " + model.getPC() + "\n"
								+ "Exception message: " + e.getMessage(),
								"Run time error",
								JOptionPane.OK_OPTION);
					System.out.println("Null pointer exception from line " + model.getPC()); // just for debugging
					System.out.println("Exception message: " + e.getMessage());
			} catch(ParityCheckException e) {
				JOptionPane.showMessageDialog(frame, 
						"Parity check exception from line " + model.getPC() + "\n"
								+ "Exception message: " + e.getMessage(),
								"Run time error",
								JOptionPane.OK_OPTION);
					System.out.println("Parity check exception from line " + model.getPC()); // just for debugging
					System.out.println("Exception message: " + e.getMessage());
			} catch(IllegalInstructionException e) {
				JOptionPane.showMessageDialog(frame, 
						"Illegal instruction from line " + model.getPC() + "\n"
								+ "Exception message: " + e.getMessage(),
								"Run time error",
								JOptionPane.OK_OPTION);
					System.out.println("Illegal instruction from line " + model.getPC()); // just for debugging
					System.out.println("Exception message: " + e.getMessage());
			} catch(IllegalArgumentException e) {
				JOptionPane.showMessageDialog(frame, 
						"Illegal argument from line " + model.getPC() + "\n"
								+ "Exception message: " + e.getMessage(),
								"Run time error",
								JOptionPane.OK_OPTION);
					System.out.println("Illegal argument from line " + model.getPC()); // just for debugging
					System.out.println("Exception message: " + e.getMessage());
			} catch(DivideByZeroException e) {
				JOptionPane.showMessageDialog(frame, 
						"Divided by zero on line " + model.getPC() + "\n"
								+ "Exception message: " + e.getMessage(),
								"Run time error",
								JOptionPane.OK_OPTION);
					System.out.println("Divided by zero on line " + model.getPC()); // just for debugging
					System.out.println("Exception message: " + e.getMessage());
			}
		}
		setChanged();
		notifyObservers();
	}

	public void assembleFile() {
		filesMgr.assembleFile();
	}

	public void loadFile() {
		filesMgr.loadFile();
	}

	public void exit() { // method executed when user exits the program
		int decision = JOptionPane.showConfirmDialog(
				frame, "Do you really wish to exit?",
				"Confirmation", JOptionPane.YES_NO_OPTION);
		if (decision == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}
	
	private void createAndShowGUI() {
		stepControl = new StepControl(this);
		
		filesMgr = new FilesMgr(this);
		filesMgr.initialize();
		codeViewPanel = new CodeViewPanel(this, model);
		memoryViewPanel1 = new MemoryViewPanel(this, model, 0, 160);
		memoryViewPanel2 = new MemoryViewPanel(this, model, 160, Memory.DATA_SIZE/2);
		memoryViewPanel3 = new MemoryViewPanel(this, model, Memory.DATA_SIZE/2, Memory.DATA_SIZE);
		controlPanel = new ControlPanel(this);
		processorPanel = new ProcessorViewPanel(this, model);
		menuBuilder = new MenuBarBuilder(this);
		frame = new JFrame("Simulator");
		JMenuBar bar = new JMenuBar();
		frame.setJMenuBar(bar);
		bar.add(menuBuilder.createFileMenu());
		bar.add(menuBuilder.createExecuteMenu());

		Container content = frame.getContentPane(); 
		content.setLayout(new BorderLayout(1,1));
		content.setBackground(Color.BLACK);
		frame.setSize(1200,600);
		frame.add(codeViewPanel.createCodeDisplay(), BorderLayout.LINE_START);
		frame.add(processorPanel.createProcessorDisplay(),BorderLayout.PAGE_START);
		JPanel center = new JPanel();
		center.setLayout(new GridLayout(1,3));
		center.add(memoryViewPanel1.createMemoryDisplay());
		center.add(memoryViewPanel2.createMemoryDisplay());
		center.add(memoryViewPanel3.createMemoryDisplay());
		frame.add(center, BorderLayout.CENTER);
		frame.add(controlPanel.createControlDisplay(), BorderLayout.PAGE_END);
		// the next line will be commented or deleted later
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(WindowListenerFactory.windowClosingFactory(e -> exit()));
		frame.setLocationRelativeTo(null);
		stepControl.start();
		currentState.enter();
		setChanged();
		notifyObservers();
		frame.setVisible(true);
	}
	
	public void clear() {
		model.clear();
		model.setProgramSize(0);
		setCurrentState(States.NOTHING_LOADED);
		currentState.enter();
		setChanged();
		notifyObservers("Clear");
		System.out.println(model.getCode()[3]);
	}
	
	
	public void toggleAutoStep() {
		stepControl.toggleAutoStep();
		if (stepControl.isAutoStepOn()) {
			setCurrentState(States.AUTO_STEPPING);
		} else {
			setCurrentState(States.PROGRAM_LOADED_NOT_AUTOSTEPPING);
		}
	}
	
	public void reload() {
		stepControl.setAutoStepOn(false);
		clear();
		filesMgr.finalLoad_ReloadStep();
	}
	
	public void setPeriod(int value) {
		stepControl.setPeriod(value);
	}
	
	
	//provided main method
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ViewMediator mediator = new ViewMediator();
				MachineModel model = 
					new MachineModel(true, () -> 
					mediator.setCurrentState(States.PROGRAM_HALTED));
				mediator.setModel(model);
				mediator.createAndShowGUI();
			}
		});
	}
}