package com.example.demo.services;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileServiceImpl implements IUploadFileService{
	
	private final Logger log = LoggerFactory.getLogger(IUploadFileService.class);
	
	private final static String DIRECTORIO_UPLOAD = "src//main//resources//static/images";
	
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
	public String copiar(MultipartFile multipartFile) throws IOException {
		
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        //Establecemos el directorio donde se subiran nuestros ficheros  
        String uploadDir = DIRECTORIO_UPLOAD;
         
        guardarFile(uploadDir, fileName, multipartFile);
        return fileName;
	}

	public static void guardarFile(String uploadDir, String fileName,
	        MultipartFile multipartFile) throws IOException {
	    Path uploadPath = Paths.get(uploadDir);
	      
	    if (!Files.exists(uploadPath)) {
	        Files.createDirectories(uploadPath);
	    }
	      
	    try (InputStream inputStream = multipartFile.getInputStream()) {
	        Path filePath = uploadPath.resolve(fileName);
	        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
	    } catch (IOException ioe) {        
	        throw new IOException("Could not save image file: " + fileName, ioe);
	    }      
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

