package com.team4.quanliquanmicay.util;

import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

import java.io.File;
import java.util.Properties;

/**
 * Simple email utility for sending reports with attachment.
 */
public final class Xmail {

	private Xmail() {}

	/**
	 * Send an email with a single attachment.
	 * Uses environment variables for credentials:
	 * - APP_MAIL_USERNAME
	 * - APP_MAIL_PASSWORD
	 * Optional overrides:
	 * - APP_MAIL_HOST (default smtp.gmail.com)
	 * - APP_MAIL_PORT (default 587)
	 * Optional debug:
	 * - APP_MAIL_DEBUG=true
	 */
	public static void sendEmailWithAttachment(String to, String subject, String body, File attachment) throws Exception {
		if (to == null || to.trim().isEmpty()) {
			throw new IllegalArgumentException("Email người nhận trống");
		}
		if (attachment == null || !attachment.exists()) {
			throw new IllegalArgumentException("Tập tin đính kèm không tồn tại");
		}

		final String username = System.getenv().getOrDefault("APP_MAIL_USERNAME", "khoaphan180806@gmail.com");
		final String password = System.getenv().getOrDefault("APP_MAIL_PASSWORD", "fsvk kifr llfz obzb");
		if (username.isEmpty() || password.isEmpty()) {
			throw new IllegalStateException("Thiếu cấu hình email (APP_MAIL_USERNAME/PASSWORD)");
		}

		final String host = System.getenv().getOrDefault("APP_MAIL_HOST", "smtp.gmail.com");
		final String port = System.getenv().getOrDefault("APP_MAIL_PORT", "587");
		final String debug = System.getenv().getOrDefault("APP_MAIL_DEBUG", "false");

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);
		props.put("mail.debug", debug);

		Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		MimeBodyPart textPart = new MimeBodyPart();
		textPart.setText(body != null ? body : "", "UTF-8");

		MimeBodyPart filePart = new MimeBodyPart();
		filePart.attachFile(attachment);

		MimeMultipart multipart = new MimeMultipart();
		multipart.addBodyPart(textPart);
		multipart.addBodyPart(filePart);

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(username));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
		message.setSubject(subject != null ? subject : "");
		message.setContent(multipart);

		Transport.send(message);
	}
}
