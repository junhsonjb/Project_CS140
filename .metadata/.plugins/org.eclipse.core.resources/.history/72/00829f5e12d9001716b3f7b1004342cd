package projectview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.util.Observable;
import javax.swing.JFrame;
import javax.swing.JPanel;

import project.MachineModel;

import project.Memory;

public class ViewMediator extends Observable {
	
	private MachineModel model;
	private JFrame frame;
	private StepControl stepControl;
	private CodeViewPanel codeViewPanel;
	private MemoryViewPanel memoryViewPanel1;
	private MemoryViewPanel memoryViewPanel2;
	private MemoryViewPanel memoryViewPanel3;
	private ControlPanel controlPanel;
	private ProcessorViewPanel processorPanel;
	private States currentState = States.NOTHING_LOADED;
	
	public void step() {
		
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
	
	public States getCurrentState() {
		return currentState;
	}
	
	public void setCurrentState(States cs) {
		currentState = cs;
	}
	
	private void createAndShowGUI() {
		stepControl = new StepControl(this);
		//filesMgr = new FilesMgr(this);
		//filesMgr.initialize();
		codeViewPanel = new CodeViewPanel(this, model);
		memoryViewPanel1 = new MemoryViewPanel(this, model, 0, 160);
		memoryViewPanel2 = new MemoryViewPanel(this, model, 160, Memory.DATA_SIZE/2);
		memoryViewPanel3 = new MemoryViewPanel(this, model, Memory.DATA_SIZE/2, Memory.DATA_SIZE);
		controlPanel = new ControlPanel(this);
		processorPanel = new ProcessorViewPanel(this, model);
		//menuBuilder = new MenuBarBuilder(this);
		frame = new JFrame("Simulator");
		//JMenuBar bar = new JMenuBar();
		//frame.setJMenuBar(bar);
		//bar.add(menuBuilder.createFileMenu());
		//bar.add(menuBuilder.createExecuteMenu());

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
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		//frame.addWindowListener(WindowListenerFactory.windowClosingFactory(e -> exit()));
		frame.setLocationRelativeTo(null);
		stepControl.start();
		//currentState().enter();
		setChanged();
		notifyObservers();
		frame.setVisible(true);
	}
	
	public void clear() {}
	
	public void toggleAutoStep() {}
	
	public void reload() {}
	
	public void setPeriod(int value) {}
	
	public void makeReady(String s) {  }

	public void execute() {  }

	public void assembleFile() {  }

	public void loadFile() {  }

	public void exit() {  }
	
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
