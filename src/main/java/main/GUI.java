package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

public class GUI {
	private JFrame frame = new JFrame("GUI");
	private Application app;
	
	public GUI(Application app) {
		this.app=app;
		createFrame();
		open();
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
		
        JButton openFileButton = new JButton("Open File");
        openFileButton.setHorizontalAlignment(JButton.CENTER);
        openFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
        
        JButton scanButton = new JButton("Scan File");
        scanButton.setHorizontalAlignment(JButton.CENTER);
        
        Border border = BorderFactory.createLineBorder(Color.BLUE, 1);
        JLabel DCI = new JLabel("DCI");
        DCI.setBorder(border);
        JLabel DII = new JLabel("DII");
        DII.setBorder(border);
        JLabel ADCI = new JLabel("ADCI");
        ADCI.setBorder(border);
        JLabel ADII = new JLabel("ADII");
        ADII.setBorder(border);
        
        JTextField DCIoutput = new JTextField("Press Scan");
        DCIoutput.setHorizontalAlignment(JTextField.CENTER);
        DCIoutput.setEditable(false);
        DCIoutput.setBorder(border);
        JTextField DIIoutput = new JTextField("Press Scan");
        DIIoutput.setHorizontalAlignment(JTextField.CENTER);
        DIIoutput.setEditable(false);
        DIIoutput.setBorder(border);
        JTextField ADCIoutput = new JTextField("Press Scan");
        ADCIoutput.setHorizontalAlignment(JTextField.CENTER);
        ADCIoutput.setEditable(false);
        ADCIoutput.setBorder(border);
        JTextField ADIIoutput = new JTextField("Press Scan");
        ADIIoutput.setHorizontalAlignment(JTextField.CENTER);
        ADIIoutput.setEditable(false);
        ADIIoutput.setBorder(border);
       
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
       
        panel.add(DCIoutput);
        panel.add(DIIoutput);
        panel.add(ADCIoutput);
        panel.add(ADIIoutput);
        
        frame.add(openFileButton, BorderLayout.NORTH);
        frame.add(scanButton, BorderLayout.CENTER);
        frame.add(panel, BorderLayout.SOUTH);
        
	}
	
	private void setThresholds(int loc, int cyclo) {
		app.setThresholds(loc, cyclo);
	}
	

}
