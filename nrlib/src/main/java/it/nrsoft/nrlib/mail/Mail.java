package it.nrsoft.nrlib.mail;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

import it.nrsoft.nrlib.util.StringUtil;


public class Mail {
	
	static Logger logger = Logger.getLogger(Mail.class.getName());

	public static final String MIME_TEXTHTML = "text/html";

	/**
	 * Metodo di utilit� per inviare una e-mail con contenuto HTML.
	 * @param from Indirizzo del mittente
	 * @param to Indirizzo del destinatario
	 * @param host Server smtp
	 * @param port Porta Smtp
	 * @param subject Oggetto
	 * @param html Contenuto HTML
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public static void sendHtmlEmail(String from, String to, String host,
			String port, String subject, String html)
			throws AddressException, MessagingException {
		
		logger.debug("Sending Html e-mail...");

		Mail mail = new Mail();
		mail.prepare(from, to, host, port);

		mail.setContentHtml(subject, html);

		mail.send();
	}
	

	/**
	 * Metodo di utilit� per inviare una e-mail con contenuto testo.
	 * @param from Indirizzo del mittente
	 * @param to Indirizzo del destinatario
	 * @param host Server smtp
	 * @param port Porta Smtp
	 * @param subject Oggetto
	 * @param text Contenuto testo
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public static void sendEmail(String from, String to, String host,
			String port, String subject, String text)
			throws AddressException, MessagingException {
		
		logger.debug("Sending text e-mail...");
		
		Mail mail = new Mail();
		mail.prepare(from, to, host, port);

		mail.setContentText(subject, text);

		// Send message
		mail.send();

	}
	
	/**
	 * Prepara il messaggio da inviare.
	 * @param from Indirizzo del mittente
	 * @param to Indirizzo del destinatario
	 * @param host Server smtp
	 * @param port Porta Smtp
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public void prepare(String from, String to, String host,String port) 
		throws AddressException, MessagingException {
		
		
		logger.debug(String.format("Preparing e-mail: from=%s to=%s host=%s port=%s", from,to,host,port));
	
		// Get system properties
		Properties properties = System.getProperties();
	
		// Setup mail server
		properties.setProperty("mail.smtp.host", host);
		if(port!=null && !"".equals(port))
			properties.setProperty("mail.smtp.port", port);
	
		// Get the default Session object.
		Session session = Session.getDefaultInstance(properties);
	
		// Create a default MimeMessage object.
		message = new MimeMessage(session);
	
		// Set From: header field of the header.
		message.setFrom(new InternetAddress(from));
	
		// Set To: header field of the header.
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
	
	}

	/**
	 * Imposta il contenuto testo
	 * @param subject Oggetto
	 * @param text Contenuto testo
	 * @throws MessagingException
	 */
	public void setContentText(String subject, String text)
			throws MessagingException {
		
		logger.debug("setContentText");
		logger.debug("subject [" + subject + "]");
		logger.debug("text [" + text + "]");
		
		// Set Subject: header field
		message.setSubject(subject);
		// Now set the actual message
		message.setText(text);
	}

	/**
	 * Imposta il contenuto HTML della e-mail
	 * @param subject Oggetto della e-mail
	 * @param html Contenuto HTML
	 * @throws MessagingException
	 */
	public void setContentHtml(String subject, String html)
			throws MessagingException {
		logger.debug("setContentHtml");
		logger.debug("subject [" + subject + "]");
		logger.debug("html [" + html + "]");
		
		
		// Set Subject: header field
		message.setSubject(subject);
	
		message.setContent(html, MIME_TEXTHTML);
	}

	/**
	 * Imposta il contenuto testo con file allegati
	 * @param subject Oggetto
	 * @param body Corpo della e-mail
	 * @param filenames Elenco di allegati
	 * @throws MessagingException
	 */
	public void setContentTextAttachments(String subject,String body, String[] filenames)
			throws MessagingException {
		setContentBodyAttachments(subject, body, null, filenames);

	}
	
	private void setContentBodyAttachments(String subject,String body, String bodyMimeType, String[] filenames)
			throws MessagingException {
		
		logger.debug("setContentBodyAttachments");
		logger.debug("subject [" + subject + "]");
		logger.debug("body [" + body + "]");
		logger.debug("bodyMimeType [" + bodyMimeType + "]");
		logger.debug("filenames [" + StringUtil.join(filenames, ",") + "]");
		
		// Set Subject: header field
		message.setSubject(subject);		

		// Create the message part
		MimeBodyPart messageBodyPart = new MimeBodyPart();

		// Fill the message
		if(bodyMimeType!=null && !bodyMimeType.equals(""))
			messageBodyPart.setContent(body, bodyMimeType);
		else
			messageBodyPart.setText(body);

		// Create a multipar message
		Multipart multipart = new MimeMultipart();

		// Set text message part
		multipart.addBodyPart(messageBodyPart);

		// Part two is attachment
		for (String filename : filenames) {
			messageBodyPart = new MimeBodyPart();
			DataSource source = new FileDataSource(filename);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);
		}

		// Send the complete message parts
		message.setContent(multipart);

	}
	
	
	/**
	 * Imposta il contenuto HTML con file allegati
	 * @param subject Oggetto
	 * @param html Contenuto HTML
	 * @param filenames Elenco di allegati
	 * @throws MessagingException
	 */
	public void setContentHtmlAttachments(String subject,String html, String[] filenames)
			throws MessagingException {
		
		setContentBodyAttachments(subject, html, MIME_TEXTHTML, filenames);

	}

	/**
	 * Invia l'email precedentemente impostata
	 * @throws MessagingException
	 */
	public void send() throws MessagingException {
	
		logger.debug("Sending e-mail...");
		// Send message
		Transport.send(message);
		logger.debug("Sent.");
	}

	private MimeMessage message;
	

}
