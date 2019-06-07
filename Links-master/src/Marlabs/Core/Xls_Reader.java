package Marlabs.Core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



import Marlabs.Constants.Constants;

public class Xls_Reader {
	// Set the excel sheet path
	public static String filename = System.getProperty("user.dir") + "\\src\\Marlabs\\Data\\";
	public String path;
	public FileInputStream fis = null;
	public FileOutputStream fileOut = null;
	private XSSFWorkbook workbook = null;
	private XSSFSheet sheet = null;
	private XSSFRow row = null;
	private XSSFCell cell = null;
	
	// Sheet path
	public Xls_Reader(String path) {
		this.path = path;
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheetAt(0);
			fis.close();
		} catch (Exception e) {
			// Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Xls_Reader() {}


	// Get the row count from sheet
	public int getRowCount(String sheetName) {
		// Get the sheet name count
		System.out.println("Sheet Name ----" + sheetName);
		int index = workbook.getSheetIndex(sheetName);
		if (index == -1)
			return 0;
		else {
			sheet = workbook.getSheetAt(index);
			int number = sheet.getPhysicalNumberOfRows();

			System.out.println("rows " + number);
			return number;
		}
	}

	public int getColCount(String sheetName) {
		
		int noofColumns = sheet.getRow(0).getPhysicalNumberOfCells();
		
		System.out.println("Number of columns: " + noofColumns);
		if (noofColumns == -1)
			return -1;
		else
			return noofColumns;
	}

	// Returns the data from sheet based on the
	// "Sheet name,Column name, Row count"
	@SuppressWarnings("deprecation")
	public String getCellData(String sheetName, String colName, int rowNum) {
		try {
			if (rowNum <= 0)
				return "";

			int index = workbook.getSheetIndex(sheetName);
			int col_Num = -1;
			if (index == -1)
				return "";

			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				if (row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
					col_Num = i;
			}
			if (col_Num == -1)
				return "";

			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				return "";
			cell = row.getCell(col_Num);

			if (cell == null)
				return "";
			// New Change
			cell.setCellType(Cell.CELL_TYPE_STRING);

			if (cell.getCellType() == Cell.CELL_TYPE_STRING)
				return cell.getStringCellValue();
			else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC || cell.getCellType() == Cell.CELL_TYPE_FORMULA) {

				String cellText = String.valueOf(cell.getNumericCellValue());
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					// format in form of M/D/YY
					double d = cell.getNumericCellValue();
					// Get the time/date
					Calendar cal = Calendar.getInstance();
					cal.setTime(HSSFDateUtil.getJavaDate(d));
					cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
					cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH) + 1 + "/" + cellText;
				}
				// It will return the cell data
				return cellText;
			} else if (cell.getCellType() == Cell.CELL_TYPE_BLANK)
				return "";
			else
				return String.valueOf(cell.getBooleanCellValue());
		} catch (Exception e) {
			e.printStackTrace();
			return "row " + rowNum + " or column " + colName + " does not exist in xls";
		}
	}

	public int getCellRowNum(String sheetName, String colName, String cellValue) {

		for (int i = 2; i <= getRowCount(sheetName); i++) {
			if (getCellData(sheetName, colName, i).equalsIgnoreCase(cellValue)) {
				return i;
			}
		}
		return -1;

	}

	// Returns the data from sheet based on the
	// "Sheet name,Column count, Row count"
	@SuppressWarnings("deprecation")
	public String getCellData(String sheetName, int colNum, int rowNum) {
		try {

			if (rowNum <= 0)
				return "";

			int index = workbook.getSheetIndex(sheetName);

			if (index == -1)
				return "";

			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum - 1);
			
			if (row == null)
				return "";
			
			cell = row.getCell(colNum);
			
			if (cell == null)
				return "";
			
			cell.setCellType(Cell.CELL_TYPE_STRING);
			
			if (cell.getCellType() == Cell.CELL_TYPE_STRING)
				return cell.getStringCellValue();
			else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC || cell.getCellType() == Cell.CELL_TYPE_FORMULA) {

				String cellText = String.valueOf(cell.getNumericCellValue());
			
				return cellText;
			} else if (cell.getCellType() == Cell.CELL_TYPE_BLANK)
				return "";
			else
				return String.valueOf(cell.getBooleanCellValue());
		} catch (Exception e) {

			e.printStackTrace();
			return "row " + rowNum + " or column " + colNum + " does not exist  in xls";
		}
	}

	// ********************************************************************************************************************
	// Generic function name :Getdata
	// Description : Fetching the data from excel sheet
	// ********************************************************************************************************************
	public String getdata(String path, String sheetName, int rowNum, int colNum) {
		String retVal = null;
		try {

			FileInputStream fis = new FileInputStream(path);
			Workbook wb = WorkbookFactory.create(fis);
			XSSFSheet s = (XSSFSheet) wb.getSheet(sheetName);
			Row r = s.getRow(rowNum);
			Cell c = r.getCell(colNum);
			retVal = c.getStringCellValue();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return retVal;
	}

	// ********************************************************************************************************************
	// Generic function name :activeRowcount
	// Description :To count the active rows from excelsheet
	// ********************************************************************************************************************

	public int activeRowcount(String path, String sheetName) {
		int retVal = 0;
		try {
			FileInputStream fis = new FileInputStream(path);
			Workbook wb = WorkbookFactory.create(fis);
			XSSFSheet s = (XSSFSheet) wb.getSheet(sheetName);
			retVal = s.getLastRowNum();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retVal;
	}
	// ********************************************************************************************************************
	// Generic function name :writeToExcel
	// Description :To write the data in excelsheet
	// ********************************************************************************************************************

	public void writeToExcel(String path, String sheetName, int rowNum, int colNum, String desc) {
		try {
			FileInputStream fis = new FileInputStream(path);
			Workbook wb = WorkbookFactory.create(fis);
			XSSFSheet s = (XSSFSheet) wb.getSheet(sheetName);
			Row r = s.getRow(rowNum);
			Cell c = r.createCell(colNum);
			c.setCellValue(desc);
			FileOutputStream fos = new FileOutputStream(path);
			wb.write(fos);
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getSheetCount() {
		int count = workbook.getNumberOfSheets();
		if (count == -1)
			return 0;
		else {
			return count;
		}
	}

	public String GetSheetName(int sheetID) {
		String sheetName = workbook.getSheetName(sheetID);
		return sheetName;
	}
	
	


	
}