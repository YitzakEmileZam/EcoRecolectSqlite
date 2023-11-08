package com.example.proyecto_ecorecolect_aedii.Outlook_Email;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.proyecto_ecorecolect_aedii.Entidades.*;


import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import com.example.proyecto_ecorecolect_aedii.R;

import org.apache.commons.io.IOUtils;

public class EmailSender {
        private static final String OUTLOOK_HOST = "smtp.office365.com";
        private static final int OUTLOOK_PORT = 587;
        private static final String GMAIL_HOST = "smtp.gmail.com";
        private static final int GMAIL_PORT = 587;
        private static final String OUTLOOK_USERNAME = "ecoRecolect@outlook.com"; // aquí pon tu correo de Outlook
        private static final String OUTLOOK_PASSWORD = "cwwckkickkck123456"; // aquí pon la contraseña de ese correo de Outlook
        private static final String GMAIL_USERNAME = "codemanhh78@gmail.com"; // aquí pon tu correo de Gmail
        private static final String GMAIL_PASSWORD = "x c c i l u x f q c i u l u o y"; // aquí pon la contraseña de ese correo de Gmail

    public static void sendEmail(String recipient, String subject, String body, Context context) {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            String host;
            int port;
            String username;
            String password;

            if (recipient.toLowerCase().endsWith("@gmail.com")) {
                host = GMAIL_HOST;
                port = GMAIL_PORT;
                username = GMAIL_USERNAME;
                password = GMAIL_PASSWORD;
            } else {
                host = OUTLOOK_HOST;
                port = OUTLOOK_PORT;
                username = OUTLOOK_USERNAME;
                password = OUTLOOK_PASSWORD;
            }

            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", port);

            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(username));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
                message.setSubject(subject);

                // Crea un mensaje multipart
                Multipart multipart = new MimeMultipart();

                // Primera parte: el cuerpo del mensaje
                BodyPart bodyPartText = new MimeBodyPart();
                bodyPartText.setText(body);
                multipart.addBodyPart(bodyPartText);

                // Segunda parte: la imagen adjunta
                BodyPart bodyPartImage = new MimeBodyPart();
                @SuppressLint("ResourceType") InputStream is = context.getResources().openRawResource(R.drawable.logo_empresa_ecorecolect_1);
                byte[] bytes = IOUtils.toByteArray(is); // Necesitas la biblioteca Apache Commons IO para esto
                DataSource dataSource = new ByteArrayDataSource(bytes, "image/jpeg");
                bodyPartImage.setDataHandler(new DataHandler(dataSource));
                bodyPartImage.setFileName("logo_empresa.jpg");
                multipart.addBodyPart(bodyPartImage);

                // Agrega las partes al mensaje
                message.setContent(multipart);

                Transport.send(message);
                System.out.println("Correo electrónico enviado exitosamente.");
            } catch (MessagingException | IOException e) {
                e.printStackTrace();
                System.out.println("Error al enviar el correo electrónico: " + e.getMessage());
            }
        }


}
