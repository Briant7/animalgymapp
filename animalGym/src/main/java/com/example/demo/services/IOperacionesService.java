package com.example.demo.services;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.entity.Operacion;

public interface IOperacionesService {
	
	public Page<Operacion> findAll(Pageable pageable);
	
	public Operacion findById(Long id);
	
	public Operacion save(Operacion operacion);
	
	public void delete(Long id);
	
	public void deleteOperacion(Date date);

}
