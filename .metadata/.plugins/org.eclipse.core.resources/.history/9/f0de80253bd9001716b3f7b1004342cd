package projectview;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class ControlPanel implements Observer {
	
	private ViewMediator mediator;
	private JButton stepButton = new JButton("Step");
	private JButton clearButton = new JButton("Clear");
	private JButton runButton = new JButton("Run/Pause");
	private JButton reloadButton = new JButton("Reload");
	
	public ControlPanel(ViewMediator med) {
		this.mediator = med;
		mediator.addObserver(this);
	}
	
	public JComponent createControlDisplay() {
		JPanel panel = new JPanel(new GridLayout(1, 0));
		
		//code for stepbutton
		stepButton.setBackground(Color.WHITE);
		stepButton.addActionListener(e -> mediator.step());
		panel.add(stepButton);
		
		clearButton.setBackground(Color.WHITE);
		clearButton.addActionListener(e -> mediator.clear());
		panel.add(clearButton);
		
		runButton.setBackground(Color.WHITE);
		runButton.addActionListener(e -> mediator.toggle());
		panel.add(runButton);
		
		reloadButton.setBackground(Color.WHITE);
		reloadButton.addActionListener(e -> mediator.reload());
		panel.add(reloadButton);
		
		JSlider slider = new JSlider(5,1000);
		slider.addChangeListener(e -> mediator.setPeriod(slider.getValue())); // have not written the methods yet
		panel.add(slider);
		
		return panel;
	}
	
	//this method will be implemented once we've completed ViewMediatorC
	@Override
	public void update(Observable arg0, Object arg1) {		
	}
	
	

}
