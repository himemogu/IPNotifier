package org.himemogura.ipnotifier;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Created by pri on 2016/02/13.
 */
public class Mail {
	private static String user = "ffreedes7@gmail.com";
	private static String pswd = "mym3178ff";
	private static String toUser = "alapripon@gmail.com";

	public static void sebdMail(String subject, String body) {
		try {
			//以下メール送信
			final Properties property = new Properties();
			property.put("mail.smtp.host", "smtp.gmail.com");
			property.put("mail.host", "smtp.gmail.com");
			property.put("mail.smtp.port", "465");
			property.put("mail.smtp.socketFactory.port", "465");
			property.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

			// セッション
			final Session session = Session.getInstance(property, new javax.mail.Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(user, pswd);
				}
			});

			MimeMessage mimeMsg = new MimeMessage(session);

			mimeMsg.setSubject(subject, "utf-8");
			mimeMsg.setFrom(new InternetAddress(user));
			mimeMsg.setRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(toUser));

			final MimeBodyPart txtPart = new MimeBodyPart();
			txtPart.setText(body, "utf-8");
			final Multipart mp = new MimeMultipart();
			mp.addBodyPart(txtPart);
			mimeMsg.setContent(mp);

			// メール送信する。
			final Transport transport = session.getTransport("smtp");
			transport.connect(user, pswd);
			transport.sendMessage(mimeMsg, mimeMsg.getAllRecipients());
			transport.close();

		} catch (MessagingException e) {
			System.out.println("exception = " + e);
			Log.e(IPCheckService.TAG, "Mail Send MessagingException", e);
		} catch (Exception e) {
			Log.e(IPCheckService.TAG, "Mail Send Exception", e);
		} finally {
			System.out.println("finish sending email");
		}
	}
}
