package com.example.demo.services;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Cliente;

@Service
@Transactional
public class EmailService {

	@Autowired
	JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String email;
	
	
	public void sendListEmail(Cliente cliente, String image, String numControl) {
		MimeMessage message = javaMailSender.createMimeMessage();
		
		
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(email);
			helper.setTo(cliente.getCorreo());
			helper.setSubject("Bienvenido a Animal Gym");
			helper.setText(cliente.getNombre() + " tu numero de control es: " + numControl + 
					" y tu fecha de vencimiento es: " + new SimpleDateFormat("dd-MM-yyyy").format(cliente.getFechaFin())
					+ " \n Se adjunta un codigo QR para que tambien por medio de el puedas acceder a las instalaciones.");
			FileSystemResource file = new FileSystemResource(new File(image));
			helper.addAttachment(image, file);

			javaMailSender.send(message);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void sendListEmailToAdmin(String emailTo, String image, String numControl) {
		MimeMessage message = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(email);
			helper.setTo(emailTo);
			helper.setSubject("Notificación");
			helper.setText("Se ha realizado un nuevo registro");
			helper.setText("El numero de control del cliente es: " + numControl);
			FileSystemResource file = new FileSystemResource(new File(image));
			helper.addAttachment(image, file);

			javaMailSender.send(message);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void sendListEmail(String emailTo, String image) {
		MimeMessage message = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(email);
			helper.setTo(emailTo);
			helper.setSubject("Listado");
			helper.setText("Estimado cliente");

			FileSystemResource file = new FileSystemResource(new File(image));
			helper.addAttachment(image, file);

			javaMailSender.send(message);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void sendIngresoEmail(String emailTo, String nombre, String estatus, String numControl) {
		MimeMessage message = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(email);
			helper.setTo(emailTo);
			helper.setSubject("Se ha registrado un nuevo ingreso");
			helper.setText("Ha ingresado el cliente: " + nombre + " con estatus: " + estatus + " y numero de control: " + numControl);

			javaMailSender.send(message);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
