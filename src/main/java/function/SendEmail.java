package function;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
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
	private final String user = "techit.csula@gmail.com";
	private final String pass = ".Im0nx.W";

	public SendEmail() {

	}

	public boolean sendEmail(String emailTo, String subject, String detailText) {
		/*
		 * This function collects the necessary information ( destination, subject, and detailed text)
		 * and sends the email using a defaulted email. 
		 * This function returns false if sending the email fails and true if it works.
		 * 
		 */
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, pass);
			}
		});
		

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(user));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(emailTo));
			message.setSubject(subject);
			message.setText(detailText);

			Transport.send(message);

			System.out.println("Email sent!");

		} catch (MessagingException e) {
			return false;		}

		return true;
	}
}
