package com.example.demo.services;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

@Service
public class QRCodeService {

	public String generateQRCode(String text, int width, int height) {
		String qrCodePath = "src/main/resources/static/images";
		String qrCodeName = qrCodePath + text + "-QRCODE.png";
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		try {
			BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
			Path path = FileSystems.getDefault().getPath(qrCodeName);
			MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
			return path.toString();
		} catch (WriterException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
