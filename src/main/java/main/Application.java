package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Application {
	
	private static final String FILE_NAME = "D:\\LEI\\LEI 3º ano\\Engenharia de Software I\\Long-Method.xlsx";
	private int DCI = 0, DII = 0, ADCI = 0, ADII = 0;

	public void defectDetection(){
		
		//Corre o excel
		try {
			FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();

			while (iterator.hasNext()) {
				int contadorCelula = 0;
				boolean islong = false, iplasma = false, pmi = false;
				Row currentRow = iterator.next();
				Iterator<Cell> cellIterator = currentRow.iterator();

				while (cellIterator.hasNext()) {
					Cell currentCell = cellIterator.next();
					contadorCelula++;
					
					if (contadorCelula == 9 ) {
						islong = currentCell.getBooleanCellValue();}
					if (contadorCelula == 10 ) {
						iplasma = currentCell.getBooleanCellValue();}
					if (contadorCelula == 11 ) {
						pmi = currentCell.getBooleanCellValue();
					}		
				}
				checkErrorIdentifiers(islong, iplasma, pmi);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	
	private void checkErrorIdentifiers(boolean islong, boolean iplasma, boolean pmi) {
		if (islong == true && (iplasma == true || pmi == true)) {
			DCI++;}
		if (islong == false && (iplasma == true || pmi == true)) {
			DII++;}
		if (islong == false && (iplasma == false || pmi == false)) {
			ADCI++;}
		if (islong == true && (iplasma == false || pmi == false)) {
			ADII++;}
	}
}
