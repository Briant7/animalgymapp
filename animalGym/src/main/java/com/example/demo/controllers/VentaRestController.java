package com.example.demo.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.DetalleVenta;
import com.example.demo.entity.Producto;
import com.example.demo.entity.Usuario;
import com.example.demo.entity.Venta;
import com.example.demo.services.IUsuarioService;
import com.example.demo.services.UsuarioService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/animalgym")
public class VentaRestController {

	@Autowired
	private IUsuarioService userService;
	
	@Autowired
	private UsuarioService usuarioService;

	@GetMapping("/ventas/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public Venta mostrar(@PathVariable Long id) {
		return userService.findVentaById(id);
	}

	@DeleteMapping("/ventas/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		userService.deleteVenta(id);
	}

	@GetMapping("/ventas/filtrar-productos/{nomb}")
	public List<Producto> filtrarProductos(@PathVariable String nomb) {
		return userService.findProductoByNombre(nomb);
	}

	@PostMapping("/ventas")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Venta crear(@RequestBody Venta venta) {
		Usuario usuario = null;
		usuario = usuarioService.findByUsername(venta.getUser());
		venta.setUsuario(usuario);
		for (DetalleVenta detalle : venta.getDetalles()) {
			Producto producto = detalle.getProducto();
			producto = userService.findProductoById(producto.getId());
			Integer stock = producto.getStock();
			producto.setStock(stock - detalle.getCantidad());
			userService.saveProducto(producto);
		}
		return userService.saveVenta(venta);
	}

	@GetMapping("/productos/page/{page}")
	public Page<Producto> productos(@PathVariable Integer page) {
		Page<Producto> productos = userService.findProductoAll(PageRequest.of(page, 8));
		return productos;
	}

	@GetMapping("/productos/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public Producto mostrarProducto(@PathVariable Long id) {
		return userService.findProductoById(id);
	}

	@PostMapping("/productos")
	public ResponseEntity<?> crear(@Valid @RequestBody Producto producto) {
		Producto productoNuevo = null;
		Map<String, Object> response = new HashMap<>();
		try {
			productoNuevo = userService.saveProducto(producto);

		} catch (Exception e) {
			response.put("mensaje", "Error al insertar la base de datos");
			response.put("error", e.getMessage().concat(": "));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El producto se ha creado con exito");
		response.put("producto", productoNuevo);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/productos/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Producto producto, @PathVariable Long id) {
		
		Producto productoActual = userService.findProductoById(id);
		Map<String, Object> response = new HashMap<>();
		Producto productoActualizado = null;
		
		if(productoActual == null) {
			response.put("mensaje", "Error no se pudo editar el producto");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			productoActual.setNombre(producto.getNombre());
			productoActual.setDescripcion(producto.getDescripcion());
			productoActual.setPrecio(producto.getPrecio());
			productoActual.setStock(producto.getStock());
			
			productoActualizado = userService.saveProducto(productoActual);
			
		} 
		catch (Exception e) {
			response.put("mensaje", "Error al actualizar la base de datos");
			response.put("error", e.getMessage().concat(": "));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El producto se ha actualizado con exito");
		response.put("usuario", productoActualizado);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/productos/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteProducto(@PathVariable Long id) {
		userService.deleteProducto(id);
	}

}
