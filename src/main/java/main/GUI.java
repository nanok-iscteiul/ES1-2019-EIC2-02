package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

public class GUI {

	JTextField DCIoutput;
	JTextField DIIoutput;
	JTextField ADCIoutput;
	JTextField ADIIoutput;
	private JFrame frame = new JFrame("GUI");
	private Application app;
	
	public GUI(Application app) {
		this.app=app;
		createFrame();
		load();
	}
	
	private void createFrame() {
		
		frame.setLayout(new BorderLayout());
		addJTextFields();        

        frame.setSize(720, 576);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
	
	public void load() {
		frame.setVisible(true);
	}
	
	public void addJTextFields() {
		JPanel panel = new JPanel();
		JPanel panel_aux = new JPanel();
		Container c = frame.getContentPane();

        panel.setLayout(new GridLayout(0, 4));
        
        c.add(panel);
		
        final JTextField filePath = new JTextField("Please Load An Exel file");
        filePath.setEditable(false);
        
        JButton loadFileButton = new JButton("load File");
        loadFileButton.setHorizontalAlignment(JButton.CENTER);
        loadFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
					app.setPath(chooser.getSelectedFile().getAbsolutePath());
                }
			}
		});
        
        panel_aux.add(filePath, BorderLayout.CENTER);
        panel_aux.add(loadFileButton);
        
        Border border = BorderFactory.createLineBorder(Color.BLUE, 1);
        DCIoutput = new JTextField("0");
        DCIoutput.setHorizontalAlignment(JTextField.CENTER);
        DCIoutput.setEditable(false);
        DCIoutput.setBorder(border);
        JTextField DIIoutput = new JTextField("0");
        DIIoutput.setHorizontalAlignment(JTextField.CENTER);
        DIIoutput.setEditable(false);
        DIIoutput.setBorder(border);
        JTextField ADCIoutput = new JTextField("0");
        ADCIoutput.setHorizontalAlignment(JTextField.CENTER);
        ADCIoutput.setEditable(false);
        ADCIoutput.setBorder(border);
        JTextField ADIIoutput = new JTextField("0");
        ADIIoutput.setHorizontalAlignment(JTextField.CENTER);
        ADIIoutput.setEditable(false);
        ADIIoutput.setBorder(border);
        
        JButton defectDefection = new JButton("Defect Defection");
        defectDefection.setHorizontalAlignment(JButton.CENTER);
        defectDefection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				app.defectDetection();
			}
		});
        
        JLabel DCI = new JLabel("DCI");
        DCI.setBorder(border);
        JLabel DII = new JLabel("DII");
        DII.setBorder(border);
        JLabel ADCI = new JLabel("ADCI");
        ADCI.setBorder(border);
        JLabel ADII = new JLabel("ADII");
        ADII.setBorder(border);
       
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
        
        frame.add(panel_aux, BorderLayout.NORTH);
        frame.add(defectDefection, BorderLayout.CENTER);
        frame.add(panel, BorderLayout.SOUTH);
        
	}
	public void receiveOutputDefectDetection(int DCIValue, int DIIValue, int ADCIValue, int ADIIValue) {
        DCIoutput.setText(String.valueOf(DCIValue));
        DIIoutput.setText(String.valueOf(1));
        ADCIoutput.setText(String.valueOf(1));
        ADIIoutput.setText(String.valueOf(1));
    }
	
	private void setThresholds(int loc, int cyclo) {
		app.setThresholds(loc, cyclo);
	}
}
