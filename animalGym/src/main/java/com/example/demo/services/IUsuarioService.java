package com.example.demo.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.entity.Producto;
import com.example.demo.entity.Role;
import com.example.demo.entity.Usuario;
import com.example.demo.entity.Venta;

public interface IUsuarioService {

	public Usuario findByUsername(String username);

	public List<Usuario> findAll();

	public Page<Usuario> findAll(Pageable pageable);

	public Usuario findById(Long id);

	public Usuario save(Usuario cliente);

	public void delete(Long id);
	
	public List<Role> findAllRoles();
	
	public Venta findVentaById(Long id);
	
	public Venta saveVenta(Venta venta);
	
	public void deleteVenta(Long id);
	
	public List<Producto> findProductoByNombre(String nomb);
	
	public Page<Producto> findProductoAll(Pageable pageable);
	
	public Producto saveProducto(Producto producto);
	
	public Producto findProductoById(Long id);
	
	public void deleteProducto(Long id);

}
