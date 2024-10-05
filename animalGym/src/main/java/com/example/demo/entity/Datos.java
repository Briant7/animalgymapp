package com.example.demo.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
//@Table(name = "datos")
public class Datos implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private int visitasHoy;
	
	private int inactivos;
	
	private int activos;
	
	private int total;
	
	private int registros;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getVisitasHoy() {
		return visitasHoy;
	}

	public void setVisitasHoy(int visitasHoy) {
		this.visitasHoy = visitasHoy;
	}

	public int getInactivos() {
		return inactivos;
	}

	public void setInactivos(int inactivos) {
		this.inactivos = inactivos;
	}

	public int getActivos() {
		return activos;
	}

	public void setActivos(int activos) {
		this.activos = activos;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getRegistros() {
		return registros;
	}

	public void setRegistros(int registros) {
		this.registros = registros;
	}

	@Override
	public String toString() {
		return "Datos [id=" + id + ", visitasHoy=" + visitasHoy + ", inactivos=" + inactivos + ", activos=" + activos
				+ ", total=" + total + "]";
	}

}
