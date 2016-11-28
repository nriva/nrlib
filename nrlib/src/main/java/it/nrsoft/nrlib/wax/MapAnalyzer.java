package it.nrsoft.nrlib.wax;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import it.nrsoft.nrlib.io.FileSystemWalkerListener;

public class MapAnalyzer implements FileSystemWalkerListener {
	
	private PrintStream fout;
	private PrintStream ferr;
	
//	private Map<String,Integer> data = new TreeMap<String,Integer>();
	
	
	private Map<String,List<String>> files = new TreeMap<String,List<String>>(); 
	
	private int numberOfFiles = 0;
	
	public MapAnalyzer(PrintStream fout,PrintStream ferr)
	{
		this.fout = fout;
		this.ferr = ferr;
		
		files.put("err",new LinkedList<String>());
		files.put("npg",new LinkedList<String>());
	}
	
	public Map<String, List<String>> getFiles() {
		return files;
	}

	public int getNumberOfFiles() {
		return numberOfFiles;
	}

	public boolean check(String sNomeFile) throws ParserConfigurationException, SAXException, IOException
	{
		
		File fXmlFile = new File(sNomeFile);
		
		
		numberOfFiles++;
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		
		Document doc = dBuilder.parse(fXmlFile);
				
		//optional, but recommended
		//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
		doc.getDocumentElement().normalize();

				
		NodeList nList = doc.getElementsByTagName("page");
		if(nList.getLength()==0)
		{
			files.get("npg").add(sNomeFile);
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
				
				if(!files.containsKey(version))
					files.put(version,new LinkedList<String>());
				files.get(version).add(fXmlFile.getName());

			}
		}
		return true;		
	}
	


	@Override
	public void visitFile(File file) {
		if(file.getName().length()!=11)
			return;
		
//		fos.println("File: " + file.getAbsolutePath());

		try {
			check(file.getAbsolutePath());
		} catch (Exception e) {
			ferr.println("File: " + file.getAbsolutePath());
			files.get("err").add(file.getName());
			e.printStackTrace(ferr);
		}
		
	}
}
