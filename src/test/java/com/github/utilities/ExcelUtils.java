package com.github.utilities;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.testng.Assert;

import com.sun.tools.sjavac.Log;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

@Slf4j
public class ExcelUtils {
	public Object[][] readAllDataSheet(String dirPath, String sheetName) {
		File file = new File(dirPath);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		XSSFWorkbook wb = null;
		try {
			wb = new XSSFWorkbook(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		XSSFSheet sheet = wb.getSheet(sheetName);
		
		
		int row = sheet.getLastRowNum();
		int col = sheet.getRow(1).getLastCellNum();
		System.out.println(row);
		System.out.println(col);
		Object[][] array = new Object[row][col];
		for(int r = 0; r < row; r++) {
			XSSFRow eachRow = sheet.getRow(r);
			for(int c = 0; c < col; c++) {
				XSSFCell cell = eachRow.getCell(c);
				//System.out.print(cell.getStringCellValue());
				array[r][c] = cell.getStringCellValue();
			}
			//System.out.println();
		}
		
		
		return array;
	}
	
	public XSSFCell readSingleCellData(String dirPath, String sheetName, int rowNo, int cellNo) {
		File file = new File(dirPath);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		XSSFWorkbook wb = null;
		try {
			wb = new XSSFWorkbook(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		XSSFSheet sheet = wb.getSheet(sheetName);
		return sheet.getRow(rowNo).getCell(cellNo);
		
	}
	
	
	public void writeDataToCell(String dirPath, String sheetName, String value) throws IOException {
		File file = new File(dirPath);
		FileInputStream fis = new FileInputStream(file);
		XSSFWorkbook wb  = new XSSFWorkbook(fis);
		XSSFSheet sheet = wb.getSheet(sheetName);
		sheet.getRow(0).getCell(0).setCellValue(value);
		fis.close();
		FileOutputStream fos = new FileOutputStream(file);
		wb.write(fos);
		fos.close();
		wb.close();
		
		
	}
	
	public void createXLAndWrite(String filePath, String sheetName, int rowNo, int cellNo, String value) throws IOException {
		File file = new File(filePath);
		FileOutputStream fos = new FileOutputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet newSheet = wb.createSheet();
		wb.setSheetName(wb.getSheetIndex(newSheet), sheetName);
		newSheet.createRow(rowNo).createCell(cellNo).setCellValue(value);
		Cell cell = wb.getSheet(sheetName).getRow(rowNo).getCell(cellNo);
		XSSFCellStyle style = wb.createCellStyle();
		
		style.setFillBackgroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
		style.setFillPattern(FillPatternType.BRICKS);
		cell.setCellStyle(style);
		//mylog.info("Background color of the cell set successfully");
		wb.write(fos);
		wb.close();
		fos.close();
		
		
	}
	
	
	public static void main(String[] args) throws IOException{
		ExcelUtils e = new ExcelUtils();
		e.writeDataToCell(Constants.NEW_REPONAMES, "repoNames", "Hi");
	}
	

}
