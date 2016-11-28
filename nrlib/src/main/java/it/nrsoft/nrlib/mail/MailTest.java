package it.nrsoft.nrlib.mail;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import it.nrsoft.nrlib.mail.Mail;


public class MailTest {
	
	private static void usage()
	{
		System.out.println("MailTest <from> <to> <host> <port> <subject> <body>");
	}

	/**
	 * @param args
	 * @throws MessagingException 
	 * @throws AddressException 
	 */
	public static void main(String[] args) throws AddressException, MessagingException {
		
		
		if(args.length!=6)
		{
			usage();
			return;
		}
		
		
		Mail mail = new Mail();
		
		try {
		
		mail.prepare(args[0], args[1], args[2], args[3]);
		
		
		mail.setContentText(args[4], args[5]);

		mail.send();
		}
		catch(Exception e)
		{
			usage();
		}
		
//		Mail.sendHtmlEmail("nicola.riva1@gmail.com", "riva@cad.it", "mail.cadit.it", "", "HD 525446", "<H1>Ci sono novit�?</H1>");

	}

}
