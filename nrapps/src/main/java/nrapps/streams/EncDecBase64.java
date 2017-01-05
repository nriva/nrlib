package nrapps.streams;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.mail.MessagingException;
import javax.mail.internet.MimeUtility;

import it.nrsoft.nrlib.argparser.ArgParser;
import it.nrsoft.nrlib.argparser.InvalidSwitchException;
import it.nrsoft.nrlib.io.StreamCopier;
import it.nrsoft.nrlib.notify.Notify;
import it.nrsoft.nrlib.pattern.Observer;


/**
 * Hello world!
 *
 */
public class EncDecBase64 
{
    public static void main( String[] args ) throws MessagingException, IOException
    {
    	
    	ArgParser parser = new ArgParser();
    	parser.addSwitchChar('-');
    	parser.setMinNumArgs(2);
    	parser.addSwitchDef(new String[]{"e","-encode"});
    	parser.addSwitchDef(new String[]{"d","-decode"});
    	
    	try {
			parser.parse(args);
	    	if(parser.getSwitches().containsKey("e"))
	    		encode(parser.getArguments().get(0),parser.getArguments().get(1));
	    	else if(parser.getSwitches().containsKey("d"))
	    		decode(parser.getArguments().get(0),parser.getArguments().get(1));
		} catch (InvalidSwitchException e) {
			System.out.println(parser.usage());
		}
    	


    }

	private static void encode(String inputFilename,String outputFilename) throws FileNotFoundException, MessagingException, IOException {
		InputStream is = new FileInputStream(inputFilename);
		
		OutputStream os = MimeUtility.encode(new FileOutputStream(outputFilename), "base64");
		
		StreamCopier sc = new StreamCopier();
		sc.getSubjectHelper().addObserver(new Observer() {
			
			@Override
			public void Update(Object subject) {

				System.out.println( ((Notify)subject).getMessage() );  
				
			}
		});
		
		sc.copy(is,os);
		
		is.close();
		os.close();
	}
	
	private static void decode(String inputFilename,String outputFilename) throws FileNotFoundException, MessagingException, IOException {
		
		
		InputStream is = MimeUtility.decode(new FileInputStream(inputFilename), "base64");
		
		OutputStream os = new FileOutputStream(outputFilename);
		
		StreamCopier sc = new StreamCopier();
		sc.getSubjectHelper().addObserver(new Observer() {
			
			@Override
			public void Update(Object subject) {

				System.out.println( ((Notify)subject).getMessage() );  
				
			}
		});		
		
		sc.copy(is,os);
		
		is.close();
		os.close();
	}	
}
