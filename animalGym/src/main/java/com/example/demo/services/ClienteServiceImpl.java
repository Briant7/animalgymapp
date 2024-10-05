package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.IClienteDao;
import com.example.demo.entity.Cliente;
import com.example.demo.entity.Periodo;

@Service
public class ClienteServiceImpl implements IClienteService {
	
	@Autowired
	private IClienteDao clienteDao;

	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll() {
		
		return (List<Cliente>) clienteDao.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<Cliente> findAll(Pageable pageable) {
		return clienteDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente findById(Long id) {
		return clienteDao.findById(id).orElse(null);
	}

	@Override
	public Cliente save(Cliente cliente) {
		return clienteDao.save(cliente);
	}

	@Override
	public void delete(Long id) {
		clienteDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Periodo> findAllPeriodos() {
		return clienteDao.findAllPeriodos();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Cliente> findAllClientesVencidos(Pageable pageable) {
		return clienteDao.findAllClientesVencidos(pageable);
	}

	@Override
	public Cliente findByNumControl(String numControl) {
		return clienteDao.findByNumControl(numControl);
	}

	@Override
	public Cliente findByTelefono(String telefono) {
		return clienteDao.findByTelefono(telefono);
	}

	@Override
	public int findCountClientesVencidos() {
		return clienteDao.findCountClientesVencidos();
	}

	@Override
	public int findCountClientesActivos() {
		return clienteDao.findCountClientesActivos();
	}

	@Override
	public int findCountClientesTotal() {
		return clienteDao.findCountClientesTotal();
	}

	@Override
	public int findByNumeroControl(String numControl) {
		return clienteDao.findByNumeroControl(numControl);
	}

	@Override
	public int findCountClientesHoyRegistros() {
		return clienteDao.findCountClientesHoyRegistros();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Cliente> findAllClientesRegistros(Pageable pageable) {
		return clienteDao.findAllClientesRegistros(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Cliente> findAllClientesActivos(Pageable pageable) {
		return clienteDao.findAllClientesActivos(pageable);
	}
	
}
