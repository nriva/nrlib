package it.nrsoft.nrlib.util.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelAPIWrapperPOI extends ExcelAPIWrapper {
	
	private Workbook workbook;
	private Sheet currentSheet;
	private CellStyle defaultStyle=null;
	
	/**
	 * @return the currentSheet
	 */
	public Sheet getCurrentSheet() {
		return currentSheet;
	}




	ExcelAPIWrapperPOI(String apiname)
	{
		if(apiname.endsWith("X"))
			workbook = new XSSFWorkbook();
		else
			workbook = new HSSFWorkbook();
	}
	
	
	
	
	/**
	 * @return the wb
	 */
	public Workbook getWorkbook() {
		return workbook;
	}




	public Cell getCell(int row, int col)
	{
        Row _row = currentSheet.getRow(row);
        if(_row == null)
        	_row = currentSheet.createRow(row);
        Cell cell = _row.getCell(col);
        if(cell==null)
        	cell = _row.createCell(col);
        
        return cell;
		
	}
	
	
	public boolean setStyle(String name,CellStyle style)
	{
		styles.put(name, style);
		return true;
	}
	
	@Override
	public boolean setCellStyle(int row,int col,String styleName)
	{
		Cell cell = getCell(row,col);
		CellStyle style = (CellStyle)getCellStyle(row, col); 
		cell.setCellStyle(style);		
		return true;
	}
	
	

	@Override
	public boolean setCell(int row, int col, String text) {
        Cell cell = getCell(row,col);
        cell.setCellValue(text);
		return true;
	}

	@Override
	public boolean setCell(int row, int col, Calendar calendar) {
        Cell cell = getCell(row,col);
        cell.setCellValue(calendar);
		return true;
	}

	@Override
	public boolean setCell(int row, int col, double number) {
        Cell cell = getCell(row,col);
        cell.setCellValue(number);
		return true;
	}

	@Override
	public boolean setCell(int row, int col, int number) {
        Cell cell = getCell(row,col);
        cell.setCellValue(number);
		return true;
	}

	@Override
	public boolean addSheet(String name) {
        currentSheet = workbook.createSheet(name);
		return true;
	}

	@Override
	public boolean saveTo(String filename) throws IOException {
        FileOutputStream out = new FileOutputStream(filename);
        workbook.write(out);
        out.close();
		return true;
	}
	
	@Override
	public boolean loadFrom(String filename) throws IOException
	{
		FileInputStream file = new FileInputStream(new File(filename));
		
		String extension = filename.substring(filename.lastIndexOf('.')); 
        
		if("xls".equals(extension))
			workbook = new HSSFWorkbook(file);
		else
			workbook = new XSSFWorkbook(file);
		workbook.setActiveSheet(0);
		return true;
		
	}




	@Override
	protected Object getDefaultCellStyle() {
		if(defaultStyle==null)
			defaultStyle =  workbook.createCellStyle();
		return defaultStyle;
	}

}
