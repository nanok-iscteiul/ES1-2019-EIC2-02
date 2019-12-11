package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Toolkit;
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
	private JTextField loc;// textField do numero de linhas de codigo
	private JTextField cyclo;// textField da complexidade cyclomatica
	private JButton longMethod;// botao para lancar o long method
	private JComboBox<String> optionList;// ComboBox para and e or
	private JTextField laa_txt;// textField para introduzir um valor de laa
	private JTextField atfd_txt;// textField para introduzir um valor para atfd
	private JButton featureEnvyButton;// botao para lancar o feature_envy
	private JTextField dciIplasma;// contador dci para detecao de defeitos iplasma
	private JTextField diiIplasma;// contador dii para detecao de defeitos iplasma
	private JTextField adciIplasma;// contador adci para detecao de defeitos iplasma
	private JTextField adiiIplasma;// contador adii para detecao de defeitos iplasma
	private JTextField dciPmd;// contador dci para detecao de defeitos pmd
	private JTextField diiPmd;// contador dii para detecao de defeitos pmd
	private JTextField adciPmd;// contador adci para detecao de defeitos pmd
	private JTextField adiiPmd;// contador adii para detecao de defeitos pmd
	private JTextField dciDefinedRules;// contador dci para detecao de defeitos das regras definidas
	private JTextField diiDefinedRules;// contador dii para detecao de defeitos das regras definidas
	private JTextField adciDefinedRules;// contador adci para detecao de defeitos das regras definidas
	private JTextField adiiDefinedRules;// contador adii para detecao de defeitos das regras definidas
	private JButton showFileButton;// botao para lancar o programa excel com o ficheiro escolhido
	private DefaultTableModel tableModel;// tabela da interface gráfica com dados do excel e regras definidas
	private JLabel definedRules;// Label para detecao de defeitos das regras definidas

	public GUI(Application app) {
		this.app = app;
		frame = new JFrame("Aplicação");
		createFrame();
		addFields();
		frame.setVisible(true);
		frame.setResizable(false);
	}

	private void createFrame() {
		frame.setMinimumSize(new Dimension(
				(int) GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getWidth(),
				(int) GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getHeight()));
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	private void addFields() {// adiciona os paineis, butoes, labels
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		Border raisedbevel = BorderFactory.createRaisedBevelBorder();

		// Painel esquerdo
		final JPanel esquerdo = new JPanel(new GridLayout(4, 1));
		esquerdo.setPreferredSize(new Dimension((int) dimension.getWidth() * (1 / 3), (int) dimension.getHeight()));
		// painel esquerdo 1
		JPanel esquerdo1 = new JPanel(new FlowLayout());
		esquerdo1.setBorder(raisedbevel);
		// painel dataFile
		JPanel dataFile = new JPanel();
		JLabel dFText = new JLabel("Data File:", SwingConstants.CENTER);
		dFText.setFont(new Font("Arial", Font.BOLD, 45));

		dataFile.add(dFText);
		dataFile.setPreferredSize(new Dimension(450, 70));
		// panel pathficheiro

		JPanel filePathPanel = new JPanel();
		filePath = new JTextField("Please Load An Excel file");
		filePath.setHorizontalAlignment(JTextField.CENTER);
		filePath.setEditable(false);
		filePath.setFont(new Font("Arial", Font.BOLD, 20));
		filePath.setPreferredSize(new Dimension(500, 45));

		filePathPanel.add(filePath);
		// painel load/show
		JPanel fileChoser = new JPanel();

		JButton loadFileButton = new JButton("Load File");
		loadFileButton.setHorizontalAlignment(JButton.CENTER);
		loadFileButton.setPreferredSize(new Dimension(200, 50));
		loadFileButton.setFont(new Font("Arial", Font.BOLD, 15));
		loadFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {// ao ser clicado no botao para abrir um ficheiro excel
				JFileChooser chooser = new JFileChooser();
				if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
					if (chooser.getSelectedFile().getAbsolutePath().endsWith(".xlsx")
							|| chooser.getSelectedFile().getAbsolutePath().endsWith(".xls")) {
						app.setPath(chooser.getSelectedFile().getAbsolutePath());
						filePath.setText(chooser.getSelectedFile().getName());
						featureEnvyButton.setEnabled(true);
						longMethod.setEnabled(true);
						showFileButton.setEnabled(true);
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
		showFileButton = new JButton("Show Excel");
		showFileButton.setEnabled(false);
		showFileButton.setHorizontalAlignment(JButton.CENTER);
		showFileButton.setPreferredSize(new Dimension(200, 50));
		showFileButton.setFont(new Font("Arial", Font.BOLD, 15));
		showFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {// abrir o programa excel com o ficheiro escolhido
				try {
					Desktop.getDesktop().open(new File(app.getFileName()));
				} catch (IOException e1) {
					System.out.println("Erro ao abrir ficheiro Excel");
				}
			}
		});

		fileChoser.add(loadFileButton, BorderLayout.WEST);
		fileChoser.add(showFileButton, BorderLayout.EAST);
		fileChoser.setPreferredSize(new Dimension(450, 60));

		esquerdo1.add(dataFile);
		esquerdo1.add(filePathPanel);
		esquerdo1.add(fileChoser);
		// painel esquerdo 2
		JPanel esquerdo2 = new JPanel(new FlowLayout());
		esquerdo2.setBorder(raisedbevel);

		// Label longmethod
		JPanel longTextPanel = new JPanel();
		JLabel longText = new JLabel("Long Method:", SwingConstants.CENTER);
		longText.setFont(new Font("Arial", Font.BOLD, 40));

		longTextPanel.add(longText);
		longTextPanel.setPreferredSize(new Dimension(450, 70));
		// painel botoesLongMethod
		JPanel parametrosLongMethodPanel = new JPanel();

		Border border = BorderFactory.createLineBorder(Color.BLUE, 1);

		JLabel linesOfCode = new JLabel("Lines of code");
		linesOfCode.setHorizontalAlignment(JLabel.CENTER);
		linesOfCode.setBorder(border);
		linesOfCode.setFont(new Font("Arial", Font.BOLD, 20));
		linesOfCode.setPreferredSize(new Dimension(200, 50));
		parametrosLongMethodPanel.add(linesOfCode);

		loc = new JTextField("80");
		loc.setHorizontalAlignment(JTextField.CENTER);
		loc.setFont(new Font("Arial", Font.BOLD, 20));
		loc.setPreferredSize(new Dimension(100, 50));
		parametrosLongMethodPanel.add(loc);
		loc.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent keyEvent) {
				char c = keyEvent.getKeyChar();
				if (!(Character.isDigit(c))) {// apenas aceita numeros
					keyEvent.consume();
				}
			}
		});

		JLabel cyclomaticComplexity = new JLabel("Cyclomatic Complexity");
		cyclomaticComplexity.setHorizontalAlignment(JLabel.CENTER);
		cyclomaticComplexity.setBorder(border);
		cyclomaticComplexity.setFont(new Font("Arial", Font.BOLD, 17));
		cyclomaticComplexity.setPreferredSize(new Dimension(200, 50));
		parametrosLongMethodPanel.add(cyclomaticComplexity);

		cyclo = new JTextField("10");
		cyclo.setHorizontalAlignment(JTextField.CENTER);
		cyclo.setFont(new Font("Arial", Font.BOLD, 20));
		cyclo.setPreferredSize(new Dimension(100, 50));
		parametrosLongMethodPanel.add(cyclo);
		cyclo.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent keyEvent) {
				char c = keyEvent.getKeyChar();
				if (!(Character.isDigit(c))) {// apenas aceita numeros
					keyEvent.consume();
				}
			}
		});

		JPanel longMetodPanel = new JPanel();
		longMethod = new JButton("Long Method");
		longMethod.setPreferredSize(new Dimension(400, 50));
		longMethod.setFont(new Font("Arial", Font.BOLD, 15));
		longMethod.setEnabled(false);
		longMetodPanel.add(longMethod);
		longMethod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {// correr o long method e a detecao de defeitos para esses
														// valores
				if (loc.getText() != "" && cyclo.getText() != "") {
					int locValue = Integer.parseInt(loc.getText());
					int cycloValue = Integer.parseInt(cyclo.getText());
					app.longMethod(locValue, cycloValue);
					fillLongMethod();
					app.defectDetectionDefinedRules(0);
				}
			}
		});
		esquerdo2.add(longTextPanel);
		esquerdo2.add(parametrosLongMethodPanel);
		esquerdo2.add(longMetodPanel);
		// fim painel esquerdo 2

		// painel esquerdo 3

		JPanel esquerdo3 = new JPanel();
		esquerdo3.setBorder(raisedbevel);

		JPanel featureEnvyPanel = new JPanel();
		JLabel featureEnvyLabel = new JLabel("Feature Envy:");
		featureEnvyLabel.setHorizontalAlignment(JTextField.CENTER);
		featureEnvyLabel.setFont(new Font("Arial", Font.BOLD, 40));
		featureEnvyPanel.add(featureEnvyLabel);
		featureEnvyPanel.setPreferredSize(new Dimension(450, 70));

		JPanel botoesFeatureEnvyPanel = new JPanel();

		JLabel atfd = new JLabel("ATFD >");
		atfd.setHorizontalAlignment(JLabel.CENTER);
		atfd.setBorder(border);
		atfd.setPreferredSize(new Dimension(100, 50));
		atfd.setFont(new Font("Arial", Font.BOLD, 20));
		botoesFeatureEnvyPanel.add(atfd);

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
		botoesFeatureEnvyPanel.add(atfd_txt);

		String[] option = { "and", "or" };
		optionList = new JComboBox<String>(option);
		optionList.setSelectedIndex(1);
		optionList.setPreferredSize(new Dimension(100, 50));
		optionList.setFont(new Font("Arial", Font.BOLD, 17));
		((JLabel) optionList.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);// centrar o texto na
																							// JComboBox
		botoesFeatureEnvyPanel.add(optionList);

		JLabel laa = new JLabel("LAA <");
		laa.setHorizontalAlignment(JLabel.CENTER);
		laa.setBorder(border);
		laa.setPreferredSize(new Dimension(100, 50));
		laa.setFont(new Font("Arial", Font.BOLD, 20));
		botoesFeatureEnvyPanel.add(laa);

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
		botoesFeatureEnvyPanel.add(laa_txt);

		JPanel feautureEnvyButtonPanel = new JPanel();
		featureEnvyButton = new JButton("Feature Envy");
		featureEnvyButton.setHorizontalAlignment(JButton.CENTER);
		featureEnvyButton.setPreferredSize(new Dimension(400, 50));
		featureEnvyButton.setFont(new Font("Arial", Font.BOLD, 15));
		featureEnvyButton.setEnabled(false);
		feautureEnvyButtonPanel.add(featureEnvyButton);
		featureEnvyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {// correr o feature_envy e a detecao de defeitos para esses
														// valores
				String choice = optionList.getSelectedItem().toString();
				app.feature_envy(Double.parseDouble(atfd_txt.getText()), choice, Double.parseDouble(laa_txt.getText()));
				fillFeature_envy();
				app.defectDetectionDefinedRules(1);

			}
		});

		esquerdo3.add(featureEnvyPanel);
		esquerdo3.add(botoesFeatureEnvyPanel);
		esquerdo3.add(feautureEnvyButtonPanel);

		// painel esquerdo 4
		JPanel esquerdo4 = new JPanel();
		esquerdo4.setBorder(raisedbevel);

		JPanel qualityIndicatorPanel = new JPanel(new GridLayout(1, 2));
		JLabel qualityIndicatorLabel = new JLabel("Quality indicators:", SwingConstants.CENTER);
		qualityIndicatorLabel.setFont(new Font("Arial", Font.BOLD, 40));
		qualityIndicatorPanel.add(qualityIndicatorLabel);
		qualityIndicatorPanel.setPreferredSize(new Dimension(450, 70));

		JPanel indicatorsTable = new JPanel(new GridLayout(0, 5));
		indicatorsTable.setPreferredSize(new Dimension(760, 155));

		JLabel blank = new JLabel("");
		blank.setHorizontalAlignment(JTextField.CENTER);
		blank.setBorder(border);
		indicatorsTable.add(blank);

		JLabel dci = new JLabel("DCI");
		dci.setHorizontalAlignment(JTextField.CENTER);
		dci.setBorder(border);
		dci.setFont(new Font("Arial", Font.BOLD, 20));
		indicatorsTable.add(dci);
		JLabel dii = new JLabel("DII");
		dii.setHorizontalAlignment(JTextField.CENTER);
		dii.setBorder(border);
		dii.setFont(new Font("Arial", Font.BOLD, 20));
		indicatorsTable.add(dii);
		JLabel adci = new JLabel("ADCI");
		adci.setHorizontalAlignment(JTextField.CENTER);
		adci.setBorder(border);
		adci.setFont(new Font("Arial", Font.BOLD, 20));
		indicatorsTable.add(adci);
		JLabel adii = new JLabel("ADII");
		adii.setHorizontalAlignment(JTextField.CENTER);
		adii.setBorder(border);
		adii.setFont(new Font("Arial", Font.BOLD, 20));
		indicatorsTable.add(adii);
		JLabel iplasma = new JLabel("IPlasma");
		iplasma.setHorizontalAlignment(JTextField.CENTER);
		iplasma.setBorder(border);
		iplasma.setFont(new Font("Arial", Font.BOLD, 15));
		indicatorsTable.add(iplasma);

		dciIplasma = new JTextField("-");
		dciIplasma.setHorizontalAlignment(JTextField.CENTER);
		dciIplasma.setEditable(false);
		dciIplasma.setBorder(border);
		dciIplasma.setFont(new Font("Arial", Font.BOLD, 20));
		indicatorsTable.add(dciIplasma);

		diiIplasma = new JTextField("-");
		diiIplasma.setHorizontalAlignment(JTextField.CENTER);
		diiIplasma.setEditable(false);
		diiIplasma.setBorder(border);
		diiIplasma.setFont(new Font("Arial", Font.BOLD, 20));
		indicatorsTable.add(diiIplasma);

		adciIplasma = new JTextField("-");
		adciIplasma.setHorizontalAlignment(JTextField.CENTER);
		adciIplasma.setEditable(false);
		adciIplasma.setBorder(border);
		adciIplasma.setFont(new Font("Arial", Font.BOLD, 20));
		indicatorsTable.add(adciIplasma);

		adiiIplasma = new JTextField("-");
		adiiIplasma.setHorizontalAlignment(JTextField.CENTER);
		adiiIplasma.setEditable(false);
		adiiIplasma.setBorder(border);
		adiiIplasma.setFont(new Font("Arial", Font.BOLD, 20));
		indicatorsTable.add(adiiIplasma);

		JLabel pmd = new JLabel("PMD");
		pmd.setHorizontalAlignment(JTextField.CENTER);
		pmd.setBorder(border);
		pmd.setFont(new Font("Arial", Font.BOLD, 15));
		indicatorsTable.add(pmd);

		dciPmd = new JTextField("-");
		dciPmd.setHorizontalAlignment(JTextField.CENTER);
		dciPmd.setEditable(false);
		dciPmd.setBorder(border);
		dciPmd.setFont(new Font("Arial", Font.BOLD, 20));
		indicatorsTable.add(dciPmd);

		diiPmd = new JTextField("-");
		diiPmd.setHorizontalAlignment(JTextField.CENTER);
		diiPmd.setEditable(false);
		diiPmd.setBorder(border);
		diiPmd.setFont(new Font("Arial", Font.BOLD, 20));
		indicatorsTable.add(diiPmd);

		adciPmd = new JTextField("-");
		adciPmd.setHorizontalAlignment(JTextField.CENTER);
		adciPmd.setEditable(false);
		adciPmd.setBorder(border);
		adciPmd.setFont(new Font("Arial", Font.BOLD, 20));
		indicatorsTable.add(adciPmd);

		adiiPmd = new JTextField("-");
		adiiPmd.setHorizontalAlignment(JTextField.CENTER);
		adiiPmd.setEditable(false);
		adiiPmd.setBorder(border);
		adiiPmd.setFont(new Font("Arial", Font.BOLD, 20));
		indicatorsTable.add(adiiPmd);

		definedRules = new JLabel("Defined Rules");
		definedRules.setHorizontalAlignment(JTextField.CENTER);
		definedRules.setBorder(border);
		definedRules.setFont(new Font("Arial", Font.BOLD, 12));
		indicatorsTable.add(definedRules);

		dciDefinedRules = new JTextField("-");
		dciDefinedRules.setHorizontalAlignment(JTextField.CENTER);
		dciDefinedRules.setEditable(false);
		dciDefinedRules.setBorder(border);
		dciDefinedRules.setFont(new Font("Arial", Font.BOLD, 20));
		indicatorsTable.add(dciDefinedRules);

		diiDefinedRules = new JTextField("-");
		diiDefinedRules.setHorizontalAlignment(JTextField.CENTER);
		diiDefinedRules.setEditable(false);
		diiDefinedRules.setBorder(border);
		diiDefinedRules.setFont(new Font("Arial", Font.BOLD, 20));
		indicatorsTable.add(diiDefinedRules);

		adciDefinedRules = new JTextField("-");
		adciDefinedRules.setHorizontalAlignment(JTextField.CENTER);
		adciDefinedRules.setEditable(false);
		adciDefinedRules.setBorder(border);
		adciDefinedRules.setFont(new Font("Arial", Font.BOLD, 20));
		indicatorsTable.add(adciDefinedRules);

		adiiDefinedRules = new JTextField("-");
		adiiDefinedRules.setHorizontalAlignment(JTextField.CENTER);
		adiiDefinedRules.setEditable(false);
		adiiDefinedRules.setBorder(border);
		adiiDefinedRules.setFont(new Font("Arial", Font.BOLD, 20));
		indicatorsTable.add(adiiDefinedRules);

		esquerdo4.add(qualityIndicatorPanel);
		esquerdo4.add(indicatorsTable);

		esquerdo.add(esquerdo1);
		esquerdo.add(esquerdo2);
		esquerdo.add(esquerdo3);
		esquerdo.add(esquerdo4);
		// Fim de painel Esquerdo
		// Painel direito

		final JPanel direito = new JPanel(new GridLayout());
		direito.setPreferredSize(new Dimension((int) dimension.getWidth() * (2 / 3), (int) dimension.getHeight()));
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

		// add à frame

		frame.setContentPane(new CustomGridBag(esquerdo, direito));

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
		dciIplasma.setText("" + countersIPlasma[0]);
		diiIplasma.setText("" + countersIPlasma[1]);
		adciIplasma.setText("" + countersIPlasma[2]);
		adiiIplasma.setText("" + countersIPlasma[3]);

		dciPmd.setText("" + countersPmd[0]);
		diiPmd.setText("" + countersPmd[1]);
		adciPmd.setText("" + countersPmd[2]);
		adiiPmd.setText("" + countersPmd[3]);
	}

	public void receiveOutputDefectDetectionDefinedRules(String origem, int[] counters) {// receber e colocar na GUI o
																							// resultado das regras
																							// definidas
		definedRules.setText(origem);

		dciDefinedRules.setText("" + counters[0]);
		diiDefinedRules.setText("" + counters[1]);
		adciDefinedRules.setText("" + counters[2]);
		adiiDefinedRules.setText("" + counters[3]);
	}

	public void clearDefectDetectionTable() {// quando é carregado um novo ficheiro tiram-se os resultados anteriores
		dciIplasma.setText("-");
		diiIplasma.setText("-");
		adciIplasma.setText("-");
		adiiIplasma.setText("-");

		dciPmd.setText("-");
		diiPmd.setText("-");
		adciPmd.setText("-");
		adiiPmd.setText("-");

		dciDefinedRules.setText("-");
		diiDefinedRules.setText("-");
		adciDefinedRules.setText("-");
		adiiDefinedRules.setText("-");

		definedRules.setText("Defined Rules");

	}

}