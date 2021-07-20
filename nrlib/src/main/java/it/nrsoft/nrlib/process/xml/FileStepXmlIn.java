package it.nrsoft.nrlib.process.xml;


import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Consumer;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.Location;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import it.nrsoft.nrlib.process.DataRow;
import it.nrsoft.nrlib.process.InitialProperties;
import it.nrsoft.nrlib.process.ProcessData;
import it.nrsoft.nrlib.process.SimpleDataRow;
import it.nrsoft.nrlib.process.Step;
import it.nrsoft.nrlib.process.StepResult;
import it.nrsoft.nrlib.process.file.FileStep;
import it.nrsoft.nrlib.tuples.Pair;

public class FileStepXmlIn extends FileStep {
	
	public static final String PROP_XML_DATATAGS = "file.xml.dataTags";
	protected List<String[]> tags = new LinkedList<>();

	public FileStepXmlIn(String name, InitialProperties properties) {
		super(name, properties);
		
		String[] props = properties.getProperty(PROP_XML_DATATAGS).split(",");
		for(String prop: props) {
			
			String[] parts = prop.trim().split("@");
			tags.add(parts);
		}
	}
	
	

	@Override
	public StepResult execute() {
		
		XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
		XMLEventReader reader;
		
		StepResult result = new StepResult();
		
		int tagsRead = 0;
		DataRow row = new SimpleDataRow();
		
		try {
			reader = xmlInputFactory.createXMLEventReader(new FileInputStream(this.fileName));
        
	        ProcessData dataOut = new ProcessData(this.properties);
			result.setDataOut(dataOut );
			
			while (reader.hasNext()) {
			    XMLEvent nextEvent = reader.nextEvent();

			    if (nextEvent.isStartElement()) {
			        StartElement startElement = nextEvent.asStartElement();
			        
			        String tagName = startElement.getName().getLocalPart();
			        boolean found = false;
			        String attributeName = ""; 
			        
					for(String[] tag : this.tags) {
						if(tag[0].equalsIgnoreCase(tagName)) {
							found = true;
							if(tag.length>1) {
								attributeName = tag[1];
							}
						}
			        }
			        
			        if (found) {
			        	
			        	StringBuilder value = new StringBuilder();
			        	
			        	if(attributeName.length()>0  ) {
			        		value.append(startElement.getAttributeByName(new QName(attributeName)).getValue());
			        		row.put(getOutputFieldName(tagName + "_" + attributeName) , value.toString());
			        	} else {
			        	
			        	
				        	nextEvent = reader.nextEvent();
		
		                    while (!nextEvent.isEndElement()) {
		                        value.append(nextEvent.asCharacters().getData());
		                        nextEvent = reader.nextEvent();
		                    }
		                
		                    row.put(getOutputFieldName(tagName), value.toString());
			        	}
			        	
	                    
	                    tagsRead++;
	                    
	                    if(tagsRead==tags.size()) {

	                    	dataOut.addDataRow(row);
	                    	row = new SimpleDataRow();
	                    	tagsRead =0;
	                    }
			        	
			        	
			        }
			    }
			    
			    
			    
			    
			}
		
		} catch (FileNotFoundException | XMLStreamException e) {
			result.setErrorCode(1);
			result.setMessage(e.getMessage());
			e.printStackTrace();
		}
		
		
		
		return result;

	}

}
