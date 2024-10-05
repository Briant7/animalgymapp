package com.example.demo.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "ventas")
public class Venta implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nota;
	
	@Column(name = "fecha_venta")
	@Temporal(TemporalType.DATE)
	private Date fechaVenta;
	
	@Column(name = "hora_venta")
	@Temporal(TemporalType.TIME)
	private Date horaVenta;
	
	@JsonIgnoreProperties({"ventas", "hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY)
	//@JoinColumn(name="usuario_id")
	private Usuario usuario;
	
	private String user;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "venta_id")
	private List<DetalleVenta> detalles;
	
	public Venta() {
		detalles = new ArrayList<>();
	}

	@PrePersist
	public void prePersist() {
		this.fechaVenta = new Date();
		this.horaVenta = new Date();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNota() {
		return nota;
	}
	public void setNota(String nota) {
		this.nota = nota;
	}
	public Date getFechaVenta() {
		return fechaVenta;
	}
	public void setFechaVenta(Date fechaVenta) {
		this.fechaVenta = fechaVenta;
	}

	public Date getHoraVenta() {
		return horaVenta;
	}

	public void setHoraVenta(Date horaVenta) {
		this.horaVenta = horaVenta;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<DetalleVenta> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<DetalleVenta> detalles) {
		this.detalles = detalles;
	}

//	@Override
//	public String toString() {
//		return "Venta [id=" + id + ", nota=" + nota + ", fechaVenta=" + fechaVenta + ", horaVenta=" + horaVenta
//				+ ", usuario=" + usuario + ", detalles=" + detalles + "]";
//	}
	
	public Double getTotal() {
		Double total = 0.00;
		for(DetalleVenta detalle: detalles) {
			total += detalle.getImporte(); 
		}
		return total;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

}
