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

	private void createFrame() {
		frame.setMinimumSize(new Dimension(//tamanho minimo da frame defenido como tamanho maximo de uma frame no ambiente de trabalho atual
				(int) GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getWidth(),
				(int) GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getHeight()));
		frame.setResizable(false);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	private void addFields() {// Cria painel esquerdo e direito, depois adiciona-os a frame
		buttons = new JButton[3];//3 botoes que ficarao enabled/disabled
		textFields = new JTextField[12]; //existem 12 JTextField a serem preenchidos/alterados
		frame.setContentPane(new CustomGridBag(generateEsquerdo(), generateDireito()));
		frame.setVisible(true);

	}
	private JPanel generateEsquerdo() {//Parte Esquerda da Frame
		JPanel esquerdo = new JPanel(new GridLayout(4, 1));
		JPanel[] subsEsquerdo = createSubsEsquerdo(4);
	
		// Panel Esquerdo 1
		// Panel Esquerdo 1 - Panel caixa de texto sobre o path do ficheiro	
		JPanel filePathPanel = new JPanel();
		filePath = new JTextField("Please Load An Excel file");
		filePath.setHorizontalAlignment(JTextField.CENTER);
		filePath.setEditable(false);
		filePath.setFont(new Font("Arial", Font.BOLD, 20));
		filePath.setPreferredSize(new Dimension(500, 45));
		filePathPanel.add(filePath);
		
		//Panel Esquerdo 1 - Panel com os Botoes Load e Show
		JPanel fileChoser = new JPanel();
		
		JButton loadFileButton = createButton("Load File",200,50,15, 10);//Botao Load
		loadFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {// ao ser clicado no botao para abrir um ficheiro excel
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
		createButton("Show Excel",200,50,15,0).addActionListener(new ActionListener() {//Botao Show Exel
			public void actionPerformed(ActionEvent e) {// abrir o programa excel com o ficheiro escolhido
				try {
					Desktop.getDesktop().open(new File(app.getFileName()));
				} catch (IOException e1) {
					System.out.println("Erro ao abrir ficheiro Excel");
				}
			}
		});

		fileChoser.add(loadFileButton, BorderLayout.WEST);
		fileChoser.add(buttons[0], BorderLayout.EAST);
		
		subsEsquerdo[0].add(createTitlePanel("Data File:", 45, 450, 70));//adicao do titulo ao panel esquerdo nr1
		subsEsquerdo[0].add(filePathPanel);
		subsEsquerdo[0].add(fileChoser);
		
		//Panel Esquerdo 2
		//Panel Esquerdo 2 - Panel Labels e caixas de input de text
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
				if (!(Character.isDigit(c))) {// apenas aceita numeros
					keyEvent.consume();
				}
			}
		});

		JLabel cyclomaticComplexity = new JLabel("Complexity >");
		cyclomaticComplexity.setHorizontalAlignment(JLabel.CENTER);
		cyclomaticComplexity.setBorder(border);
		cyclomaticComplexity.setFont(new Font("Arial", Font.BOLD, 20));
		cyclomaticComplexity.setPreferredSize(new Dimension(220, 50));

		cyclo = new JTextField("10");
		cyclo.setHorizontalAlignment(JTextField.CENTER);
		cyclo.setFont(new Font("Arial", Font.BOLD, 20));
		cyclo.setPreferredSize(new Dimension(100, 50));
		cyclo.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent keyEvent) {
				char c = keyEvent.getKeyChar();
				if (!(Character.isDigit(c))) {// apenas aceita numeros
					keyEvent.consume();
				}
			}
		});
		
		//Panel Esquerdo 2 - Panel botao Long Method
		JPanel longMetodPanel = new JPanel();
		createButton("Long Method",400,50,15,1).addActionListener(new ActionListener() {//Botão Long Method
			public void actionPerformed(ActionEvent e) {// correr o long method e a detecao de defeitos para esses
														// valores
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
		
		subsEsquerdo[1].add(createTitlePanel("Long Method:",40,450,70));//adicao do titulo ao panel esquerdo nr2
		subsEsquerdo[1].add(parametrosLongMethodPanel);
		subsEsquerdo[1].add(longMetodPanel);
		
		//Panel Esquerdo 3 - Panel Labels , inputs e JBoxCombo
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
			public void keyTyped(KeyEvent keyEvent) {// aceita numeros inteiros ou decimais
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
		((JLabel) optionList.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);// centrar o texto na
																							// JComboBox
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
			public void keyTyped(KeyEvent keyEvent) {// aceita numeros inteiros ou decimais
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
		
		//Panel Esquerdo 3 - Panel botao Feauture Envy
		JPanel feautureEnvyButtonPanel = new JPanel();
		createButton("Feature Envy",400,50,15,2).addActionListener(new ActionListener() {//Botão Feauture Envy
			public void actionPerformed(ActionEvent e) {// correr o feature_envy e a detecao de defeitos para esses
														// valores
				if(!atfd_txt.getText().toString().equals("") && !laa_txt.getText().toString().equals("")) {
					String choice = optionList.getSelectedItem().toString();
					app.feature_envy(Double.parseDouble(atfd_txt.getText()), choice, Double.parseDouble(laa_txt.getText()));
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
		
		subsEsquerdo[2].add(createTitlePanel("Feature Envy:",40,450,70));//adicao do titulo ao panel esquerdo nr3
		subsEsquerdo[2].add(botoesFeatureEnvyPanel);
		subsEsquerdo[2].add(feautureEnvyButtonPanel);

		//Panel Esquerdo 4
		subsEsquerdo[3].add(createTitlePanel("Quality indicators:",40,450,70));//adicao do titulo ao panel esquerdo nr4
		
		//Panel Esquerdo 4 - Panel Tabela
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

		subsEsquerdo[3].add(indicatorsTable);

		for(int i = 0;i<subsEsquerdo.length;i++)
			esquerdo.add(subsEsquerdo[i]);
		
		return esquerdo;
	}
	
	private JPanel[] createSubsEsquerdo(int n) {//Devolve um Vetor de paineis com uma border predefenida
		Border raisedbevel = BorderFactory.createRaisedBevelBorder();
		JPanel[] panels = new JPanel[n];
		for(int i=0; i<n;i++) {
			JPanel panel = new JPanel(new FlowLayout());
			panel.setBorder(raisedbevel);
			panels[i] = panel;
		}
		return panels;
	}
	
	private JPanel createTitlePanel(String string, int font, int x, int  y) {//Devolve um Painel com um titulo No meio
		JPanel panel = new JPanel();
		
		JLabel panelText = new JLabel(string, SwingConstants.CENTER);
		panelText.setFont(new Font("Arial", Font.BOLD, font));

		panel.add(panelText);
		panel.setPreferredSize(new Dimension(x, y));
		
		return panel;
	}
	
	public JButton createButton(String name,int x, int y, int font, int pos) {//Cria JButtons
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
	
	private void createAndAddTableJLabel(String name, Border border,int font, JPanel table){//Cria JLabel e adiciona a table
		JLabel label = new JLabel(name);
		if(font==11) {//definedRules é a unica label com font == 12
			definedRules = new JLabel("Defined Rules");
			label = definedRules;// a label fica a apontar para defined Rules nesse caso
		}	
		label.setHorizontalAlignment(JTextField.CENTER);
		label.setBorder(border);
		label.setFont(new Font("Arial", Font.BOLD, font));
		table.add(label);
		
	}
	
	private void createTableJTextField(int pos,Border border,JPanel indicatorsTable) {//No vetor textFiels cria uma JTextField e adiciona a table
		JTextField field = new JTextField("-");
		field.setHorizontalAlignment(JTextField.CENTER);
		field.setEditable(false);
		field.setBorder(border);
		field.setFont(new Font("Arial", Font.BOLD, 20));
		indicatorsTable.add(field);
		textFields[pos] = field;
		
	}
	
	private JPanel generateDireito() {//Parte Direita da Frame
		JPanel direito = new JPanel(new GridLayout());
		String[] col = { "MethodId", "LOC", "CYCLO", "ATFD", "LAA", "is long method", "iPlasma", "PMD",
				"is feature envy", "is long method by defined rules", "is feature envy by defined rules" };
		tableModel = new DefaultTableModel(col, 0);
		JTable table = new JTable(tableModel);
		for (int i = 0; i < 5; i++) {// tamanhos das colunas
			table.getColumn(col[i]).setMinWidth(70);
			table.getColumn(col[i]).setMaxWidth(70);
		}
		table.getColumn(col[5]).setMinWidth(100);
		table.getColumn(col[5]).setMaxWidth(100);
		table.getColumn(col[6]).setMinWidth(70);
		table.getColumn(col[6]).setMaxWidth(70);
		table.getColumn(col[7]).setMinWidth(70);
		table.getColumn(col[7]).setMaxWidth(70);
		table.getColumn(col[8]).setMinWidth(140);
		table.getColumn(col[8]).setMaxWidth(140);
		table.getColumn(col[9]).setMinWidth(200);
		table.getColumn(col[9]).setMaxWidth(240);
		table.getColumn(col[10]).setMinWidth(205);
		table.getColumn(col[10]).setMaxWidth(205);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);

		for (int i = 0; i < col.length; i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}

		table.setEnabled(false);

		direito.add(new JScrollPane(table));
		
		return direito;
	}

	public void fillTable() {// preencher a tabela de acordo com o ficheiro
		tableModel.setRowCount(0);
		for (MethodData m : app.getMethodsData()) {
			Object[] aux = { m.getMethodId(), m.getLoc(), m.getCyclo(), m.getAtfd(), m.getLaa(), m.getIs_long_method(),
					m.getIplasma(), m.getPmd(), m.getIs_feature_envy() };
			tableModel.addRow(aux);
		}
	}

	private void fillLongMethod() {// preencher a coluna do is Long method
		for (MethodData m : app.getMethodsData()) {
			tableModel.setValueAt(m.getIs_long_method_by_rules(), m.getMethodId() - 1, 9);
		}
	}

	private void fillFeature_envy() {// preencher a coluna do is feature envy
		for (MethodData m : app.getMethodsData()) {
			tableModel.setValueAt(m.getIs_feature_envy_by_rules(), m.getMethodId() - 1, 10);
		}
	}

	public void receiveOutputDefectDetection(int[] countersIPlasma, int[] countersPmd) {// receber output da detecao de
																						// defeitos
		textFields[0].setText("" + countersIPlasma[0]);
		textFields[1].setText("" + countersIPlasma[1]);
		textFields[2].setText("" + countersIPlasma[2]);
		textFields[3].setText("" + countersIPlasma[3]);

		textFields[4].setText("" + countersPmd[0]);
		textFields[5].setText("" + countersPmd[1]);
		textFields[6].setText("" + countersPmd[2]);
		textFields[7].setText("" + countersPmd[3]);
	}

	public void receiveOutputDefectDetectionDefinedRules(String origem, int[] counters) {// receber e colocar na GUI o
																							// resultado das regras definidas
		definedRules.setText(origem);

		textFields[8].setText("" + counters[0]);
		textFields[9].setText("" + counters[1]);
		textFields[10].setText("" + counters[2]);
		textFields[11].setText("" + counters[3]);
	}

	public void clearDefectDetectionTable() {// quando é carregado um novo ficheiro tiram-se os resultados anteriores
		textFields[0].setText("-");
		textFields[1].setText("-");
		textFields[2].setText("-");
		textFields[3].setText("-");

		textFields[4].setText("-");
		textFields[5].setText("-");
		textFields[6].setText("-");
		textFields[7].setText("-");

		textFields[8].setText("-");
		textFields[9].setText("-");
		textFields[10].setText("-");
		textFields[11].setText("-");

		definedRules.setText("Defined Rules");
	}
	
}