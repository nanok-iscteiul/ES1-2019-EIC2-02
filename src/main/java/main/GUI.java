package main;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class GUI {
	private JFrame frame = new JFrame("GUI");
	
	
	public GUI() {
		createFrame();
	}
	
	private void createFrame() {
		
		frame.setLayout(new BorderLayout());
		addJTextFields();        

        frame.setSize(720, 576);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
	
	public void open() {
		frame.setVisible(true);
	}
	
	public void addJTextFields() {
		JPanel panel = new JPanel();
		Container c = frame.getContentPane();

        panel.setLayout(new GridLayout(0, 4));
        
        c.add(panel);
		
        JTextField DCI = new JTextField("DCI");
        JTextField DII = new JTextField("DII");
        JTextField ADCI = new JTextField("ADCI");
        JTextField ADII = new JTextField("ADII");
       
        DCI.setHorizontalAlignment(JTextField.CENTER);
        DII.setHorizontalAlignment(JTextField.CENTER);
        ADCI.setHorizontalAlignment(JTextField.CENTER);
        ADII.setHorizontalAlignment(JTextField.CENTER);
        
        Dimension d = new Dimension(200,50);
        DCI.setPreferredSize(d);
        DII.setPreferredSize(d);
        ADCI.setPreferredSize(d);
        ADII.setPreferredSize(d);
        
        panel.add(DCI);
        panel.add(DII);
        panel.add(ADCI);
        panel.add(ADII);
        
        frame.add(panel, BorderLayout.SOUTH);
	}
	

}
