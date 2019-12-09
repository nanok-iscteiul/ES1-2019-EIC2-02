package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import gui.GUI;

public class Application {

	private String FILE_NAME;// caminho do ficheiro excel
	
	private List<MethodData> methodsData;

	private GUI gui;

	public Application() {
		gui = new GUI(this);
		methodsData = new ArrayList<MethodData>();
	}

	public void setPath(String path) {// definir o caminho do ficheiro excel
		this.FILE_NAME = path;
	}

	public void longMethod(int locThreshold, int cycloThreshold) {
		DefaultTableModel tableModel = gui.getTableModel();
		try {
			FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));// abre o ficheiro excel
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();
			Row currentRow = iterator.next();// iterador de linhas

			int loc = -1, cyclo = -1;
			int tableRowIterator = 0;
			boolean is_long_method_by_rules = false;

			while (iterator.hasNext()) {// percorrer todas as linhas do ficheiro
				currentRow = iterator.next();// iterador de celulas

				int contadorCelula = 0;// contador auxiliar para saber que celula se está a analisar
				Iterator<Cell> cellIterator = currentRow.iterator();

				while (cellIterator.hasNext()) {// percorrer as celulas de cada linha
					Cell currentCell = cellIterator.next();// celula a analisar
					contadorCelula++;

					if (contadorCelula == 5)// obter o numero de linhas de codigo do metodo
						loc = (int) currentCell.getNumericCellValue();

					if (contadorCelula == 6) {// obter a complexidade ciclomatica e inserir o metodo na lista adequada
						cyclo = (int) currentCell.getNumericCellValue();

						if (locThreshold < loc && cycloThreshold < cyclo) {
							is_long_method_by_rules = true;// no caso de ser longMethod para as
															// metricas indicadas
						} else {
							is_long_method_by_rules = false;// no caso de não ser longMethod para
															// as metricas indicadas.
						}
					}
				}
				tableModel.setValueAt(is_long_method_by_rules, tableRowIterator, 9);
				tableRowIterator++;
			}
			workbook.close();
		} catch (FileNotFoundException e) {
			System.out.println("Erro ao abrir o ficheiro!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void defectDetection() {
		int [] countersIPlasma = {0,0,0,0};
		int [] countersPmd = {0,0,0,0};
		try {
			FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));// abrir o ficheiro excel
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();
			Row currentRow = iterator.next();// iterador de linhas
			while (iterator.hasNext()) {// percorer todas as linhas
				int contadorCelula = 0;
				boolean islong = false, iplasma = false, pmi = false;
				currentRow = iterator.next();
				Iterator<Cell> cellIterator = currentRow.iterator();// iterador de celulas

				while (cellIterator.hasNext()) {// percorer todas as celulas de cada linha
					Cell currentCell = cellIterator.next();// celula a analisar
					contadorCelula++;

					if (contadorCelula == 9) {// valor do is_longMethod
						islong = currentCell.getBooleanCellValue();
					}
					if (contadorCelula == 10) {// valor da ferramenta iplasma
						iplasma = currentCell.getBooleanCellValue();
					}
					if (contadorCelula == 11) {// valor da ferramenta pmi
						pmi = currentCell.getBooleanCellValue();
					}
				}
				countersIPlasma = checkErrorIdentifiers(islong,iplasma,countersIPlasma);
				countersPmd = checkErrorIdentifiers(islong, pmi, countersPmd);
			}
			gui.receiveOutputDefectDetection(countersIPlasma, countersPmd);
			workbook.close();
		} catch (FileNotFoundException e) {
			System.out.println("Erro ao abrir o ficheiro!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private int [] checkErrorIdentifiers(boolean islong, boolean iplasmaOrPmd,int [] counters) {
		if (islong == true && iplasmaOrPmd == true ) {//DCI
			counters[0]++;
		}
		if (islong == false && iplasmaOrPmd == true ) {//DII
			counters[1]++;
		}
		if (islong == false && iplasmaOrPmd == false ) {//ADCI
			counters[2]++;
		}
		if (islong == true && iplasmaOrPmd == false ) {//ADII
			counters[3]++;
		}
		return counters;
	}

	public void feature_envy(double ATFDThreshold, String andOr, double LAAThreshold) {
		DefaultTableModel tableModel = gui.getTableModel();
		try {
			FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));// abre o ficheiro excel
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();
			Row currentRow = iterator.next();// iterador de linhas

			double atfd = -1;
			double laa = -1;
			int tableRowIterator = 0;
			boolean is_feature_envy_by_rules = false;

			while (iterator.hasNext()) {// percorrer todas as linhas do ficheiro
				currentRow = iterator.next();// iterador de celulas

				int contadorCelula = 0;// contador auxiliar para saber que celula se está a analisar
				Iterator<Cell> cellIterator = currentRow.iterator();

				while (cellIterator.hasNext()) {// percorrer as celulas de cada linha
					Cell currentCell = cellIterator.next();// celula a analisar
					contadorCelula++;

					if (contadorCelula == 7)
						atfd = (double) currentCell.getNumericCellValue();

					if (contadorCelula == 8) {
						String aux_laa = currentCell.toString();
						laa = Double.parseDouble(aux_laa);

						if (andOr == "and") {

							if (atfd > ATFDThreshold && laa < LAAThreshold) {
								is_feature_envy_by_rules = true;
							} else {
								is_feature_envy_by_rules = false;
							}
						} else {
							if (atfd > ATFDThreshold || laa < LAAThreshold) {
								is_feature_envy_by_rules = true;
							} else {
								is_feature_envy_by_rules = false;
							}
						}
					}
				}
				tableModel.setValueAt(is_feature_envy_by_rules,tableRowIterator,10);
				tableRowIterator++;
			}
			workbook.close();
		} catch (FileNotFoundException e) {
			System.out.println("Erro ao abrir o ficheiro!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void fillTable() {
		DefaultTableModel tableModel = gui.getTableModel();
		try {
			FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));// abre o ficheiro excel
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();
			Row currentRow = iterator.next();// iterador de linhas

			int methodId = -1;
			int loc = -1;
			int cyclo = -1;
			double atfd = -1;
			double laa = -1;
			boolean is_long_method = false;
			boolean iPlasma = false;
			boolean pmd = false;
			boolean is_feature_envy = false;

			while (iterator.hasNext()) {// percorrer todas as linhas do ficheiro
				currentRow = iterator.next();// iterador de celulas

				int contadorCelula = 0;// contador auxiliar para saber que celula se está a analisar
				Iterator<Cell> cellIterator = currentRow.iterator();

				while (cellIterator.hasNext()) {// percorrer as celulas de cada linha
					Cell currentCell = cellIterator.next();// celula a analisar
					contadorCelula++;

					if (contadorCelula == 1)// obter o ID do metodo
						methodId = (int) currentCell.getNumericCellValue();

					if (contadorCelula == 5)// loc
						loc = (int) currentCell.getNumericCellValue();

					if (contadorCelula == 6)
						cyclo = (int) currentCell.getNumericCellValue();

					if (contadorCelula == 7)
						atfd = (double) currentCell.getNumericCellValue();

					if (contadorCelula == 8) {
						String aux_laa = currentCell.toString();
						laa = Double.parseDouble(aux_laa);
					}

					if (contadorCelula == 9)
						is_long_method = currentCell.getBooleanCellValue();

					if (contadorCelula == 10)
						iPlasma = currentCell.getBooleanCellValue();

					if (contadorCelula == 11)
						pmd = currentCell.getBooleanCellValue();

					if (contadorCelula == 12)
						is_feature_envy = currentCell.getBooleanCellValue();

				}
				String[] aux = { "" + methodId, "" + loc, "" + cyclo, "" + (int) atfd, "" + laa,
						String.valueOf(is_long_method), String.valueOf(iPlasma), String.valueOf(pmd),
						String.valueOf(is_feature_envy) };
				tableModel.addRow(aux);
			}
			workbook.close();

		} catch (FileNotFoundException e) {
			System.out.println("Erro ao abrir o ficheiro!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void defectDetectionDefinedRules(int number) {
		int [] counters = {0,0,0,0};
		DefaultTableModel tableModel = gui.getTableModel();
		try {
			FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));// abrir o ficheiro excel
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();
			Row currentRow = iterator.next();// iterador de linhas
			
			int tableRowIterator = 0;
			boolean excelIsLongMethod = false;
			boolean tableIsLongMethod = false;
			boolean excelIsFeatureEnvy = false;
			boolean tableIsFeatureEnvy = false;
			
			while (iterator.hasNext()) {// percorer todas as linhas
				int contadorCelula = 0;
				
				currentRow = iterator.next();
				Iterator<Cell> cellIterator = currentRow.iterator();// iterador de celulas

				while (cellIterator.hasNext()) {// percorer todas as celulas de cada linha
					Cell currentCell = cellIterator.next();// celula a analisar
					contadorCelula++;

					if (contadorCelula == 9 && number == 0) {// valor do is_longMethod
						excelIsLongMethod = currentCell.getBooleanCellValue();
					}
					
					if (contadorCelula == 12 && number == 1) {
						excelIsFeatureEnvy = currentCell.getBooleanCellValue();
					}
					
				}
				String aux;
				if( number == 0) {//para long method
					aux = tableModel.getValueAt(tableRowIterator, 9).toString();
					if( aux == "false") {
						tableIsLongMethod = false;
					}else {
						tableIsLongMethod = true;
					}
					counters=checkErrorIdentifiers(excelIsLongMethod, tableIsLongMethod, counters);
				}else {//para feature_envy
					aux = tableModel.getValueAt(tableRowIterator, 10).toString();
					if( aux == "false") {
						tableIsFeatureEnvy = false;
					}else {
						tableIsFeatureEnvy = true;
					}
					counters=checkErrorIdentifiers(excelIsFeatureEnvy, tableIsFeatureEnvy, counters);
				}
				tableRowIterator++;
			}
			if( number == 0){
				gui.receiveOutputDefectDetectionDefinedRules("Long Method", counters);
			}
			else {
				gui.receiveOutputDefectDetectionDefinedRules("Feature Envy", counters);
			}
			workbook.close();
		} catch (FileNotFoundException e) {
			System.out.println("Erro ao abrir o ficheiro!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getFileName() {
		return FILE_NAME;
	}

}
