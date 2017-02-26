package function;

import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {
	/*
	 * This class is for sending email notifications to users
	 * of the techIT program. It uses TLS connection on port
	 * 587.
	 */
	public SendEmail() {}

	public boolean sendEmail(Session session, String emailFrom, String emailTo, String subject, String detailText) {
		/*
		 * This function collects the necessary information ( destination, subject, and detailed text)
		 * and sends the email using a defaulted email. 
		 * This function returns false if sending the email fails and true if it works.
		 * 
		 */
		

		try {
			
			Long timeNow = System.currentTimeMillis();

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(emailFrom));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(emailTo));
			message.setSubject(subject);
			message.setText(detailText);
			message.saveChanges();
			
			Transport.send(message);

			Long timeAfter = System.currentTimeMillis();
			Long finalTime = timeAfter - timeNow;
			
			System.out.println("Email sent!");
			System.out.println("Email sending time: " + finalTime);

		} catch (MessagingException e) {
			return false;		
		}

		return true;
	}
	
	public boolean sendMultipleEmail(Session session, Properties properties, String emailFrom, List<String> addressTo , String subject, String detailText){
		try{
			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(emailFrom));
			message.setSubject(subject);
			message.setText(detailText);
			message.saveChanges();
			
			
			Long timeNow = System.currentTimeMillis();
			Transport transport = session.getTransport("smtp");
			transport.connect(properties.getProperty("mail.smtp.host"),
					Integer.parseInt(properties.getProperty("mail.smtp.port")),
					properties.getProperty("mail.smtp.user"),
					properties.getProperty("mail.smtp.pass"));
			Address[] addresses = new Address[addressTo.size()];
			for(int i = 0; i < addressTo.size(); i ++){
				addresses[i] = new InternetAddress(addressTo.get(i));
			}
			transport.sendMessage(message, addresses);

			Long timeAfter = System.currentTimeMillis();
			Long finalTime = timeAfter - timeNow;
			
			System.out.println("Emails sent!");
			System.out.println("Emails sending time: " + finalTime);

		}catch(MessagingException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean sendMultipleEmail(Session session, String emailFrom, List<String> addressTo , String subject, String detailText){
		try {
			
			Long timeNow = System.currentTimeMillis();

			if(addressTo.size() > 0){
				for(int i = 0; i < addressTo.size(); i ++)
				{
					Message message = new MimeMessage(session);
					message.setFrom(new InternetAddress(emailFrom));
					message.setRecipients(Message.RecipientType.TO,
							InternetAddress.parse(addressTo.get(i)));
					message.setSubject(subject);
					message.setText(detailText);
					message.saveChanges();
					
					Transport.send(message);
				}
			}
			Long timeAfter = System.currentTimeMillis();
			Long finalTime = timeAfter - timeNow;
			
			System.out.println("Email sent!");
			System.out.println("Email sending time: " + finalTime);

		} catch (MessagingException e) {
			return false;		
		}

		return true;
	}
}
