package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

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
	
	private JTextField DCIOutput;
	private JTextField DIIOutput;
	private JTextField ADCIOutput;
	private JTextField ADIIOutput;

	public GUI(Application app) {
		this.app = app;
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

		final JTextField loc = new JTextField();
		loc.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent keyEvent) {
				char c = keyEvent.getKeyChar();
				if (!(Character.isDigit(c)) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE) {
					keyEvent.consume();
				}
			}

		});

		final JTextField cyclo = new JTextField();
		cyclo.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent keyEvent) {
				char c = keyEvent.getKeyChar();
				if (!(Character.isDigit(c)) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE) {
					keyEvent.consume();
				}
			}
		});

		JButton longMethod = new JButton("longMethod");
		longMethod.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (loc.getText() != "" && cyclo.getText() != "") {
					int locValue = Integer.parseInt(loc.getText());
					int cycloValue = Integer.parseInt(cyclo.getText());

					app.setLocCycloThresholds(locValue, cycloValue);
					app.longMethod();
				}
			}
		});

		loc.setHorizontalAlignment(JTextField.CENTER);
		frame.add(loc, BorderLayout.CENTER);

		Border border = BorderFactory.createLineBorder(Color.BLUE, 1);
		JLabel DCI = new JLabel("DCI");
		DCI.setBorder(border);
		JLabel DII = new JLabel("DII");
		DII.setBorder(border);
		JLabel ADCI = new JLabel("ADCI");
		ADCI.setBorder(border);
		JLabel ADII = new JLabel("ADII");
		ADII.setBorder(border);

		DCIOutput = new JTextField("Press Scan");
		DCIOutput.setHorizontalAlignment(JTextField.CENTER);
		DCIOutput.setEditable(false);
		DCIOutput.setBorder(border);
		DIIOutput = new JTextField("Press Scan");
		DIIOutput.setHorizontalAlignment(JTextField.CENTER);
		DIIOutput.setEditable(false);
		DIIOutput.setBorder(border);
		ADCIOutput = new JTextField("Press Scan");
		ADCIOutput.setHorizontalAlignment(JTextField.CENTER);
		ADCIOutput.setEditable(false);
		ADCIOutput.setBorder(border);
		ADIIOutput = new JTextField("Press Scan");
		ADIIOutput.setHorizontalAlignment(JTextField.CENTER);
		ADIIOutput.setEditable(false);
		ADIIOutput.setBorder(border);

		DCI.setHorizontalAlignment(JTextField.CENTER);
		DII.setHorizontalAlignment(JTextField.CENTER);
		ADCI.setHorizontalAlignment(JTextField.CENTER);
		ADII.setHorizontalAlignment(JTextField.CENTER);

		Dimension d = new Dimension(200, 50);
		DCI.setPreferredSize(d);
		DII.setPreferredSize(d);
		ADCI.setPreferredSize(d);
		ADII.setPreferredSize(d);

		panel.add(DCI);
		panel.add(DII);
		panel.add(ADCI);
		panel.add(ADII);

		panel.add(DCIOutput);
		panel.add(DIIOutput);
		panel.add(ADCIOutput);
		panel.add(ADIIOutput);

		frame.add(openFileButton, BorderLayout.NORTH);
		frame.add(scanButton, BorderLayout.CENTER);
		frame.add(panel, BorderLayout.SOUTH);
//		frame.add(longMethod, BorderLayout.SOUTH);

	}

	public void receiveOutputLongMethod(List<Method> longMethods, List<Method> nonLongMethods) {
		// mostra na gui os resultados da execucao do long method
		

	}

	public void receiveOutputDefectDetection(int DCIValue, int DIIValue, int ADCIValue, int ADIIValue) {
		DCIOutput.setText(""+DCIValue);
		DIIOutput.setText(""+DIIValue);
		ADCIOutput.setText(""+ADCIValue);
		ADIIOutput.setText(""+ADIIValue);
	}

}
