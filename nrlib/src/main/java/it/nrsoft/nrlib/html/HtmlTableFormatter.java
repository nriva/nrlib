package it.nrsoft.nrlib.html;

import java.util.*;

/**
 * Classe che produce il codice HTML di una tabella di stringhe.
 * @author riva
 *
 */
public class HtmlTableFormatter {
	
	private List<String> headers = new LinkedList<String>();
	private List<String[]> rows = new LinkedList<String[]>();
	
	public String attrTable = "";
	public String attrBody = "";
	public String attrRow = "";
	public String attrHeader = "";
	public String attrHeaders = "";
	public String attrElem = "";
	
	/**
	 * Aggiunge uno header alla tabella.
	 * @param header
	 */
	public void addHeader(String header)
	{
		headers.add(header);
	}
	
	/**
	 * Aggiunge un elenco di header alla tabella.
	 * @param headers
	 */
	public void addHeaders(String[] headers)
	{
		for(String header : headers)
		{
			addHeader(header);
		}
	}
	/**
	 * Elimina tutti gli headers.
	 */
	public void clearHeaders()
	{
		headers.clear();
	}
	/**
	 * Aggiunge una riga alla tabella.
	 * @param row
	 */
	public void addRow(String[] row)
	{
		rows.add(row);
	}
	/**
	 * Elimina tutte le righe.
	 */
	public void clearRows()
	{
		rows.clear();
	}
	
	/**
	 * Ottiene il codice HTML della tabella.
	 * @return il codice HTML
	 */
	public String getHtml()
	{
		
		StringBuilder html=new StringBuilder("<table"+ (attrTable.equals("")?"":" " + attrTable) +">");
		if(headers.size()>0) {
			html.append("<thead" + (attrHeaders.equals("")?"":" " + attrHeaders) + ">");
			for(String header : headers)
				html.append("<th"+ (attrHeader.equals("")?"":" " + attrHeader) +">" + header + "</th>");
			html.append("</thead>");
		}
		if(rows.size()>0) {
			html.append("<tbody" + (attrBody.equals("")?"":" " + attrBody) + ">");
			for(String[] row : rows) {
				html.append("<tr"+ (attrRow.equals("")?"":" " + attrRow) +">");
				for(String value : row)
					html.append("<td"+ (attrElem.equals("")?"":" " + attrElem) +">" + value + "</td>");
				html.append("</tr>");
			}
			html.append("</tbody>");
		}
		html.append("</table>"); 
		return html.toString();
	}

}
