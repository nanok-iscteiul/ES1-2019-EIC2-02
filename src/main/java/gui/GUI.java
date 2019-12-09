package gui;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import application.Application;
import application.MethodData;

public class GUI {

	private Application app;
	private JFrame frame;
	private JTextField filePath;
	private JTextField loc;
	private JTextField cyclo;
	private JButton longMethod;
	private JComboBox<String> optionList;
	private JTextField laa_txt;
	private JTextField atfd_txt;
	private JButton featureEnvyButton;
	private JTextField dciIplasma;
	private JTextField diiIplasma;
	private JTextField adciIplasma;
	private JTextField adiiIplasma;
	private JTextField dciPmd;
	private JTextField diiPmd;
	private JTextField adciPmd;
	private JTextField adiiPmd;
	private JTextField dciDefinedRules;
	private JTextField diiDefinedRules;
	private JTextField adciDefinedRules;
	private JTextField adiiDefinedRules;
	private JButton showFileButton;
	private DefaultTableModel tableModel;
	private JLabel definedRules;

	public GUI(Application app) {
		this.app = app;
		frame = new JFrame("Aplicação");
		createFrame();
		addFields();
		frame.setVisible(true);
	}

	private void createFrame() {
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setMinimumSize(new Dimension((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 720));
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	private void addFields() {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		
		//Painel esquerdo
		final JPanel esquerdo = new JPanel(new GridLayout(4,1));
		//Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		esquerdo.setPreferredSize(new Dimension((int)dimension.getWidth()*(1/3), (int)dimension.getHeight()));
		//painel esquerdo 1
		JPanel esquerdo1 = new JPanel(new GridLayout(2,1));
		//painel dataFile
		JPanel dataFile = new JPanel(new GridLayout(1,2));
		JLabel dFText = new JLabel("Data File:");
		dFText.setFont(new Font("Arial", Font.BOLD,20));
		
		dataFile.add(dFText);
		
		//painel load/show
		JPanel fileChoser = new JPanel();
		fileChoser.setLayout(new GridLayout(1,3));
		
		filePath = new JTextField("Please Load An Excel file");
		filePath.setHorizontalAlignment(JTextField.CENTER);
		filePath.setEditable(false);
		
		JButton loadFileButton = new JButton("Load File");
		loadFileButton.setHorizontalAlignment(JButton.CENTER);
		loadFileButton.setPreferredSize(new Dimension(300, 25));
		loadFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
					app.setPath(chooser.getSelectedFile().getAbsolutePath());
					filePath.setText(chooser.getSelectedFile().getName());
					featureEnvyButton.setEnabled(true);
					longMethod.setEnabled(true);
					showFileButton.setEnabled(true);
					app.loadFile();
					fillTable();
					app.defectDetection();
				}
			}
		});
		showFileButton = new JButton("Show Excel");
		showFileButton.setEnabled(false);
		showFileButton.setHorizontalAlignment(JButton.CENTER);
		showFileButton.setPreferredSize(new Dimension(300, 25));
		showFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
		            Desktop.getDesktop().open(new File(app.getFileName()));
		        } catch (IOException e1) {
		            System.out.println("Erro ao abrir ficheiro Excel");
		        }
			}
		});
		
		fileChoser.add(filePath);
		fileChoser.add(loadFileButton);
		fileChoser.add(showFileButton);
	
		esquerdo1.add(dataFile);
		esquerdo1.add(fileChoser);
		//painel esquerdo 2
		JPanel esquerdo2 = new JPanel(new GridLayout(2,1));
		
		//Label longmethod
		JPanel longMethodPanel = new JPanel(new GridLayout(1,2));
		JLabel longText = new JLabel("Long Method:");
		longText.setFont(new Font("Arial", Font.BOLD,20));
		longMethodPanel.add(longText);
		
		//painel botoesLongMethod
		JPanel botoesLongMethodPanel = new JPanel(new GridLayout(1,5));
		
		Border border = BorderFactory.createLineBorder(Color.BLUE, 1);
		
		JLabel linesOfCode = new JLabel("Lines of code");
		linesOfCode.setHorizontalAlignment(JLabel.CENTER);
		linesOfCode.setBorder(border);
		linesOfCode.setPreferredSize(new Dimension(115, 30));
		botoesLongMethodPanel.add(linesOfCode);

		loc = new JTextField("80");
		loc.setHorizontalAlignment(JTextField.CENTER);
		loc.setPreferredSize(new Dimension(30, 30));
		botoesLongMethodPanel.add(loc);
		loc.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent keyEvent) {
				char c = keyEvent.getKeyChar();
				if (!(Character.isDigit(c))) {
					keyEvent.consume();
				}
			}
		});
		
		JLabel cyclomaticComplexity = new JLabel("Cyclomatic Complexity");
		cyclomaticComplexity.setHorizontalAlignment(JLabel.CENTER);
		cyclomaticComplexity.setBorder(border);
		cyclomaticComplexity.setPreferredSize(new Dimension(140, 30));
		botoesLongMethodPanel.add(cyclomaticComplexity);

		cyclo = new JTextField("10");
		cyclo.setHorizontalAlignment(JTextField.CENTER);
		cyclo.setPreferredSize(new Dimension(30, 30));
		botoesLongMethodPanel.add(cyclo);
		cyclo.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent keyEvent) {
				char c = keyEvent.getKeyChar();
				if (!(Character.isDigit(c))) {
					keyEvent.consume();
				}
			}
		});

		longMethod = new JButton("Long Method");
		longMethod.setPreferredSize(new Dimension(300, 50));
		longMethod.setEnabled(false);
		botoesLongMethodPanel.add(longMethod);
		longMethod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (loc.getText() != "" && cyclo.getText() != "") {
					int locValue = Integer.parseInt(loc.getText());
					int cycloValue = Integer.parseInt(cyclo.getText());
					app.longMethod(locValue,cycloValue);
					fillLongMethod();
					app.defectDetectionDefinedRules(0);
				}
			}
		});
		esquerdo2.add(longMethodPanel);
		esquerdo2.add(botoesLongMethodPanel);
		// fim painel esquerdo 2
		
			
		//painel esquerdo 3
		
		JPanel esquerdo3 = new JPanel(new GridLayout(2,1));
		JPanel featureEnvyPanel = new JPanel(new GridLayout(1,2));
		JLabel featureEnvyLabel = new JLabel("Feature Envy:");
		featureEnvyLabel.setFont(new Font("Arial", Font.BOLD,20));
		featureEnvyPanel.add(featureEnvyLabel);
		
		JPanel botoesFeatureEnvyPanel = new JPanel(new GridLayout(1,6));

		JLabel atfd = new JLabel("ATFD >");
		atfd.setHorizontalAlignment(JLabel.CENTER);
		atfd.setBorder(border);
		atfd.setPreferredSize(new Dimension(75, 30));
		botoesFeatureEnvyPanel.add(atfd);

		atfd_txt = new JTextField("4");
		atfd_txt.setHorizontalAlignment(JTextField.CENTER);
		atfd_txt.setPreferredSize(new Dimension(30, 30));
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
		botoesFeatureEnvyPanel.add(atfd_txt);

		String[] option = { "and", "or" };
		optionList = new JComboBox<String>(option);
		optionList.setSelectedIndex(1);
		optionList.setPreferredSize(new Dimension(108, 30));
		botoesFeatureEnvyPanel.add(optionList);

		JLabel laa = new JLabel("LAA <");
		laa.setHorizontalAlignment(JLabel.CENTER);
		laa.setBorder(border);
		laa.setPreferredSize(new Dimension(75, 30));
		botoesFeatureEnvyPanel.add(laa);

		laa_txt = new JTextField("0.42");
		laa_txt.setHorizontalAlignment(JTextField.CENTER);
		laa_txt.setPreferredSize(new Dimension(30, 30));
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
		botoesFeatureEnvyPanel.add(laa_txt);

		featureEnvyButton = new JButton("Feature Envy");
		featureEnvyButton.setHorizontalAlignment(JButton.CENTER);
		featureEnvyButton.setPreferredSize(new Dimension(300, 50));
		featureEnvyButton.setEnabled(false);
		botoesFeatureEnvyPanel.add(featureEnvyButton);
		featureEnvyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String choice = optionList.getSelectedItem().toString();
				app.feature_envy(Double.parseDouble(atfd_txt.getText()), choice, Double.parseDouble(laa_txt.getText()));
				fillFeature_envy();
				app.defectDetectionDefinedRules(1);
				
			}
		});
		
		esquerdo3.add(featureEnvyPanel);
		esquerdo3.add(botoesFeatureEnvyPanel);
		
		//painel esquerdo 4
		JPanel esquerdo4 = new JPanel(new GridLayout(2,1));
		JPanel qualityIndicatorPanel = new JPanel(new GridLayout(1,2));
		JLabel qualityIndicatorLabel = new JLabel("Quality indicators:");
		qualityIndicatorLabel.setFont(new Font("Arial", Font.BOLD,20));
		qualityIndicatorPanel.add(qualityIndicatorLabel);
		
		JPanel indicatorsTable = new JPanel(new GridLayout(0,5));
		JLabel blank = new JLabel("");
		blank.setHorizontalAlignment(JTextField.CENTER);
		blank.setBorder(border);
		indicatorsTable.add(blank);
		
		JLabel dci = new JLabel("DCI");
		dci.setHorizontalAlignment(JTextField.CENTER);
		dci.setBorder(border);
		indicatorsTable.add(dci);
		JLabel dii = new JLabel("DII");
		dii.setHorizontalAlignment(JTextField.CENTER);
		dii.setBorder(border);
		indicatorsTable.add(dii);
		JLabel adci = new JLabel("ADCI");
		adci.setHorizontalAlignment(JTextField.CENTER);
		adci.setBorder(border);
		indicatorsTable.add(adci);
		JLabel adii = new JLabel("ADII");
		adii.setHorizontalAlignment(JTextField.CENTER);
		adii.setBorder(border);
		indicatorsTable.add(adii);
		JLabel iplasma = new JLabel("Iplasma");
		iplasma.setHorizontalAlignment(JTextField.CENTER);
		iplasma.setBorder(border);
		indicatorsTable.add(iplasma);
		
		dciIplasma = new JTextField("-");
		dciIplasma.setHorizontalAlignment(JTextField.CENTER);
		dciIplasma.setEditable(false);
		dciIplasma.setBorder(border);
		indicatorsTable.add(dciIplasma);
		
		diiIplasma = new JTextField("-");
		diiIplasma.setHorizontalAlignment(JTextField.CENTER);
		diiIplasma.setEditable(false);
		diiIplasma.setBorder(border);
		indicatorsTable.add(diiIplasma);
		
		adciIplasma = new JTextField("-");
		adciIplasma.setHorizontalAlignment(JTextField.CENTER);
		adciIplasma.setEditable(false);
		adciIplasma.setBorder(border);
		indicatorsTable.add(adciIplasma);

		adiiIplasma = new JTextField("-");
		adiiIplasma.setHorizontalAlignment(JTextField.CENTER);
		adiiIplasma.setEditable(false);
		adiiIplasma.setBorder(border);
		indicatorsTable.add(adiiIplasma);
		
		JLabel pmd = new JLabel("PMD");
		pmd.setHorizontalAlignment(JTextField.CENTER);
		pmd.setBorder(border);
		indicatorsTable.add(pmd);
		
		dciPmd = new JTextField("-");
		dciPmd.setHorizontalAlignment(JTextField.CENTER);
		dciPmd.setEditable(false);
		dciPmd.setBorder(border);
		indicatorsTable.add(dciPmd);
		
		diiPmd = new JTextField("-");
		diiPmd.setHorizontalAlignment(JTextField.CENTER);
		diiPmd.setEditable(false);
		diiPmd.setBorder(border);
		indicatorsTable.add(diiPmd);
		
		adciPmd = new JTextField("-");
		adciPmd.setHorizontalAlignment(JTextField.CENTER);
		adciPmd.setEditable(false);
		adciPmd.setBorder(border);
		indicatorsTable.add(adciPmd);

		adiiPmd = new JTextField("-");
		adiiPmd.setHorizontalAlignment(JTextField.CENTER);
		adiiPmd.setEditable(false);
		adiiPmd.setBorder(border);
		indicatorsTable.add(adiiPmd);
		
		definedRules = new JLabel("Defined Rules");
		definedRules.setHorizontalAlignment(JTextField.CENTER);
		definedRules.setBorder(border);
		indicatorsTable.add(definedRules);
		
		dciDefinedRules = new JTextField("-");
		dciDefinedRules.setHorizontalAlignment(JTextField.CENTER);
		dciDefinedRules.setEditable(false);
		dciDefinedRules.setBorder(border);
		indicatorsTable.add(dciDefinedRules);
		
		diiDefinedRules = new JTextField("-");
		diiDefinedRules.setHorizontalAlignment(JTextField.CENTER);
		diiDefinedRules.setEditable(false);
		diiDefinedRules.setBorder(border);
		indicatorsTable.add(diiDefinedRules);
		
		adciDefinedRules = new JTextField("-");
		adciDefinedRules.setHorizontalAlignment(JTextField.CENTER);
		adciDefinedRules.setEditable(false);
		adciDefinedRules.setBorder(border);
		indicatorsTable.add(adciDefinedRules);

		adiiDefinedRules = new JTextField("-");
		adiiDefinedRules.setHorizontalAlignment(JTextField.CENTER);
		adiiDefinedRules.setEditable(false);
		adiiDefinedRules.setBorder(border);
		indicatorsTable.add(adiiDefinedRules);		
		
		esquerdo4.add(qualityIndicatorPanel);
		esquerdo4.add(indicatorsTable);
		
		esquerdo.add(esquerdo1);
		esquerdo.add(esquerdo2);
		esquerdo.add(esquerdo3);
		esquerdo.add(esquerdo4);
		//Fim de painel Esquerdo
		//Painel direito
		
		final JPanel direito = new JPanel(new GridLayout());
		direito.setPreferredSize(new Dimension((int)dimension.getWidth()*(2/3), (int)dimension.getHeight()));
		String[] col = { "MethodId", "LOC", "CYCLO","ATFD", "LAA", "is long method", "iPlasma","PMD", 
				"is feature envy", "is long method by defined rules",
				"is feature envy by defined rules"};
		tableModel = new DefaultTableModel(col, 0);
		JTable table = new JTable(tableModel);
		for(int i=0;i<5;i++) {
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
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		
		for(int i=0;i<col.length;i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
		
		table.setEnabled(false);
	
		direito.add(new JScrollPane(table));
		
		//add à frame
	
		frame.setContentPane(new CustomGridBag(esquerdo, direito));
		
	}
	
	private void fillTable() {
		tableModel.setRowCount(0);
		for( MethodData m : app.getMethodsData()) {
			String[] aux = { String.valueOf(m.getMethodId()),String.valueOf(m.getLoc()),
					String.valueOf(m.getCyclo()), String.valueOf(m.getAtfd()), 
					String.valueOf(m.getLaa()),String.valueOf(m.getIs_long_method()), 
					String.valueOf(m.getIplasma()), String.valueOf(m.getPmd()),
					String.valueOf(m.getIs_feature_envy()) };
			tableModel.addRow(aux);
		}
	}
	
	private void fillLongMethod() {
		for(MethodData m : app.getMethodsData()) {
			tableModel.setValueAt(m.getIs_long_method_by_rules(), m.getMethodId()-1, 9);
		}
	}
	
	private void fillFeature_envy() {
		for(MethodData m : app.getMethodsData()) {
			tableModel.setValueAt(m.getIs_feature_envy_by_rules(), m.getMethodId()-1, 10);
		}
	}
	
	

	public void receiveOutputDefectDetection(int [] countersIPlasma, int [] countersPmd) {
		dciIplasma.setText("" + countersIPlasma[0]);
		diiIplasma.setText("" + countersIPlasma[1]);
		adciIplasma.setText("" + countersIPlasma[2]);
		adiiIplasma.setText("" + countersIPlasma[3]);
		
		dciPmd.setText(""+ countersPmd[0]);
		diiPmd.setText(""+ countersPmd[1]);
		adciPmd.setText(""+ countersPmd[2]);
		adiiPmd.setText(""+ countersPmd[3]);
	}
	
	public void receiveOutputDefectDetectionDefinedRules(String origem,int [] counters) {
		definedRules.setText(origem);
		
		dciDefinedRules.setText(""+ counters[0]);
		diiDefinedRules.setText(""+ counters[1]);
		adciDefinedRules.setText(""+ counters[2]);
		adiiDefinedRules.setText(""+ counters[3]);
	}
	
	public DefaultTableModel getTableModel() {
		return tableModel;
	}

}