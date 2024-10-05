package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Producto;

public interface IProductoDao extends JpaRepository<Producto, Long>{

	@Query("select p from Producto p where p.nombre like %?1%")
	public List<Producto> findByNombre(String nomb);
	
	public List<Producto> findByNombreContainingIgnoreCase(String nomb);
	
	@Query("select p from Producto p where p.nombre like ?1%")
	public List<Producto> findByNombre1(String nomb);
	
	public List<Producto> findByNombreStartingWithIgnoreCase(String nomb);
	
	
}