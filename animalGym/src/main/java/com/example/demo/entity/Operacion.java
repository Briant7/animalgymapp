package com.example.demo.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "operaciones")
public class Operacion implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String username;
	
	@Column(name = "tipo_operacion")
	private String tipoOperacion;
	
	@Temporal(TemporalType.DATE)
	private Date fecha;
	
	private String hora;
	
	private String cliente;
	
	@Column(name = "cliente_actual")
	private String clienteActual;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_fin")
	private Date fechaFin;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "nueva_fecha")
	private Date nuevaFecFin;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTipoOperacion() {
		return tipoOperacion;
	}

	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getClienteActual() {
		return clienteActual;
	}

	public void setClienteActual(String clienteActual) {
		this.clienteActual = clienteActual;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Date getNuevaFecFin() {
		return nuevaFecFin;
	}

	public void setNuevaFecFin(Date nuevaFecFin) {
		this.nuevaFecFin = nuevaFecFin;
	}

	@Override
	public String toString() {
		return "Operacion [id=" + id + ", username=" + username + ", tipoOperacion=" + tipoOperacion + ", fecha="
				+ fecha + ", hora=" + hora + ", cliente=" + cliente + ", clienteActual=" + clienteActual + ", fechaFin="
				+ fechaFin + ", nuevaFecFin=" + nuevaFecFin + "]";
	}

}
