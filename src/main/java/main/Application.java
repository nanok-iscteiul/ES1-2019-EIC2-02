package main;

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

public class Application {

	private static final int LOC_THRESHOLD = 80;// metricas por defeito para o long method
	private static final int CYCLO_THRESHOLD = 10;

	private static int LOC_THRESHOLD_IN_USE = LOC_THRESHOLD;
	private static int CYCLO_THRESHOLD_IN_USE = CYCLO_THRESHOLD;
	private int DCI = 0, DII = 0, ADCI = 0, ADII = 0;

	private String FILE_NAME = "C:\\Users\\nicha\\Desktop\\Long-Method.xlsx";//caminho do ficheiro excel

	private GUI gui;

	public Application() {
		gui = new GUI(this);
	}

	public void setLocCycloThresholds(int loc, int cyclo) {// definir metrica caso o utilizador altere
		LOC_THRESHOLD_IN_USE = loc;
		CYCLO_THRESHOLD_IN_USE = cyclo;
	}

	public void setPath(String path) {// definir o caminho do ficheiro excel
		this.FILE_NAME = path;
	}

	public void longMethod() {
		try {
			FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));//abre o ficheiro excel
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();
			Row currentRow = iterator.next();//iterador de linhas

			List<Method> longMethods = new ArrayList<Method>();//lista dos metodos que são long method de acordo com as metricas indicadas
			List<Method> nonLongMethods = new ArrayList<Method>();//lista dos metodos que não sãp long method de acordo com as metricas indicadas

			String methodName = "";
			int loc = -1, cyclo = -1;
			int methodId = -1;

			while (iterator.hasNext()) {//percorrer todas as linhas do ficheiro
				currentRow = iterator.next();//iterador de celulas

				int contadorCelula = 0;//contador auxiliar para saber que celula se está a analisar
				Iterator<Cell> cellIterator = currentRow.iterator();

				while (cellIterator.hasNext()) {//percorrer as celulas de cada linha
					Cell currentCell = cellIterator.next();//celula a analisar
					contadorCelula++;

					if (contadorCelula == 1)//obter o ID do metodo
						methodId = (int) currentCell.getNumericCellValue();

					if (contadorCelula == 4)//obter o nome do metodo
						methodName = currentCell.getStringCellValue();

					if (contadorCelula == 5)//obter o numero de linhas de codigo do metodo
						loc = (int) currentCell.getNumericCellValue();

					if (contadorCelula == 6) {//obter a complexidade ciclomatica e inserir o metodo na lista adequada
						cyclo = (int) currentCell.getNumericCellValue();

						if (LOC_THRESHOLD_IN_USE < loc && CYCLO_THRESHOLD_IN_USE < cyclo) {
							longMethods.add(new Method(methodId, methodName));//no caso de ser longMethod para as metricas indicadas
						} else {
							nonLongMethods.add(new Method(methodId, methodName));//no caso de não ser longMethod para as metricas indicadas.
						}
					}
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
			System.out.println("Erro ao abrir o ficheiro!");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void defectDetection() {
		resetCounters();
		try {
			FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));//abrir o ficheiro excel
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();
			Row currentRow = iterator.next();//iterador de linhas
			while (iterator.hasNext()) {//percorer todas as linhas 
				int contadorCelula = 0;
				boolean islong = false, iplasma = false, pmi = false;
				currentRow = iterator.next();
				Iterator<Cell> cellIterator = currentRow.iterator();//iterador de celulas

				while (cellIterator.hasNext()) {//percorer todas as celulas de cada linha
					Cell currentCell = cellIterator.next();//celula a analisar
					contadorCelula++;

					if (contadorCelula == 9) {//valor do is_longMethod
						islong = currentCell.getBooleanCellValue();
					}
					if (contadorCelula == 10) {//valor da ferramenta iplasma
						iplasma = currentCell.getBooleanCellValue();
					}
					if (contadorCelula == 11) {//valor da ferramenta pmi
						pmi = currentCell.getBooleanCellValue();
					}
				}
				checkErrorIdentifiers(islong, iplasma, pmi);
			}
			gui.receiveOutputDefectDetection(DCI, DII, ADCI, ADII);
			workbook.close();
		} catch (FileNotFoundException e) {
			System.out.println("Erro ao abrir o ficheiro!");
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
