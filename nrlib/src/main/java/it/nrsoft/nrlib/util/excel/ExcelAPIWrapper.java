package it.nrsoft.nrlib.util.excel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public abstract class ExcelAPIWrapper {
	
	private static final String ODD_MARK = "@odd";

	private static final String EVEN_MARK = "@even";
	
	protected Map<String,Object> styles = new TreeMap<String,Object>();	
	
	public  abstract boolean setCell(int row,int col,String text);
	public  abstract boolean setCell(int row,int col,Calendar calendar);
	public  abstract boolean setCell(int row,int col,double number);
	public  abstract boolean setCell(int row,int col,int number);
	
	
	public abstract boolean addSheet(String name);
	
	public abstract boolean saveTo(String filename) throws IOException;
	public abstract boolean loadFrom(String filename) throws IOException;
	
	public abstract boolean setCellStyle(int row,int col,String styleName);
	
	protected Object getCellStyle(int row,int col)  {
		
		
		
		Object style=null;
		
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
			if(styles.containsKey(key))
			{
				style = styles.get(key);
				break;
			}
		
		if(style==null)
		{
			style = getDefaultCellStyle();
		}
		
		
		
		
		return style;
	}
	
	protected abstract Object getDefaultCellStyle();
}
