package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import application.Application;
import application.MethodData;

/**
 * @author ESI-2019-EIC2-02
 *
 */
public class GUI {

	private Application app;// objecto aplicacao
	private JFrame frame;
	private JTextField filePath;// textField do nome do ficheiro
	private JButton[] buttons;
	private JTextField loc;// textField do numero de linhas de codigo
	private JTextField cyclo;// textField da complexidade cyclomatica
	private JTextField laa_txt;// textField para introduzir um valor de laa
	private JTextField atfd_txt;// textField para introduzir um valor para atfd
	private JComboBox<String> optionList;// ComboBox para and e or
	private JTextField [] textFields;//Vetor que contem varias "caixas" de JTextField
	private DefaultTableModel tableModel;// tabela da interface gráfica com dados do excel e regras definidas
	private JLabel definedRules;// Label para detecao de defeitos das regras definidas

	public GUI(Application app) {
		this.app = app;
		frame = new JFrame("Aplicação");
		createFrame();
		addFields();
	}

	/**
	 *  Creates the frame, requiring a minimum size defined as the maximum
	 *  size of the frame of the actual workspace.
	 */
	private void createFrame() {
		frame.setMinimumSize(new Dimension((int) GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getWidth(),
				(int) GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getHeight()));
		frame.setResizable(false);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	/**
	 * Creates the left and right panel, adding them to the frame.
	 * It also creates an array of buttons that will be enabled or disabled and 
	 * JTextFields that could be altered in the future by the user.
	 */
	private void addFields() {
		buttons = new JButton[3];
		textFields = new JTextField[12];
		frame.setContentPane(new CustomGridBag(generateLeft(), generateDireito()));
		frame.setVisible(true);

	}
	/**
	 * Creates the left part of the Frame, including the fileChoser panel, 
	 * parametrosLongMethodPanel panel, botoesFeatureEnvyPanel panel,
	 * feautureEnvyButtonPanel panel and indicatorsTable panel with all the
	 * labels, text field boxes, buttons and option panes associated to them.
	 * In the fileChoser panel, when the load button is selected it opens
	 * an Excel file, while when the show button is selected it
	 * opens the Excel program with the selected file.
	 * In the parametrosLongMethodPanel panel, the JLabel linesOfCode as well
	 * as the JLabel cyclomaticComplexity only accept numbers in its argument.
	 * The JPanel longMetodPanel creates the button long method which when
	 * pressed, will execute the long method and the defect detection to 
	 * the values previously defined.
	 * The JPanel botoesFeatureEnvyPanel creates the JLabel atfd label and
	 * the JLabel laa label and posteriorly changes the JTextField laa_txt and
	 * JTextField atfd_txt, accepting integer or double numbers.
	 * In The JPanel feautureEnvyButtonPanel the button Feature Envy is
	 * created which will execute the feature envy and the defect detection
	 * for the values defined by the user.
	 * The JPanel indicatorsTable creates and adds the several tables to the frame.
	 * 
	 * @return JPanel with the left part of the Frame.
	 */
	private JPanel generateLeft() {
		JPanel left = new JPanel(new GridLayout(4, 1));
		JPanel[] subsLeft = createSubLeft(4);
	
		JPanel filePathPanel = new JPanel();
		filePath = new JTextField("Please Load An Excel file");
		filePath.setHorizontalAlignment(JTextField.CENTER);
		filePath.setEditable(false);
		filePath.setFont(new Font("Arial", Font.BOLD, 20));
		filePath.setPreferredSize(new Dimension(700, 45));
		filePathPanel.add(filePath);
		
		JPanel fileChoser = new JPanel();
		
		JButton loadFileButton = createButton("Load File",200,50,15, 10);
		loadFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
					if (chooser.getSelectedFile().getAbsolutePath().endsWith(".xlsx")
							|| chooser.getSelectedFile().getAbsolutePath().endsWith(".xls")) {
						app.setPath(chooser.getSelectedFile().getAbsolutePath());
						filePath.setText(chooser.getSelectedFile().getName());
						for(int i=0;i<buttons.length;i++)
							buttons[i].setEnabled(true);
						clearDefectDetectionTable();
						app.loadFile();
						fillTable();
						app.defectDetection();
					} else {
						JOptionPane.showMessageDialog(null, "Escolha um ficheiro excel", "Erro ao abrir ficheiro",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});
		createButton("Show Excel",200,50,15,0).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Desktop.getDesktop().open(new File(app.getFileName()));
				} catch (IOException e1) {
					System.out.println("Erro ao abrir ficheiro Excel");
				}
			}
		});

		fileChoser.add(loadFileButton, BorderLayout.WEST);
		fileChoser.add(buttons[0], BorderLayout.EAST);
		
		subsLeft[0].add(createTitlePanel("Data File:", 45, 450, 70));
		subsLeft[0].add(filePathPanel);
		subsLeft[0].add(fileChoser);
		
		JPanel parametrosLongMethodPanel = new JPanel();

		Border border = BorderFactory.createLineBorder(Color.BLUE, 1);

		JLabel linesOfCode = new JLabel("Lines of code >");
		linesOfCode.setHorizontalAlignment(JLabel.CENTER);
		linesOfCode.setBorder(border);
		linesOfCode.setFont(new Font("Arial", Font.BOLD, 20));
		linesOfCode.setPreferredSize(new Dimension(220, 50));

		loc = new JTextField("80");
		loc.setHorizontalAlignment(JTextField.CENTER);
		loc.setFont(new Font("Arial", Font.BOLD, 20));
		loc.setPreferredSize(new Dimension(100, 50));
		loc.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent keyEvent) {
				char c = keyEvent.getKeyChar();
				if (!(Character.isDigit(c))) {
					keyEvent.consume();
				}
			}
		});

		JLabel cyclomaticComplexity = new JLabel("Cyclomatic Complexity >");
		cyclomaticComplexity.setHorizontalAlignment(JLabel.CENTER);
		cyclomaticComplexity.setBorder(border);
		cyclomaticComplexity.setFont(new Font("Arial", Font.BOLD, 20));
		cyclomaticComplexity.setPreferredSize(new Dimension(240, 50));

		cyclo = new JTextField("10");
		cyclo.setHorizontalAlignment(JTextField.CENTER);
		cyclo.setFont(new Font("Arial", Font.BOLD, 20));
		cyclo.setPreferredSize(new Dimension(100, 50));
		cyclo.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent keyEvent) {
				char c = keyEvent.getKeyChar();
				if (!(Character.isDigit(c))) {
					keyEvent.consume();
				}
			}
		});
		
		JPanel longMetodPanel = new JPanel();
		createButton("Long Method",400,50,15,1).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!loc.getText().toString().equals("") && !cyclo.getText().toString().equals("")) {
					int locValue = Integer.parseInt(loc.getText());
					int cycloValue = Integer.parseInt(cyclo.getText());
					app.longMethod(locValue, cycloValue);
					fillLongMethod();
					app.defectDetectionDefinedRules(0);
				}
				else {
					JOptionPane.showMessageDialog(null,"Introduza um input no quadro Long Method!","Input invalido",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		parametrosLongMethodPanel.add(linesOfCode);
		parametrosLongMethodPanel.add(loc);
		parametrosLongMethodPanel.add(cyclomaticComplexity);
		parametrosLongMethodPanel.add(cyclo);
		longMetodPanel.add(buttons[1]);
		
		subsLeft[1].add(createTitlePanel("Long Method:",40,450,70));
		subsLeft[1].add(parametrosLongMethodPanel);
		subsLeft[1].add(longMetodPanel);
		
		JPanel botoesFeatureEnvyPanel = new JPanel();

		JLabel atfd = new JLabel("ATFD >");
		atfd.setHorizontalAlignment(JLabel.CENTER);
		atfd.setBorder(border);
		atfd.setPreferredSize(new Dimension(100, 50));
		atfd.setFont(new Font("Arial", Font.BOLD, 20));

		atfd_txt = new JTextField("4");
		atfd_txt.setHorizontalAlignment(JTextField.CENTER);
		atfd_txt.setPreferredSize(new Dimension(100, 50));
		atfd_txt.setFont(new Font("Arial", Font.BOLD, 20));
		atfd_txt.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent keyEvent) {
				char c = keyEvent.getKeyChar();
				boolean exit = false;
				if (c == KeyEvent.VK_PERIOD && atfd_txt.getText().contains(".")) {
					keyEvent.consume();
					exit = true;
				}
				if (c == KeyEvent.VK_PERIOD && !atfd_txt.getText().contains("."))
					exit = true;
				if (!(Character.isDigit(c)) && !exit)
					keyEvent.consume();
			}
		});

		String[] option = { "and", "or" };
		optionList = new JComboBox<String>(option);
		optionList.setSelectedIndex(1);
		optionList.setPreferredSize(new Dimension(100, 50));
		optionList.setFont(new Font("Arial", Font.BOLD, 17));
		((JLabel) optionList.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel laa = new JLabel("LAA <");
		laa.setHorizontalAlignment(JLabel.CENTER);
		laa.setBorder(border);
		laa.setPreferredSize(new Dimension(100, 50));
		laa.setFont(new Font("Arial", Font.BOLD, 20));

		laa_txt = new JTextField("0.42");
		laa_txt.setHorizontalAlignment(JTextField.CENTER);
		laa_txt.setPreferredSize(new Dimension(100, 50));
		laa_txt.setFont(new Font("Arial", Font.BOLD, 20));
		laa_txt.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent keyEvent) {
				char c = keyEvent.getKeyChar();
				boolean exit = false;
				if (c == KeyEvent.VK_PERIOD && laa_txt.getText().contains(".")) {
					keyEvent.consume();
					exit = true;
				}
				if (c == KeyEvent.VK_PERIOD && !laa_txt.getText().contains("."))
					exit = true;
				if (!(Character.isDigit(c)) && !exit)
					keyEvent.consume();
			}
		});
		
		JPanel feautureEnvyButtonPanel = new JPanel();
		createButton("Feature Envy",400,50,15,2).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!atfd_txt.getText().toString().equals("") && !laa_txt.getText().toString().equals("")) {
					String choice = optionList.getSelectedItem().toString();
					app.feature_envy(Double.parseDouble(atfd_txt.getText()), Double.parseDouble(laa_txt.getText()), choice);
					fillFeature_envy();
					app.defectDetectionDefinedRules(1);
				}
				else {
					JOptionPane.showMessageDialog(null,"Introduza um input no quadro Feature Envy!","Input invalido",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		botoesFeatureEnvyPanel.add(atfd);
		botoesFeatureEnvyPanel.add(atfd_txt);
		botoesFeatureEnvyPanel.add(optionList);
		botoesFeatureEnvyPanel.add(laa);
		botoesFeatureEnvyPanel.add(laa_txt);
		feautureEnvyButtonPanel.add(buttons[2]);
		
		subsLeft[2].add(createTitlePanel("Feature Envy:",40,450,70));
		subsLeft[2].add(botoesFeatureEnvyPanel);
		subsLeft[2].add(feautureEnvyButtonPanel);

		subsLeft[3].add(createTitlePanel("Quality indicators:",40,450,70));
		
		JPanel indicatorsTable = new JPanel(new GridLayout(0, 5));
		indicatorsTable.setPreferredSize(new Dimension(760, 155));

		createAndAddTableJLabel("",border,20,indicatorsTable);
		createAndAddTableJLabel("DCI",border,20,indicatorsTable);
		createAndAddTableJLabel("DII",border,20,indicatorsTable);
		createAndAddTableJLabel("ADCI",border,20,indicatorsTable);
		createAndAddTableJLabel("ADII",border,20,indicatorsTable);
		
		createAndAddTableJLabel("IPlasma",border,13,indicatorsTable);

		for(int i = 0; i<4;i++)
			createTableJTextField(i,border,indicatorsTable);

		createAndAddTableJLabel("PMD",border,13,indicatorsTable);
		
		for(int i = 4; i<8;i++)
			createTableJTextField(i,border,indicatorsTable);

		createAndAddTableJLabel("Defined Rules",border,11,indicatorsTable);
		
		for(int i = 8; i<12;i++)
			createTableJTextField(i,border,indicatorsTable);

		subsLeft[3].add(indicatorsTable);

		for(int i = 0;i<subsLeft.length;i++)
			left.add(subsLeft[i]);
		
		return left;
	}
	
	
	/**
	 * Array of panels with a preferred border.
	 * @param n size of the JPanel panels array.
	 * @return panels array.
	 */
	private JPanel[] createSubLeft(int n) {
		Border raisedbevel = BorderFactory.createRaisedBevelBorder();
		JPanel[] panels = new JPanel[n];
		for(int i=0; i<n;i++) {
			JPanel panel = new JPanel(new FlowLayout());
			panel.setBorder(raisedbevel);
			panels[i] = panel;
		}
		return panels;
	}
	
	/**
	 * Panel with a title in the middle with a set Text positioning 
	 * @param string for the title panel
	 * @param font for the title panel
	 * @param x dimension
	 * @param y dimension
	 * @return JPanel with the Title Panel with a title in the middle
	 */
	private JPanel createTitlePanel(String string, int font, int x, int  y) {
		JPanel panel = new JPanel();
		
		JLabel panelText = new JLabel(string, SwingConstants.CENTER);
		panelText.setFont(new Font("Arial", Font.BOLD, font));

		panel.add(panelText);
		panel.setPreferredSize(new Dimension(x, y));
		
		return panel;
	}
	
	/**
	 * Creates a button with the title, dimension and font options.
	 * @param name String name for the button.
	 * @param x dimension.
	 * @param y dimension.
	 * @param font letters style for the button Text.
	 * @param pos iterator for the buttons array.
	 * @return JButton.
	 */
	public JButton createButton(String name,int x, int y, int font, int pos) {
		JButton button = new JButton(name);
		button.setHorizontalAlignment(JButton.CENTER);
		button.setPreferredSize(new Dimension(x, y));
		button.setFont(new Font("Arial", Font.BOLD, font));
		if(pos<buttons.length) {
			buttons[pos] = button;
			button.setEnabled(false);
		}
		return button;
	}
	
	/**
	 * Creates and adds a table to a determined panel. The definedRules
	 * is the only label in which the font is equal to eleven.
	 * If so the label points to the definedRules. 
	 * @param name for the new label.
	 * @param border size.
	 * @param font letters style for the table JLabel.
	 * @param table to be added to the panel.
	 */
	private void createAndAddTableJLabel(String name, Border border,int font, JPanel table){
		JLabel label = new JLabel(name);
		if(font==11) {
			definedRules = new JLabel("Defined Rules");
			label = definedRules;
		}	
		label.setHorizontalAlignment(JTextField.CENTER);
		label.setBorder(border);
		label.setFont(new Font("Arial", Font.BOLD, font));
		table.add(label);
		
	}
	
	/**
	 * In the array textFields it is created a JTextField
	 * and adds the corresponding table to it.
	 * @param pos iterator
	 * @param border size.
	 * @param indicatorsTable
	 */
	private void createTableJTextField(int pos,Border border,JPanel indicatorsTable) {//No vetor textFiels cria uma JTextField e adiciona a table
		JTextField field = new JTextField("-");
		field.setHorizontalAlignment(JTextField.CENTER);
		field.setEditable(false);
		field.setBorder(border);
		field.setFont(new Font("Arial", Font.BOLD, 20));
		indicatorsTable.add(field);
		textFields[pos] = field;
		
	}
	
	/**
	 * Right part of the frame. It is create a String array with all the 
	 * names for the column and then adds it to the frame, posteriorly
	 * setting the columns sizes, and its corresponding maximum and minimum widths.
	 * 
	 * @return JPanel with the right part of the frame.
	 */
	private JPanel generateDireito() {
		JPanel direito = new JPanel(new GridLayout());
		String[] col = { "MethodId", "LOC", "CYCLO", "ATFD", "LAA", "is long method", "iPlasma", "PMD",
				"is feature envy", "is long method by defined rules", "is feature envy by defined rules" };
		tableModel = new DefaultTableModel(col, 0);
		JTable table = new JTable(tableModel);
		for (int i = 0; i < 5; i++) {
			table.getColumn(col[i]).setMinWidth(70);
			table.getColumn(col[i]).setMaxWidth(70);
		}
		table.getColumn(col[5]).setMinWidth(100);
		table.getColumn(col[5]).setMaxWidth(100);
		table.getColumn(col[6]).setMinWidth(65);
		table.getColumn(col[6]).setMaxWidth(65);
		table.getColumn(col[7]).setMinWidth(65);
		table.getColumn(col[7]).setMaxWidth(65);
		table.getColumn(col[8]).setMinWidth(100);
		table.getColumn(col[8]).setMaxWidth(100);
		table.getColumn(col[9]).setMinWidth(180);
		table.getColumn(col[9]).setMaxWidth(180);
		table.getColumn(col[10]).setMinWidth(195);
		table.getColumn(col[10]).setMaxWidth(195);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);

		for (int i = 0; i < col.length; i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}

		table.setEnabled(false);

		direito.add(new JScrollPane(table));
		
		return direito;
	}

	/**
	 * Firstly, sets the row count to 0 and after it fills the table
	 * accordingly, with the file uploaded.
	 */
	public void fillTable() {
		tableModel.setRowCount(0);
		for (MethodData m : app.getMethodsData()) {
			Object[] aux = { m.getMethodId(), m.getLoc(), m.getCyclo(), m.getAtfd(), m.getLaa(), m.getIs_long_method(),
					m.getIplasma(), m.getPmd(), m.getIs_feature_envy() };
			tableModel.addRow(aux);
		}
	}

	/**
	 * Fills the is long method column.
	 */
	private void fillLongMethod() {
		for (MethodData m : app.getMethodsData()) {
			tableModel.setValueAt(m.getIs_long_method_by_rules(), m.getMethodId() - 1, 9);
		}
	}

	/**
	 * Fills the column of the is feature envy by iterating the
	 * getsMethodsData and posteriorly setting the corresponding values.
	 */
	private void fillFeature_envy() {
		for (MethodData m : app.getMethodsData()) {
			tableModel.setValueAt(m.getIs_feature_envy_by_rules(), m.getMethodId() - 1, 10);
		}
	}

	/**
	 * Receives and updates the outputs from the defect detection.
	 * @param countersIPlasma array of integers.
	 * @param countersPmd array of integers.
	 */
	public void receiveOutputDefectDetection(int[] countersIPlasma, int[] countersPmd) {
		textFields[0].setText("" + countersIPlasma[0]);
		textFields[1].setText("" + countersIPlasma[1]);
		textFields[2].setText("" + countersIPlasma[2]);
		textFields[3].setText("" + countersIPlasma[3]);

		textFields[4].setText("" + countersPmd[0]);
		textFields[5].setText("" + countersPmd[1]);
		textFields[6].setText("" + countersPmd[2]);
		textFields[7].setText("" + countersPmd[3]);
	}

	/**
	 * Receives and places on the Graphical User Interface (GUI)
	 * the results from the rules defined.
	 * @param origin String to setText.
	 * @param counters array of integers.
	 */
	public void receiveOutputDefectDetectionDefinedRules(String origin, int[] counters) {
		definedRules.setText(origin);

		textFields[8].setText("" + counters[0]);
		textFields[9].setText("" + counters[1]);
		textFields[10].setText("" + counters[2]);
		textFields[11].setText("" + counters[3]);
	}

	/**
	 * When its uploaded a new file the previous results are
	 * cleared from their textFields.
	 */
	public void clearDefectDetectionTable() {
		for(int i = 0; i != 12; i++) {
			textFields[i].setText("-");
		}
		definedRules.setText("Defined Rules");
	}
	
}