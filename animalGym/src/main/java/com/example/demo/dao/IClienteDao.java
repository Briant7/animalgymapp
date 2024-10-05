package com.example.demo.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Cliente;
import com.example.demo.entity.Periodo;

public interface IClienteDao extends JpaRepository<Cliente, Long>{
	
	@Query("from Periodo")
	public List<Periodo> findAllPeriodos();
	
	@Query("from Cliente c where c.fechaFin <= CURDATE()")
	public Page<Cliente> findAllClientesVencidos(Pageable pageable);
	
	@Query("from Cliente c where c.fechaFin > CURDATE()")
	public Page<Cliente> findAllClientesActivos(Pageable pageable);
	
	@Query("from Cliente c where c.fechaRegistro = CURDATE()")
	public Page<Cliente> findAllClientesRegistros(Pageable pageable);
	
	@Query("SELECT COUNT(*) FROM Cliente c where c.fechaFin <= CURDATE()")
	public int findCountClientesVencidos();
	
	@Query("SELECT COUNT(*) FROM Cliente c where c.fechaFin > CURDATE()")
	public int findCountClientesActivos();
	
	@Query("SELECT COUNT(*) FROM Cliente c where c.fechaRegistro = CURDATE()")
	public int findCountClientesHoyRegistros();
	
	@Query("SELECT COUNT(*) FROM Cliente")
	public int findCountClientesTotal();
	
	@Query("SELECT c FROM Cliente c WHERE c.numControl = ?1")
	public Cliente findByNumControl(String numControl);
	
	@Query("SELECT c FROM Cliente c WHERE c.telefono = ?1")
	public Cliente findByTelefono(String telefono);

	@Query("SELECT COUNT(*) FROM Cliente c WHERE c.numControl = ?1")
	public int findByNumeroControl(String numControl);
}
