package com.example.demo.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.entity.Cliente;
import com.example.demo.entity.Periodo;

public interface IClienteService {
	
	public List<Cliente> findAll();
	
	public Page<Cliente> findAll(Pageable pageable);
	
	public Page<Cliente> findAllClientesRegistros(Pageable pageable);
	
	public Cliente findById(Long id);
	
	public Cliente save(Cliente cliente);
	
	public void delete(Long id);
	
	public List<Periodo> findAllPeriodos();
	
	public Page<Cliente> findAllClientesVencidos(Pageable pageable);
	
	public Page<Cliente> findAllClientesActivos(Pageable pageable);
	
	public Cliente findByNumControl(String id);
	
	public Cliente findByTelefono(String telefono);
	
	public int findCountClientesVencidos();
	
	public int findCountClientesActivos();
	
	public int findCountClientesTotal();
	
	public int findCountClientesHoyRegistros();
	
	public int findByNumeroControl(String numControl);

}
