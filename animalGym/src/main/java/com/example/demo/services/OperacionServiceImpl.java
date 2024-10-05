package com.example.demo.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.dao.IOperacionesDao;
import com.example.demo.entity.Operacion;

@Service
public class OperacionServiceImpl implements IOperacionesService {
	
	@Autowired
	private IOperacionesDao operacionesDao;

	@Override
	public Page<Operacion> findAll(Pageable pageable) {
		return operacionesDao.findAll(pageable);
	}

	@Override
	public Operacion findById(Long id) {
		return operacionesDao.findById(id).orElse(null);
	}

	@Override
	public Operacion save(Operacion operacion) {
		return operacionesDao.save(operacion);
	}

	@Override
	public void delete(Long id) {
		operacionesDao.deleteById(id);
	}

	@Override
	public void deleteOperacion(Date date) {
		operacionesDao.deleteOperacion(date);
	}

}
