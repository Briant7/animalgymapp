package com.example.demo.services;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.entity.Historial;

public interface IHistorialService {
	
	public Page<Historial> findAll(Pageable pageable);
	
	public Historial save(Historial historial);
	
	public int findClientesVisitas();
	
	public void deleteHistorial(Date fecha);

}
