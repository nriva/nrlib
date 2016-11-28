
package it.nrsoft.nrlib.util;

import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;

import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.Number;


import jxl.write.DateTime;
import jxl.write.Formula;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;


/**
 * Utility wrapper to manage Excel files.
 * @author riva
 */
public class ExcelFile {
	
	private static final String ODD_MARK = "@odd";

	private static final String EVEN_MARK = "@even";
	
	private static final String SHEET_NAME_DEF = "Sheet";

	static Logger logger = Logger.getLogger(ExcelFile.class.getName());
	
	private WritableFont fontBasic = null;
			//WritableFont.DEFAULT_POINT_SIZE,
//			8,
//			WritableFont.BOLD,
//			false,
//			UnderlineStyle.NO_UNDERLINE,
//			Colour.WHITE);
	
	
	private WritableCellFormat cellFormatBasic = null;
	
	
	
//	private WritableFont fontWhite = new WritableFont(	WritableFont.ARIAL,
//										//WritableFont.DEFAULT_POINT_SIZE,
//										8,
//										WritableFont.BOLD,
//										false,
//										UnderlineStyle.NO_UNDERLINE,
//										Colour.WHITE);
//
//	private WritableFont fontBlack = new WritableFont(	WritableFont.ARIAL,
//										//WritableFont.DEFAULT_POINT_SIZE,
//										8,
//										WritableFont.BOLD,
//										false,
//										UnderlineStyle.NO_UNDERLINE,
//										Colour.BLACK);	

	
	/**
	 * @return the cellFormatBasic
	 */
	public WritableCellFormat getCellFormatBasic() {
		return new WritableCellFormat(cellFormatBasic);
	}



	private WritableWorkbook myExcel = null;
	
	/**
	 * Current sheet
	 */
	private WritableSheet mySheet = null;
/**
	 * @return the sheetName
	 */
	public String getSheetName() {
		return sheetName;
	}


	/**
	 * @param sheetName the sheetName to set
	 */
//	public void setSheetName(String sheetName) {
//		this.sheetName = sheetName;
//	}



	//	private WritableCellFormat cfString;
//	private WritableCellFormat cfFloat;
//	private Label myLabel;
//	private Number myNumber;
	
	/**
	 * Used for sheet labels.
	 */
	private int sheetNum;
	private String excelName;

	private String sheetName = SHEET_NAME_DEF;
	
	private File excelFile;
	
	private Map<String,WritableCellFormat> cellFormats = new TreeMap<String,WritableCellFormat>();
	private Map<String,WritableCellFormat> headerFormats = new TreeMap<String,WritableCellFormat>();
	

	/**
	 * Basic constructor.
	 * @param filename name of the Excel file
	 */
	public ExcelFile(String excelName) {
		this(excelName,SHEET_NAME_DEF);
		

	}
	
	public ExcelFile(String excelName,String sheetName) {
		
		this.excelName = excelName;
		this.sheetName = sheetName;
		
		fontBasic = new WritableFont(WritableFont.ARIAL);
		
		cellFormatBasic = new WritableCellFormat();
		cellFormatBasic.setFont(fontBasic);	

	}	
	
	
	/**
	 * @return the cellFormats
	 */
	public Map<String, WritableCellFormat> getCellFormats() {
		return cellFormats;
	}

	/**
	 * @return the headerFormats
	 */
	public Map<String,WritableCellFormat> getHeaderFormats() {
		return headerFormats;
	}




	/**
	 * Create the excel file.
	 * This is the first method to call after the instance creation.
	 */
	public boolean create() {
		WorkbookSettings mySet = new WorkbookSettings();
		mySet.setEncoding("UTF-8");
		
		excelFile = new File(excelName);
		try {
			myExcel = Workbook.createWorkbook(excelFile, mySet);
		} catch (IOException e) {
			logger.fatal("Workbook creation",e);
			return false;
		}
		
		sheetNum = 1;
		addSheet();
		
//		 WritableFont wf = new WritableFont(WritableFont.ARIAL, 8, WritableFont.NO_BOLD);
//		 cfString = new WritableCellFormat(fontBasic);
		 
		 //cfFloat = new WritableCellFormat (wf, NumberFormats.FLOAT);
//		 cfFloat = new WritableCellFormat (fontBasic);
		 
//		myExcel.setColourRGB(Colour.GREY_80_PERCENT, 83, 83, 83);
//		myExcel.setColourRGB(Colour.RED, 207, 47, 68);
		return true;
	}
	
	
	/**
	 * Override default colour definition
	 * @param colour Jxl colour to be redefined
	 * @param r red
	 * @param g green
	 * @param b blue
	 */
	public void setColor(Colour colour,int r, int g,int b)
	{
		myExcel.setColourRGB(colour, r,g,b);		
	}

	
	/**
	 * Add a new sheet to the current excel file.
	 */
	public void addSheet() {
		String sName = sheetName + sheetNum;
		mySheet = myExcel.createSheet(sName, sheetNum);
		mySheet.getRow(0);
		
		logger.debug("new sheet created : " +sName);
		sheetNum++;
	}
	
	
	/**
	 * Set the current sheet.
	 * @param sheetNum the sheet number (starting from 0) 
	 */
	public void setCurrentSheet(int sheetNum)
	{
		
		int _sheetNum=sheetNum;
		if(_sheetNum<0)
			_sheetNum = 0;
		if(_sheetNum>=myExcel.getNumberOfSheets())
			_sheetNum=myExcel.getNumberOfSheets()-1;
		mySheet = myExcel.getSheet(_sheetNum);
	}

	
	/**
	 * Write the content and then close the file.
	 */
	public boolean close() {
		
		
		try {
			myExcel.write();
		} catch (IOException e) {
			logger.fatal("Writing excel",e);
			return false;
		}
		
		try {
			myExcel.close();
		} 	
		catch (WriteException e) 	{
			logger.fatal("Closing excel",e);
			return false;
		} 
		catch (IOException e) {
			logger.fatal("Closing excel",e);
			return false;
		}
		
		return true;
			
	}
	
	
	/**
	 * Add a string cell. The format is chosen with the map cellFormats.
	 * @param row Horizontal position
	 * @param col Vertical position
	 * @param label Cell content (a string)
	 * @return true if successful
	 */
	public boolean writeString(int row, int col, String label) {
		
		return writeString(row,col,label,getCellFormat(row,col,cellFormats));
	}
	
	
	/**
	 * Add a string cell.
	 * @param row Horizontal position
	 * @param col Vertical position
	 * @param label Cell content (a string)
	 * @param cellFormat Cell format
	 * @return true if successful
	 */
	public boolean writeString(int row, int col, String label, WritableCellFormat cellFormat) {
		
		Label myLabel = new Label(col, row, label, cellFormat);
		
		logger.debug("Adding new string cell [" + label + "]");
		return addCell(myLabel);

	}

	private boolean addCell(WritableCell myLabel) {
		try {
			
			mySheet.addCell(myLabel);
			
		} 	
		catch (RowsExceededException e)
		{	
			logger.fatal("Adding cell",e);
			return false;
		} 
		catch (WriteException e)
		{	
			logger.fatal("Adding cell",e);
			return false;
		}
		return true;
	}	

	
	/**
	 * Add a numeric cell. The format is chosen with the map cellFormats.
	 * @param row Horizontal position
	 * @param col Vertical position
	 * @param inNumber Cell content (a floating point number)
	 * @return true if successful
	 */
	public boolean writeNumber(int row, int col, double inNumber) {
		
		return writeNumber(row, col, inNumber, getCellFormat(row,col,cellFormats));


	}
	
	/**
	 * Add a numeric cell.
	 * @param row Horizontal position
	 * @param col Vertical position
	 * @param inNumber Cell content (a floating point number)
	 * @param cellFormat Cell format
	 * @return true if successful
	 */
	public boolean writeNumber(int row, int col, double inNumber, WritableCellFormat cellFormat) {
		

		Number myNumber = new Number(col, row, inNumber, cellFormat);
		logger.debug("Adding new numeric cell [" + inNumber + "]");
		return addCell(myNumber);
	}
	
	
	public boolean writeDate(int row, int col, Date date, WritableCellFormat cellFormat)
	{

		DateTime myDate = new DateTime(col, row, date, cellFormat);
		logger.debug("Adding new date cell [" + date + "]");
		return addCell(myDate);
	}
	

	public boolean writeDate(int row, int col, Date date)
	{
		return writeDate(row, col, date, getCellFormat(row,col,cellFormats));
	}
	
	
	public boolean writeFormula(int row, int col, String formula, WritableCellFormat cellFormat)
	{

		Formula myDate = new Formula(col, row, formula, cellFormat);
		logger.debug("Adding new date cell [" + formula + "]");
		return addCell(myDate);
	}
	

	public boolean writeFormula(int row, int col, String formula)
	{
		return writeFormula(row, col, formula, getCellFormat(row,col,cellFormats));
	}	
	
	
	/**
	 * Write column heading. The format is chosen with the map headerFormats.
	 * @param row
	 * @param col
	 * @param header
	 * @return true if successful
	 */
	public boolean writeHeader(int row, int col, String header, CellView cellView, int cellHeight) {
		
		return writeHeader(row,col,header, cellView, cellHeight, getCellFormat(row,col,headerFormats));
		
	}
	
	public boolean writeHeader(int row, int col, String header) {
		
		return writeHeader(row,col,header, null, 0, getCellFormat(row,col,headerFormats));
		
	}	
	
	
	/**
	 * Write column heading.
	 * @param row Horizontal position
	 * @param col Vertical position
	 * @param header Header content
	 * @param cellFormat Header format
	 * @return true if successful
	 */
	public boolean writeHeader(int row, int col, String header, CellView cellView, int cellHeight, WritableCellFormat cellFormat) {
		
		Label myLabel = new Label(col, row, header, cellFormat);
		logger.debug("new header created : " +header);
		
		addCell(myLabel);
		
		CellView cv = cellView;
		if(cv==null)
		{
			cv = new CellView();
			cv.setAutosize(true);
		}
		mySheet.setColumnView(col, cv);
		
		try 
		{
			if(cellHeight>0)
				mySheet.setRowView(row, cellHeight);
		} 
		catch (RowsExceededException e) 
		{
			logger.fatal("Writing header cell (setRowView)",e);
			return false;
		}
		
		return true;
	}
	
	
	
	/**
	 * Get standard cell format.
	 * @param Row row type (0, 1, 2)
	 * @return cell format
	 * @throws WriteException
	 */
	private WritableCellFormat getCellFormat(int row,int col,Map<String,WritableCellFormat> cellFormats)  {

		
		
		WritableCellFormat cellFormat=null;
		
		String parityRow = row%2==0?EVEN_MARK:ODD_MARK;
		String parityCol = col%2==0?EVEN_MARK:ODD_MARK;
		
		
		List<String> keyList = new ArrayList<String>();
		
		keyList.add(row + ":" + col);
		keyList.add(row + ":*");
		keyList.add("*:" + col);
		
		keyList.add(parityRow+":" + col);
		keyList.add(row + ":"+parityCol);
		
		keyList.add(parityRow+":"+parityCol);
		
		keyList.add(parityRow+":*");
		keyList.add("*:"+parityCol);
		
		
		for(String key : keyList)
			if(cellFormats.containsKey(key))
			{
				cellFormat = cellFormats.get(key);
				break;
			}
		
		if(cellFormat==null)
		{
			cellFormat = cellFormatBasic;
		}
		
		
		
//		if (Row == 0){
//			cellFormat.setFont(fontBlack);
//			cellFormat.setBackground(Colour.WHITE);
//			cellFormat.setWrap(true);
//			cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
//		}
//		if (Row == 1){
//			cellFormat.setFont(fontWhite);
//			cellFormat.setBackground(Colour.GREY_80_PERCENT);
//			cellFormat.setWrap(true);
//			cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
//		}
//		if (Row == 2){
//			cellFormat.setFont(fontWhite);
//			cellFormat.setBackground(Colour.RED);
//			cellFormat.setWrap(true);
//			cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
//		}

//		fontWhite = null;
//		fontBlack = null;
		
		return cellFormat;
	}
	
	
	
	/**
	 * Return the size of the file.
	 * @return file size
	 */
	public long getSize() {
		return excelFile.length(); 
	}
}
