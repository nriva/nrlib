package it.nrsoft.nrlib;

import static org.junit.Assert.*;

import java.util.Calendar;

import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.DateFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WriteException;

import org.junit.Test;

import it.nrsoft.nrlib.util.ExcelFile;

public class UnitTestExcel {

	@Test
	public final void test() throws WriteException {
		
		WritableFont fontItalic = new WritableFont(	WritableFont.ARIAL, 10);
		fontItalic.setItalic(true);
		
		WritableFont fontBold = new WritableFont(	WritableFont.ARIAL, 10, WritableFont.BOLD);		
		
		WritableFont fontBoldWhite = new WritableFont(	WritableFont.ARIAL,
													WritableFont.DEFAULT_POINT_SIZE,
													WritableFont.BOLD,
													false,
													UnderlineStyle.NO_UNDERLINE,
													Colour.WHITE);
		
		WritableCellFormat headerFormat1 = new WritableCellFormat();
		headerFormat1.setFont(fontBoldWhite);
		headerFormat1.setBackground(Colour.GREY_80_PERCENT);		
	
		
		WritableCellFormat cellFormat1 = new WritableCellFormat();
		cellFormat1.setFont(fontItalic);
		
		WritableCellFormat cellFormat2 = new WritableCellFormat();
		cellFormat2.setFont(fontBold);
		
		ExcelFile test = new ExcelFile("c:\\temp\\prova.xls","Foglio");

		assertTrue("create()", test.create());
		
		
		// Imposta il formato per l'intestazione
		test.getHeaderFormats().put("0:*", headerFormat1);
		
		// Imposta il formato per righe pari e dispari
		test.getCellFormats().put("@odd:*", cellFormat1);	// dispari
		test.getCellFormats().put("@even:*", cellFormat2);	// pari
		
		
		// Crea eccezione per la terza colonna data
		WritableCellFormat cfb = new WritableCellFormat(fontBoldWhite, DateFormats.DEFAULT);
		cfb.setBackground(Colour.RED);
		test.getCellFormats().put("*:2", cfb);
		
		
		WritableCellFormat cfb2 = test.getCellFormatBasic();
		

		// Scacchiera
//		test.getCellFormats().put("@even:@odd", headerFormat1);
//		test.getCellFormats().put("@odd:@even", headerFormat1);
		
		  

		test.addSheet();
		test.addSheet();
		test.setCurrentSheet(10);
		test.setCurrentSheet(-1);		// --> si deve posizionare sul foglio 0
		
		assertTrue("writeHeader()",test.writeHeader(0, 0, "Numero"));
		assertTrue("writeHeader()",test.writeHeader(0, 1, "Stringa"));
		assertTrue("writeHeader()",test.writeHeader(0, 2, "Data"));
		assertTrue("writeHeader()",test.writeHeader(0, 3, "Formula"));
		
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		
		assertTrue("writeNumber()",test.writeNumber(1, 0, 1));
		assertTrue("writeString()",test.writeString(1, 1, "Primo"));
		assertTrue("writeDate()",test.writeDate(1, 2, cal.getTime() ));
		assertTrue("writeFormula()",test.writeFormula(1, 3, "A2*2+1" ));
		
		cal.add(Calendar.YEAR, 1);
		
		assertTrue("writeNumber()",test.writeNumber(2, 0, 2));
		assertTrue("writeLineString()",test.writeString(2, 1, "Secondo"));
		assertTrue("writeDate()",test.writeDate(2, 2, cal.getTime()));
		assertTrue("writeFormula()",test.writeFormula(2, 3, "A3+1"));
		
		cal.add(Calendar.YEAR, 1);
		
		assertTrue("writeNumber()",test.writeNumber(3, 0, 3));
		assertTrue("writeLineString()",test.writeString(3, 1, "Terzo", cfb2));
		assertTrue("writeDate()",test.writeDate(3, 2, cal.getTime()));
		assertTrue("writeFormula()",test.writeFormula(3, 3, "A4*2+1"));
		
		cal.add(Calendar.YEAR, 1);
		
		assertTrue("writeNumber()",test.writeNumber(4, 0, 4));
		assertTrue("writeString()",test.writeString(4, 1, "Quarto"));
		assertTrue("writeDate()",test.writeDate(4, 2, cal.getTime()));
		assertTrue("writeFormula()",test.writeFormula(4, 3, "A5+1"));
		
		cal.add(Calendar.YEAR, 1);
		
		assertTrue("writeNumber()",test.writeNumber(5, 0, 5));
		assertTrue("writeString()",test.writeString(5, 1, "Quinto"));
		assertTrue("writeDate()",test.writeDate(5, 2, cal.getTime(), cfb2));
		assertTrue("writeFormula()",test.writeFormula(5, 3, "A6*2+1"));

		
		assertTrue("close", test.close());
	}

}
