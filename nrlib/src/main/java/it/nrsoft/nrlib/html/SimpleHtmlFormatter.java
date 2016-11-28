package it.nrsoft.nrlib.html;

/**
 * Semplici formattazioni per produrre codice HTML.
 * @author riva
 *
 */
public class SimpleHtmlFormatter {
	
	/**
	 * Spazio non comprimibile.
	 */
	public final static String SPACE="&nbsp";
	
	/**
	 * Tag BR
	 */
	public final static String TAG_BR="<BR>";
	
	
	/**
	 * Aggiunge il tag &lt;i&gt; attorno al parametro value.
	 * @param value
	 * @return il valore di input con il tag italic.
	 */
	public String italic(String value)
	{
		return wrap("i",value);
	}

	/**
	 * Aggiunge il tag &lt;b&gt; attorno al parametro value.
	 * @param value
	 * @return il valore di input con il tag bold.
	 */	
	public String bold(String value)
	{
		return wrap("b",value);
	}
	
	
	private String wrap(String tag,String value)
	{
		return "<" + tag + ">" + value + "</" + tag + ">";
	}
	
	public String table(String[] headers,String[][] rows)
	{
		HtmlTableFormatter tf = new HtmlTableFormatter();
		tf.addHeaders(headers);
		for(String[] row : rows)
			tf.addRow(row);
		
		return tf.getHtml();
	}
	

}
