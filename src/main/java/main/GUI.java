package main;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class GUI {
	
	private JFrame frame = new JFrame("GUI");
	
    JPanel panel = new JPanel();

	
	
	public GUI() {
		createFrame();
	}
	
	private void createFrame() {
		
		

        Container c = frame.getContentPane();

        
        panel.setLayout(new GridLayout(0, 4));
        

        
        c.add(panel);

        frame.setSize(1280, 1024);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
	
	public void open() {
		frame.setVisible(true);
	}
	
	public void addJTextFields() {
        JTextField DCI = new JTextField("DCI");
        DCI.setHorizontalAlignment(JTextField.CENTER);
        JTextField DII = new JTextField("DII");
        DII.setHorizontalAlignment(JTextField.CENTER);
        JTextField ADCI = new JTextField("ADCI");
        ADCI.setHorizontalAlignment(JTextField.CENTER);
        JTextField ADII = new JTextField("ADII");
        ADII.setHorizontalAlignment(JTextField.CENTER);
        
        
        panel.add(DCI);
        panel.add(DII);
        panel.add(ADCI);
        panel.add(ADII);
	}
	

}
