package com.example.demo.services;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileServiceImpl implements IUploadFileService{
	
	private final Logger log = LoggerFactory.getLogger(IUploadFileService.class);
	
	private final static String DIRECTORIO_UPLOAD = "uploads";

	@Override
	public Resource cargar(String nombreFoto) throws MalformedURLException {		
		Path rutaArhivo = getPath(nombreFoto);
		log.info(rutaArhivo.toString());
		Resource recurso = new UrlResource(rutaArhivo.toUri());
		
		if(!recurso.exists() && !recurso.isReadable()) {
			rutaArhivo = Paths.get("src/main/resources/static/images").resolve("no-usuario.png").toAbsolutePath();
		
			recurso = new UrlResource(rutaArhivo.toUri());
			
			log.error("Error no se pudo cargar la imagen: " + nombreFoto);
		}
		
		if(nombreFoto.equals("gatito")) {
			rutaArhivo = Paths.get("src/main/resources/static/images").resolve("gatito.gif").toAbsolutePath();
		
			recurso = new UrlResource(rutaArhivo.toUri());
		}
		
		return recurso;
	}

	@Override
	public String copiar(MultipartFile archivo) throws IOException {
		String nombreArchivo = UUID.randomUUID().toString() + "_" + archivo.getOriginalFilename().replace(" ", "");
		Path rutaArhivo = getPath(nombreArchivo);
		log.info(rutaArhivo.toString());
		
		Files.copy(archivo.getInputStream(), rutaArhivo);
		
		return nombreArchivo;
	}
	
	@Override
	public String copiarQr(MultipartFile archivo) throws IOException {
		String nombreArchivo = UUID.randomUUID().toString() + "_" + archivo.getOriginalFilename().replace(" ", "");
		Path rutaArhivo = getPath(nombreArchivo);
		log.info(rutaArhivo.toString());
		
		return nombreArchivo;
	}

	@Override
	public boolean eliminar(String nombreFoto) {
		if (nombreFoto != null && nombreFoto.length() > 0) {
			Path rutaFotoAnterior = Paths.get("uploads").resolve(nombreFoto).toAbsolutePath();
			File archivoFotoAnterior = rutaFotoAnterior.toFile();
			if (archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()) {
				archivoFotoAnterior.delete();
				return true;
			}
		}
		return false;
	}

	@Override
	public Path getPath(String nombreFoto) {
		return Paths.get(DIRECTORIO_UPLOAD).resolve(nombreFoto).toAbsolutePath();
	}

}
