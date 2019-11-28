package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Main {

	private static final String FILE_NAME = "D:\\LEI_3_ano\\Engenharia_de_Software_1\\Long-Method.xlsx";
	

	public static void main(String[] args) {
		GUI gui = new GUI();
		gui.open();
		try {
			FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();

			while (iterator.hasNext()) {

				Row currentRow = iterator.next();
				Iterator<Cell> cellIterator = currentRow.iterator();

				while (cellIterator.hasNext()) {
					Cell currentCell = cellIterator.next();
					if (currentCell.getCellTypeEnum() == CellType.STRING) {
						System.out.print(currentCell.getStringCellValue() + "--");
					} else {
						if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
							System.out.print(currentCell.getNumericCellValue() + "--");
						} else {
							if (currentCell.getCellTypeEnum() == CellType.BOOLEAN) {
								System.out.println(currentCell.getBooleanCellValue() + "--");
							}
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
