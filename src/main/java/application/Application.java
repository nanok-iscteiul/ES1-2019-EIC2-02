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

/**
 * @author ES1-2019-EIC2-02
 *
 */
public class Application {

	/**
	 * Path to excel file
	 */
	private String FILE_NAME;

	/**
	 * List with all methods obtained from excel file
	 */
	private List<MethodData> methodsData;

	private GUI gui;

	/**
	 * Class constructor
	 */
	public Application() {
		gui = new GUI(this);
		methodsData = new ArrayList<MethodData>();
	}

	/**
	 * Registers the path of excel file.
	 * 
	 * @param path a string containing the path giving the location of excel file
	 */
	public void setPath(String path) {
		this.FILE_NAME = path;
	}

	/**
	 * Updates the is_long_method_by_rules field in each method 
	 * of the methods list if they meet both of the thresholds.
	 * 
	 * @param locThreshold the threshold for the number of lines of code of method
	 * @param cycloThreshold the threshold for the cyclomatic complexity of the method
	 * @see application.MethodData.setIs_long_method_by_rules(boolean)
	 * 
	 */
	public void longMethod(int locThreshold, int cycloThreshold) {
		for (MethodData m : methodsData) {
			if (m.getLoc() > locThreshold && m.getCyclo() > cycloThreshold) {
				m.setIs_long_method_by_rules(true);
			} else {
				m.setIs_long_method_by_rules(false);
			}
		}
	}

	/**
	 * Updates the is_feature_envy_by_rules field in each method
	 * of the list of methods if they meet the required thresholds,
	 * either in conjunction or separately.
	 * 
	 * @param atfdThreshold the threshold for the number of method accesses to methods of other classes
	 * @param laaThreshold the threshold for the number of method accesses to attributes of the class itself
	 * @param andOr ("and" or "or") a string to select whether both of the thresholds needs to be met or only one
	 * @see application.MethodData.setIs_feature_envy_by_rules(boolean)
	 */
	public void feature_envy(double atfdThreshold, double laaThreshold,  String andOr) {
		for (MethodData m : methodsData) {
			if (andOr == "and") {
				if (m.getAtfd() > atfdThreshold && m.getLaa() < laaThreshold) {
					m.setIs_feature_envy_by_rules(true);
				} else {
					m.setIs_feature_envy_by_rules(false);
				}
			} else if(andOr == "or") {
				if (m.getAtfd() > atfdThreshold || m.getLaa() < laaThreshold) {
					m.setIs_feature_envy_by_rules(true);
				} else {
					m.setIs_feature_envy_by_rules(false);
				}
			}
		}
	}

	/**
	 * Detects defects in each method in the methods list, as defined in the excel file.
	 * 
	 * @see checkErrorIdentifiers(boolean, boolean, int[])
	 * @see gui.GUI.receiveOutputDefectDetection(String, int[])
	 */
	public void defectDetection() {
		int[] countersIPlasma = { 0, 0, 0, 0 };// dci, dii, adci, adii, respetivamente para cada posicao do vetor
		int[] countersPmd = { 0, 0, 0, 0 };

		for (MethodData m : methodsData) {
			countersIPlasma = checkErrorIdentifiers(m.getIs_long_method(), m.getIplasma(), countersIPlasma);
			countersPmd = checkErrorIdentifiers(m.getIs_long_method(), m.getPmd(), countersPmd);
		}
		gui.receiveOutputDefectDetection(countersIPlasma, countersPmd);
	}

	/**
	 * Detects defects in each method in the methods list, chosen by the user in the graphical interface.
	 * Informs the graphical interface of the counters.
	 * 
	 * @param number (0 or 1) identifies if checking for defects in long method(0) or feature envy(1)
	 * @see checkErrorIdentifiers(boolean, boolean, int[])
	 * @see gui.GUI.receiveOutputDefectDetectionDefinedRules(String, int[])
	 */
	public void defectDetectionDefinedRules(int number) {
		int[] counters = { 0, 0, 0, 0 };
		for (MethodData m : methodsData) {
			if (number == 0) {
				counters = checkErrorIdentifiers(m.getIs_long_method(), m.getIs_long_method_by_rules(), counters);
			} else if(number == 1) {
				counters = checkErrorIdentifiers(m.getIs_feature_envy(), m.getIs_feature_envy_by_rules(), counters);
			}
		}
		if (number == 0) {
			gui.receiveOutputDefectDetectionDefinedRules("Long Method", counters);
		} else if(number == 1) {
			gui.receiveOutputDefectDetectionDefinedRules("Feature Envy", counters);
		}
	}

	/**
	 * Increments a defect detection value if applicable.
	 * 
	 * @param islong boolean to define whether the method is long(true) of feature envy(false)
	 * @param iplasmaOrPmdOrDefRules boolean to additively specify which defect counter is increment
	 * @param counters the array with the counters of defects
	 * @return 
	 */
	private int[] checkErrorIdentifiers(boolean islong, boolean iplasmaOrPmdOrDefRules, int[] counters) {
		if (islong == true && iplasmaOrPmdOrDefRules == true) {
			counters[0]++;
		}
		if (islong == false && iplasmaOrPmdOrDefRules == true) {
			counters[1]++;
		}
		if (islong == false && iplasmaOrPmdOrDefRules == false) {
			counters[2]++;
		}
		if (islong == true && iplasmaOrPmdOrDefRules == false) {
			counters[3]++;
		}
		return counters;
	}

	/**
	 * Register data from excel file. Starts by clearing data in the methodsData list and proceeds
	 * iterating through rows and cells to create a method from each row of data from excel file.
	 */
	public void loadFile() {
		methodsData.clear();
		try {
			FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();
			Row currentRow = iterator.next();

			int methodId = -1, loc = -1, cyclo = -1;
			double atfd = -1, laa = -1;
			boolean is_long_method = false, iPlasma = false, pmd = false, is_feature_envy = false;
			String packageName = "", className = "", methodName = "";

			while (iterator.hasNext()) {
				currentRow = iterator.next();

				int contadorCelula = 0;
				Iterator<Cell> cellIterator = currentRow.iterator();

				while (cellIterator.hasNext()) {
					Cell currentCell = cellIterator.next();
					contadorCelula++;
					if (contadorCelula == 1)
						methodId = (int) currentCell.getNumericCellValue();
					if (contadorCelula == 2)
						packageName = currentCell.getStringCellValue();
					if (contadorCelula == 3)
						className = currentCell.getStringCellValue();
					if (contadorCelula == 4)
						methodName = currentCell.getStringCellValue();
					if (contadorCelula == 5)
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

				methodsData.add(new MethodData(methodId, packageName, className, methodName, loc, cyclo, atfd, laa,
						is_long_method, is_feature_envy, iPlasma, pmd));
			}
			workbook.close();

		} catch (FileNotFoundException e) {
			System.err.println("Error - File not found");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** 
	 * @return the string that contains the file name
	 */
	public String getFileName() {
		return FILE_NAME;
	}

	/**
	 * @return the list of methods
	 */
	public List<MethodData> getMethodsData() {
		return methodsData;
	}
}
