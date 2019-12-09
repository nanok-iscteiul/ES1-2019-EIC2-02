package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
		for (MethodData m : methodsData) {
			if (m.getLoc() > locThreshold && m.getCyclo() > cycloThreshold) {
				m.setIs_long_method_by_rules(true);
			} else {
				m.setIs_long_method_by_rules(false);
			}
		}
	}

	public void feature_envy(double atfdThreshold, String andOr, double laaThreshold) {
		for (MethodData m : methodsData) {
			if (andOr == "and") {
				if (m.getAtfd() > atfdThreshold && m.getLaa() < laaThreshold) {
					m.setIs_feature_envy_by_rules(true);
				} else {
					m.setIs_feature_envy_by_rules(false);
				}
			} else {
				if (m.getAtfd() > atfdThreshold || m.getLaa() < laaThreshold) {
					m.setIs_feature_envy_by_rules(true);
				} else {
					m.setIs_feature_envy_by_rules(false);
				}
			}
		}
	}
	
	public void defectDetection() {
		int[] countersIPlasma = { 0, 0, 0, 0 };
		int[] countersPmd = { 0, 0, 0, 0 };

		for (MethodData m : methodsData) {
			countersIPlasma = checkErrorIdentifiers(m.getIs_long_method(), m.getIplasma(), countersIPlasma);
			countersPmd = checkErrorIdentifiers(m.getIs_long_method(), m.getPmd(), countersPmd);
		}
		gui.receiveOutputDefectDetection(countersIPlasma, countersPmd);
	}

	public void defectDetectionDefinedRules(int number) {// 0-longMethod; 1-feature_envy
		int[] counters = { 0, 0, 0, 0 };
		for (MethodData m : methodsData) {
			if (number == 0) {// para long method
				counters = checkErrorIdentifiers(m.getIs_long_method(), m.getIs_long_method_by_rules(), counters);
			} else {// para feature_envy
				counters = checkErrorIdentifiers(m.getIs_feature_envy(), m.getIs_feature_envy_by_rules(), counters);
			}
		}
		if (number == 0) {
			gui.receiveOutputDefectDetectionDefinedRules("Long Method", counters);
		} else {
			gui.receiveOutputDefectDetectionDefinedRules("Feature Envy", counters);
		}
	}
	
	private int[] checkErrorIdentifiers(boolean islong, boolean iplasmaOrPmd, int[] counters) {
		if (islong == true && iplasmaOrPmd == true) {// DCI
			counters[0]++;
		}
		if (islong == false && iplasmaOrPmd == true) {// DII
			counters[1]++;
		}
		if (islong == false && iplasmaOrPmd == false) {// ADCI
			counters[2]++;
		}
		if (islong == true && iplasmaOrPmd == false) {// ADII
			counters[3]++;
		}
		return counters;
	}
	
	public void loadFile() {
		methodsData.clear();// limpar o vetor anterior sempre que se carrega um novo ficheiro
		try {
			FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));// abre o ficheiro excel
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();
			Row currentRow = iterator.next();// iterador de linhas

			int methodId = -1, loc = -1, cyclo = -1;
			double atfd = -1, laa = -1;
			boolean is_long_method = false, iPlasma = false, pmd = false, is_feature_envy = false;
			String packageName = "", className = "", methodName = "";

			while (iterator.hasNext()) {// percorrer todas as linhas do ficheiro
				currentRow = iterator.next();// iterador de celulas

				int contadorCelula = 0;// contador auxiliar para saber que celula se está a analisar
				Iterator<Cell> cellIterator = currentRow.iterator();

				while (cellIterator.hasNext()) {// percorrer as celulas de cada linha
					Cell currentCell = cellIterator.next();// celula a analisar
					contadorCelula++;

					if (contadorCelula == 1)// obter o ID do metodo
						methodId = (int) currentCell.getNumericCellValue();

					if (contadorCelula == 2)// obter o package do metodo
						packageName = currentCell.getStringCellValue();

					if (contadorCelula == 3)// obter a class do metodo
						className = currentCell.getStringCellValue();

					if (contadorCelula == 4)// obter o nome do metodo
						methodName = currentCell.getStringCellValue();

					if (contadorCelula == 5)// loc
						loc = (int) currentCell.getNumericCellValue();

					if (contadorCelula == 6)// cyclo
						cyclo = (int) currentCell.getNumericCellValue();

					if (contadorCelula == 7)// atfd
						atfd = (double) currentCell.getNumericCellValue();

					if (contadorCelula == 8) {// laa
						String aux_laa = currentCell.toString();
						laa = Double.parseDouble(aux_laa);
					}

					if (contadorCelula == 9)// valor do is_long_method
						is_long_method = currentCell.getBooleanCellValue();

					if (contadorCelula == 10)// valor do iplasma
						iPlasma = currentCell.getBooleanCellValue();

					if (contadorCelula == 11)// valor do pmd
						pmd = currentCell.getBooleanCellValue();

					if (contadorCelula == 12)// valor do feature_envy
						is_feature_envy = currentCell.getBooleanCellValue();

				}

				methodsData.add(new MethodData(methodId, packageName, className, methodName, loc, cyclo, atfd, laa,
						is_long_method, is_feature_envy, iPlasma, pmd));// adicionar o metodo ao vetor
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
	
	public List<MethodData> getMethodsData(){
		return methodsData;
	}
}
