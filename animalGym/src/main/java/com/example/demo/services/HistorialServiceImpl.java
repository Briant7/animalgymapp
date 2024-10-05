package com.example.demo.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.IHistorialDao;
import com.example.demo.entity.Historial;

@Service
public class HistorialServiceImpl implements IHistorialService {
	
	@Autowired
	private IHistorialDao historialDao;

	@Override
	@Transactional(readOnly = true)
	public Page<Historial> findAll(Pageable pageable) {
		return historialDao.findAll(pageable);
	}

	@Override
	public Historial save(Historial historial) {
		return historialDao.save(historial);
	}

	@Override
	public int findClientesVisitas() {
		return historialDao.findClientesVisitas();
	}

	@Override
	public void deleteHistorial(Date fecha) {
		historialDao.deleteHistorial(fecha);
	}

}
