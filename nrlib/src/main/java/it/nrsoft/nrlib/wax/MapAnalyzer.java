package it.nrsoft.nrlib.wax;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import it.nrsoft.nrlib.io.FileSystemWalkerListener;

public class MapAnalyzer implements FileSystemWalkerListener {
	
	private PrintStream fout;
	private PrintStream ferr;
	
//	private Map<String,Integer> data = new TreeMap<String,Integer>();
	
	
	private Map<String,List<String>> groupMap = new TreeMap<String,List<String>>(); 
	
	private int numberOfFiles = 0;
	private DocumentBuilder builder;
	private XPath xpath;
	
	public MapAnalyzer(PrintStream fout,PrintStream ferr) throws ParserConfigurationException
	{
		this.fout = fout;
		this.ferr = ferr;
		
		groupMap.put("err",new LinkedList<String>());
		groupMap.put("npg",new LinkedList<String>());
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		builder = factory.newDocumentBuilder();
		
		
		builder.setErrorHandler(new ErrorHandler() {

			@Override
			public void warning(SAXParseException exception) throws SAXException {
				
				logger.warning(exception.getMessage() + "\r\n" + exceptionToString(exception));
			}

			private String exceptionToString(SAXParseException exception) {
				StringWriter sw = new StringWriter();
				PrintWriter pw =new PrintWriter(sw); 
				exception.printStackTrace(pw);
				return pw.toString();
			}

			@Override
			public void error(SAXParseException exception) throws SAXException {
				logger.log( Level.SEVERE, exception.getMessage()+ "\r\n" + exceptionToString(exception));
			}

			@Override
			public void fatalError(SAXParseException exception) throws SAXException {
				logger.log( Level.SEVERE, exception.getMessage()+ "\r\n" + exceptionToString(exception));
			}
		});
		
		XPathFactory xPathfactory = XPathFactory.newInstance();
		xpath = xPathfactory.newXPath();
				
	}
	
	public Map<String, List<String>> getFiles() {
		return groupMap;
	}

	public int getNumberOfFiles() {
		return numberOfFiles;
	}

	
	private String[] values;

	
	private static class QueryExpression
	{
		public String elseGroup;
		public QueryExpression(XPathExpression expression, String targetGroup, String elseGroup, QName xpathQueryType) {
			super();
			this.expression = expression;
			this.targetGroup = targetGroup;
			this.elseGroup = elseGroup;
			this.xpathQueryType = xpathQueryType;
		}
		public XPathExpression expression;
		public String targetGroup;
		public QName xpathQueryType = XPathConstants.BOOLEAN;
	}
	
	private List<QueryExpression> expressions = new LinkedList<QueryExpression>();
	
	public void addBooleanQuery(String query, String targetGroup, String elseGroup) throws Exception
	{
		
		try {
			expressions.add( new QueryExpression(xpath.compile(query),targetGroup,elseGroup,XPathConstants.BOOLEAN));
		} catch (XPathExpressionException e) {
			throw new Exception(e);
		}		
	}
	
//	public void setStringQuery(String query, String[] values) throws Exception
//	{
//		xpathQueryType = XPathConstants.STRING;
//		this.values = values;
//		
//		try {
//			expr = xpath.compile(query);
//		} catch (XPathExpressionException e) {
//			throw new Exception(e);
//		}			
//	}
	
	
	
	
	public void check(String sNomeFile) throws Exception
	{
		numberOfFiles++;

		Document doc = builder.parse(sNomeFile);
		
		NodeList nList = doc.getElementsByTagName("page");
		if(nList.getLength()==0)
		{
			groupMap.get("npg").add(sNomeFile);
			return;
		}
		

		for(QueryExpression queryExp : expressions) {
		
			if(queryExp.xpathQueryType.equals(XPathConstants.BOOLEAN)) {
			 
				Boolean ret = (Boolean)queryExp.expression.evaluate(doc, queryExp.xpathQueryType);
				
				if(ret) {
					if(!groupMap.containsKey(queryExp.targetGroup))
						groupMap.put(queryExp.targetGroup,new LinkedList<String>());
					groupMap.get(queryExp.targetGroup).add(sNomeFile);
				}
				else
				{
					if(!groupMap.containsKey(queryExp.elseGroup))
						groupMap.put(queryExp.elseGroup,new LinkedList<String>());
					groupMap.get(queryExp.elseGroup).add(sNomeFile);
					
				}
			}
			else if(queryExp.xpathQueryType.equals(XPathConstants.STRING)) {
				String ret = (String)queryExp.expression.evaluate(doc, queryExp.xpathQueryType);
				
				if(Arrays.binarySearch(values, ret)<=0)
				{				
					if(!groupMap.containsKey(ret))
						groupMap.put(ret,new LinkedList<String>());
					groupMap.get(ret).add(sNomeFile);
				}
			}
		}
		
	}

	private void analyze(String sNomeFile)
			throws ParserConfigurationException, SAXException, IOException {
		
		File fXmlFile = new File(sNomeFile);
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		
		Document doc = dBuilder.parse(fXmlFile);
				
		//optional, but recommended
		//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
		doc.getDocumentElement().normalize();

				
		NodeList nList = doc.getElementsByTagName("page");
		if(nList.getLength()==0)
		{
			groupMap.get("npg").add(sNomeFile);
//			fos.println("No tag page!!!");
		}

		for (int temp = 0; temp < nList.getLength(); temp++) {

			Node nNode = nList.item(temp);
					
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;
				
				String version = eElement.getAttribute("version");
				if(version==null || "".equals(version))
					version = "3.0";
//				fos.println("version : " + version);
				
				if(!groupMap.containsKey(version))
					groupMap.put(version,new LinkedList<String>());
				groupMap.get(version).add(fXmlFile.getName());

			}
		}
	}
	
	
	private String exceptionToString(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw =new PrintWriter(sw); 
		e.printStackTrace(pw);
		return sw.toString();
	}	

	private static final Logger logger = Logger.getLogger(MapAnalyzer.class.getName());

	@Override
	public void visitFile(File file) {
		if(file.getName().length()!=11)
			return;
		
//		fos.println("File: " + file.getAbsolutePath());

		try {
			check(file.getAbsolutePath());
		} catch (Exception e) {
			
			groupMap.get("err").add(file.getAbsolutePath());
			
			logger.log(Level.SEVERE,"File: " + file.getAbsolutePath());
			logger.log(Level.SEVERE, e.getMessage() + "\r\n" + exceptionToString(e));
		}
		
	}


}
