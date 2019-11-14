package main;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class GUI {
	
	private JFrame frame = new JFrame("GUI");
	
	
	public GUI() {
		createFrame();
	}
	
	private void createFrame() {

        Container c = frame.getContentPane();

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        c.add(panel);

        frame.setSize(1280, 1024);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
	
	public void open() {
		frame.setVisible(true);
	}
	

}
