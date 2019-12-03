package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JTextField;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Application {

	private static final int LOC_THRESHOLD = 80;// metricas por defeito para o long method
	private static final int CYCLO_THRESHOLD = 10;

	private static int LOC_THRESHOLD_IN_USE = LOC_THRESHOLD;
	private static int CYCLO_THRESHOLD_IN_USE = CYCLO_THRESHOLD;
	private int DCI = 0, DII = 0, ADCI = 0, ADII = 0;

	private String FILE_NAME = "C:\\Users\\nicha\\Desktop\\Long-Method.xlsx";

	private GUI gui;

	public Application() {
		gui = new GUI(this);
//		defectDetection();// tirar isto
//		 longMethod();//tirar isto
	}

	public void setLocCycloThresholds(int loc, int cyclo) {// definir metrica caso o utilizador altere
		LOC_THRESHOLD_IN_USE = loc;
		CYCLO_THRESHOLD_IN_USE = cyclo;
	}

	public void setPath(String path) {
		this.FILE_NAME = path;
	}

	public void longMethod() {

		try {
			FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();
			Row currentRow = iterator.next();

			List<Method> longMethods = new ArrayList<Method>();
			List<Method> nonLongMethods = new ArrayList<Method>();

			String packageName = "", className = "", methodName = "";
			int loc = -1, cyclo = -1;
			int methodId = -1;

			while (iterator.hasNext()) {
				currentRow = iterator.next();

				int contadorCelula = 0;
				Iterator<Cell> cellIterator = currentRow.iterator();

				while (cellIterator.hasNext()) {
					Cell currentCell = cellIterator.next();
					contadorCelula++;

					if (contadorCelula == 1) {
						methodId = (int) currentCell.getNumericCellValue();
					}

					if (contadorCelula == 2)
						packageName = currentCell.getStringCellValue();

					if (contadorCelula == 3)
						className = currentCell.getStringCellValue();

					if (contadorCelula == 4)
						methodName = currentCell.getStringCellValue();

					if (contadorCelula == 5)
						loc = (int) currentCell.getNumericCellValue();

					if (contadorCelula == 6) {
						cyclo = (int) currentCell.getNumericCellValue();

						if (LOC_THRESHOLD_IN_USE < loc && CYCLO_THRESHOLD_IN_USE < cyclo) {
							longMethods.add(new Method(methodId, methodName));
						}

						else {
							nonLongMethods.add(new Method(methodId, methodName));
						}
					}
					// System.out.println("Package: "+ packageName + " Class: "+ className+ "
					// methodName: "+ methodName+ " loc= "+loc+" cyclo= "+ cyclo);
				}
			}

			for (Method m : longMethods) {
				System.out.println(m + "is long Method");
			}

			for (Method m : nonLongMethods) {
				System.out.println(m + "not long Method");
			}
			workbook.close();
			gui.receiveOutputLongMethod(longMethods, nonLongMethods);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void defectDetection() {
		resetCounters();
		// Corre o excel
		try {
			FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();
			Row currentRow = iterator.next();
			while (iterator.hasNext()) {
				int contadorCelula = 0;
				boolean islong = false, iplasma = false, pmi = false;
				currentRow = iterator.next();
				Iterator<Cell> cellIterator = currentRow.iterator();

				while (cellIterator.hasNext()) {
					Cell currentCell = cellIterator.next();
					contadorCelula++;

					if (contadorCelula == 9) {
						islong = currentCell.getBooleanCellValue();
					}
					if (contadorCelula == 10) {
						iplasma = currentCell.getBooleanCellValue();
					}
					if (contadorCelula == 11) {
						pmi = currentCell.getBooleanCellValue();
					}
				}
				checkErrorIdentifiers(islong, iplasma, pmi);
			}
			gui.receiveOutputDefectDetection(DCI, DII, ADCI, ADII);
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void checkErrorIdentifiers(boolean islong, boolean iplasma, boolean pmi) {
		if (islong == true && (iplasma == true || pmi == true)) {
			DCI++;
		}
		if (islong == false && (iplasma == true || pmi == true)) {
			DII++;
		}
		if (islong == false && (iplasma == false || pmi == false)) {
			ADCI++;
		}
		if (islong == true && (iplasma == false || pmi == false)) {
			ADII++;
		}
	}

	private void resetCounters() {
		DCI = 0;
		DII = 0;
		ADCI = 0;
		ADII = 0;
	}

}
