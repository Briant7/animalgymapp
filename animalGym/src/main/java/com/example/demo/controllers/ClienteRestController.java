package com.example.demo.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.component.ClienteComponent;
import com.example.demo.entity.Cliente;
import com.example.demo.entity.Datos;
import com.example.demo.entity.Historial;
import com.example.demo.entity.Operacion;
import com.example.demo.entity.Periodo;
import com.example.demo.services.EmailService;
import com.example.demo.services.IClienteService;
import com.example.demo.services.IHistorialService;
import com.example.demo.services.IOperacionesService;
import com.example.demo.services.IUploadFileService;
import com.example.demo.services.QRCodeService;

@CrossOrigin(origins = { "http://localhost:4200", "https://animalgym.web.app", "http://localhost:8090" })
@RestController
@RequestMapping("/animalgym")
public class ClienteRestController {

	@Autowired
	private IClienteService clienteService;

	@Autowired
	private IHistorialService historialService;

	@Autowired
	private IUploadFileService uploadService;

	@Autowired
	private QRCodeService qrCodeService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private ClienteComponent component;
	
	@Autowired
	private IOperacionesService operaciones;

	@GetMapping("/telefono/{telefono}")
	public Cliente buscarPorTelefono(@PathVariable String telefono) {
		return clienteService.findByTelefono(telefono);
	}

	@GetMapping("/clientes")
	public List<Cliente> index() {
		return clienteService.findAll();
	}

	@GetMapping("/clientes/datos")
	public Datos datos() {
		Datos datos = new Datos();
		datos.setVisitasHoy(historialService.findClientesVisitas());
		datos.setInactivos(clienteService.findCountClientesVencidos());
		datos.setActivos(clienteService.findCountClientesActivos());
		datos.setTotal(clienteService.findCountClientesTotal());
		datos.setRegistros(clienteService.findCountClientesHoyRegistros());
		return datos;
	}

	@GetMapping("/clientes/vencidos/page/{page}")
	public Page<Cliente> clientesVencidos(@PathVariable Integer page) {
		return clienteService.findAllClientesVencidos(PageRequest.of(page, 8));
	}
	
	@GetMapping("/clientes/activos/page/{page}")
	public Page<Cliente> clientesActivos(@PathVariable Integer page) {
		return clienteService.findAllClientesActivos(PageRequest.of(page, 8));
	}

	@GetMapping("/historial/page/{page}")
	public Page<Historial> historial(@PathVariable Integer page) {
		historialService.deleteHistorial(restarDiasAFecha(3));
		
		Page<Historial> historial = historialService.findAll(PageRequest.of(page, 8));
		//Eliminar historial
		
		return historial;
	}

	@GetMapping("/clientes/page/{page}")
	public Page<Cliente> index(@PathVariable Integer page) {
		return clienteService.findAll(PageRequest.of(page, 8));
	}
	
	@GetMapping("/clientes/registros/{page}")
	public Page<Cliente> clientesRegistros(@PathVariable Integer page) {
		return clienteService.findAllClientesRegistros(PageRequest.of(page, 8));
	}
	
	@GetMapping("/operaciones/page/{page}")
	public Page<Operacion> obtenerOperaciones(@PathVariable Integer page) {
		operaciones.deleteOperacion(restarDiasAFecha(10));
		
		return operaciones.findAll(PageRequest.of(page, 8));
	}

	@GetMapping("/clientes/{id}")
	public ResponseEntity<?> verCliente(@PathVariable Long id) {
		Cliente cliente = null;
		Map<String, Object> response = new HashMap<>();
		try {
			cliente = clienteService.findById(id);

			boolean estatus = validarEstatus(cliente);
			cliente.setEstatus(estatus);

		} catch (Exception e) {
			response.put("mensaje", "Error al consultar la base de datos");
			response.put("error", e.getMessage().concat(": "));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
	}

	@GetMapping("/clientes/qr/{id}")
	public ResponseEntity<?> mostrarCliente(@PathVariable Long id) {
		Cliente cliente;
		Map<String, Object> response = new HashMap<>();
		try {

			cliente = clienteService.findById(id);

		} catch (Exception e) {
			System.out.println(e);
			response.put("mensaje", "Error al consultar la base de datos");
			response.put("error", e.getMessage().concat(": "));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
	}

	@GetMapping("/clientes/numero/control/{numcontrol}")
	public ResponseEntity<?> mostrarClienteNumControl(@PathVariable String numcontrol) {
		Cliente cliente = null;
		Map<String, Object> response = new HashMap<>();
		try {
			cliente = component.validarEstatus(numcontrol);
			if(cliente == null || Objects.isNull(cliente)) {
				System.out.println("CLIENTE ES NULLL: ");
				cliente = new Cliente();
				cliente.setExiste(false);
				cliente.setId((long) 0);
			}
			else {
				cliente.setExiste(true);
			}
		} catch (Exception e) {
			response.put("mensaje", "Error al consultar la base de datos");
			response.put("error", e.getMessage().concat(": "));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		System.out.println("CLIENTE1111: " + cliente);
		return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
	}
	
	@GetMapping("/clientes/enviar/{id}")
	public ResponseEntity<?> operaciones(@PathVariable Long id){
		Cliente cliente = null;
		Map<String, Object> response = new HashMap<>();
		try {
			System.out.println("ENTRA clientes enviar");
			cliente = component.realizarOperaciones(id);

		} catch (Exception e) {
			response.put("mensaje", "Error al consultar la base de datos");
			response.put("error", e.getMessage().concat(": "));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
	}

	@PostMapping("/clientes")
	public ResponseEntity<?> crear(@RequestBody Cliente cliente) {
		System.out.println("ENTRA A CREAR: " + cliente);
		Cliente clienteNuevo = null;
		Map<String, Object> response = new HashMap<>();
		try {
			String numControl = obtenerNumeroControl();		

			Date fechaFin = new Date();

			if (cliente.getFechaInicio() != null) {
				System.out.println("ENTRA A como ADMIN SUPER: " + cliente);
				cliente.setFechaInicio(sumarHoras(cliente.getFechaInicio()));
				cliente.setFechaFin(sumarHoras(cliente.getFechaFin()));
				System.out.println("FechaInicio: " + cliente.getFechaInicio());
			} else {
				System.out.println("ENTRA COMO USER: " + cliente);
				cliente.setFechaInicio(fechaFin);
				switch (cliente.getPeriodo().getPeriodo()) {
				case 7:
					fechaFin = sumarDiasAFecha(cliente.getPeriodo().getPeriodo(), cliente);
					break;
				case 15:
					fechaFin = sumarDiasAFecha(cliente.getPeriodo().getPeriodo(), cliente);
					break;
				case 30:
					fechaFin = sumarMesAFecha(1, cliente);
					break;
				case 60:
					fechaFin = sumarMesAFecha(2, cliente);
					break;
				case 90:
					fechaFin = sumarMesAFecha(3, cliente);
					break;
				case 180:
					fechaFin = sumarMesAFecha(6, cliente);
					break;
				case 365:
					fechaFin = sumarMesAFecha(12, cliente);
					break;
				default:
				}
				System.out.println("FechaFinSumar: " + fechaFin);
				cliente.setFechaFin(fechaFin);
			}
			System.out.println("Datos cliente al: " + cliente);
			cliente.setNumControl(numControl);
			cliente.setEstatus(true);

			clienteNuevo = clienteService.save(cliente);
			generarQR(clienteNuevo, numControl);
			
			String nombreCompleto = cliente.getNombre() + " " + cliente.getApellidos();
			guardarOperacion("Registro", cliente, nombreCompleto, fechaFin);
		} catch (Exception e) {
			response.put("mensaje", "Error al insertar la base de datos");
			response.put("error", e.getMessage().concat(": "));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El cliente se ha creado con exito");
		response.put("cliente", clienteNuevo);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@PutMapping("/clientes/{id}")
	public ResponseEntity<?> update(@RequestBody Cliente cliente, @PathVariable Long id) {

		System.out.println("Peticion Request: " + cliente);
		Cliente clienteActual = clienteService.findById(id);
		Map<String, Object> response = new HashMap<>();
		Cliente clienteActualizado = null;

		if (clienteActual == null) {
			response.put("mensaje", "Error no se pudo editar al cliente");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		try {
			clienteActual.setNombre(cliente.getNombre());
			clienteActual.setApellidos(cliente.getApellidos());
			clienteActual.setCorreo(cliente.getCorreo());
			System.out.println("FechaInicio: " + cliente.getFechaInicio());
			
			if (cliente.getUsername().equals("admin")) {
				System.out.println("ENTRA A ACTUALIZA COMO SUPER ADMIN");
				clienteActual.setFechaInicio(sumarHoras(cliente.getFechaInicio()));
				clienteActual.setFechaFin(sumarHoras(cliente.getFechaFin()));
				clienteActual.setEstatus(true);
				System.out.println("Cliente: " + clienteActual);
			} 
			else if (cliente.isEstatus() == false || cliente.getRoleUser().equals("ROLE_ADMIN")) {
				Date fechaActualizar = new Date();
				clienteActual.setPeriodo(cliente.getPeriodo());
				if(cliente.getRoleUser().equals("ROLE_USER") || cliente.getFechaFin().before(fechaActualizar)) {
					clienteActual.setFechaInicio(fechaActualizar);
				}
				
				switch (cliente.getPeriodo().getPeriodo()) {
				case 7:
					fechaActualizar = sumarDiasAFecha(cliente.getPeriodo().getPeriodo(), cliente);
					break;
				case 15:
					fechaActualizar = sumarDiasAFecha(cliente.getPeriodo().getPeriodo(), cliente);
					break;
				case 30:
					fechaActualizar = sumarMesAFecha(1, cliente);
					break;
				case 60:
					fechaActualizar = sumarMesAFecha(2, cliente);
					break;
				case 90:
					fechaActualizar = sumarMesAFecha(3, cliente);
					break;
				case 180:
					fechaActualizar = sumarMesAFecha(6, cliente);
					break;
				case 365:
					fechaActualizar = sumarMesAFecha(12, cliente);
					break;
				default:
				}

				clienteActual.setFechaFin(fechaActualizar);
				clienteActual.setEstatus(true);
			}

			clienteActualizado = clienteService.save(clienteActual);
			
			String nombreCompleto = clienteActualizado.getNombre() + " " + clienteActualizado.getApellidos();
			guardarOperacion("Modificación", cliente, nombreCompleto, cliente.getFechaFin());
		} catch (Exception e) {
			response.put("mensaje", "Error al actualizar la base de datos");
			response.put("error", e.getMessage().concat(": "));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El cliente se ha actualizado con exito");
		response.put("cliente", clienteActualizado);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@DeleteMapping("/clientes/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();

		try {
			Cliente cliente = clienteService.findById(id);
			String nombreFotoAnterior = cliente.getFoto();
			uploadService.eliminar(nombreFotoAnterior);

			clienteService.delete(id);

		} catch (Exception e) {
			response.put("mensaje", "Error al eliminar de la base de datos");
			response.put("error", e.getMessage().concat(": "));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El cliente se ha eliminado con exito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@PostMapping("/clientes/upload")
	public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Long id) {
		Map<String, Object> response = new HashMap<>();

		Cliente cliente = clienteService.findById(id);

		if (!archivo.isEmpty()) {
			String nombreArchivo = null;
			try {
				nombreArchivo = uploadService.copiar(archivo);
			} catch (IOException e) {
				response.put("mensaje", "Error al subir la imagen: ");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			String nombreFotoAnterior = cliente.getFoto();

			uploadService.eliminar(nombreFotoAnterior);

			cliente.setFoto(nombreArchivo);
			clienteService.save(cliente);
			response.put("cliente", cliente);
			response.put("mensaje", "Has subido correctamente la imagen: " + nombreArchivo);

		}

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@GetMapping("/uploads/img/{nombreFoto:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto) {

		Resource recurso = null;

		try {
			recurso = uploadService.cargar(nombreFoto);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		HttpHeaders cabecera = new HttpHeaders();
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"");

		return new ResponseEntity<Resource>(recurso, HttpStatus.OK);
	}

	@GetMapping("/clientes/periodos")
	public List<Periodo> listarPeriodos() {
		return clienteService.findAllPeriodos();
	}

	@GetMapping("/v1/qrcode")
	public void generateQRCode(javax.servlet.http.HttpServletResponse response, @RequestParam String text,
			@RequestParam(defaultValue = "350") int width, @RequestParam(defaultValue = "350") int height)
			throws Exception {

		String path = qrCodeService.generateQRCode(text, width, height);

		this.emailService.sendListEmail("alejandro12olea@gmail.com", path);
	}

	public void generarQR(Cliente cliente, String numControl) throws Exception {
		String text = Long.toString(cliente.getId());
		int width = 350;
		int height = 350;

		String path = qrCodeService.generateQRCode(text, width, height);

		if(cliente.getCorreo() != null) {
			this.emailService.sendListEmail(cliente, path, numControl);
		}else {
			this.emailService.sendListEmailToAdmin( "briantvazquez@gmail.com", path, numControl);
		}
		
	}

	public static Date sumarDiasAFecha(int dias, Cliente cliente) {
		System.out.println("ENTRA A SUMAR FECHA: " + cliente);
		Date fecha = new Date();
		if (dias == 0) {
			return fecha;
		}
		if(cliente.getRoleUser().equals("ROLE_ADMIN")) {
			if(cliente.getFechaFin() !=null && cliente.getFechaFin().after(fecha)) {
				fecha = cliente.getFechaFin();
			}
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		calendar.add(Calendar.DAY_OF_YEAR, dias);
		return calendar.getTime();
	}
	
	public static Date restarDiasAFecha(int dias) {
		Date fecha = new Date();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		calendar.add(Calendar.DAY_OF_YEAR, -dias);
		return calendar.getTime();
	}

	public static Date sumarMesAFecha(int meses, Cliente cliente) {
		Date fecha = new Date();
		if(cliente.getRoleUser().equals("ROLE_ADMIN")) {
			System.out.println("ENTRA como ROLE_ADMIN: " + cliente);
			if(cliente.getFechaFin() !=null && cliente.getFechaFin().after(fecha)) {
				System.out.println("FECHA FIN ES ANTERIOR: " + cliente);
				fecha = cliente.getFechaFin();
			}
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		calendar.add(Calendar.MONTH, meses);

		return calendar.getTime();
	}
	
	public static Date sumarHoras(Date fecha) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		calendar.add(Calendar.HOUR, 12);
		return calendar.getTime();
	}

	public boolean validarEstatus(Cliente cliente) {
		boolean estatus = true;
		Date fechaHoy = new Date();
		if (cliente.getFechaFin().equals(fechaHoy)) {
			System.out.println("Fecha del cliente fin es igual a hoy");
			estatus = false;
		} else if (fechaHoy.after(cliente.getFechaFin())) {
			System.out.println("Fecha del cliente fin es mayor a hoy");
			estatus = false;
		}
		return estatus;
	}

	public String obtenerNumeroControl() {
		double numCuatro = 1000 + Math.random() * 9000;
		int numControl = (int) numCuatro;
		String numCadena = String.valueOf(numControl);
		int contador = clienteService.findByNumeroControl(numCadena);
		if (contador > 0) {
			System.out.println("Ya se encuentra o es menor a 1000");
			obtenerNumeroControl();
		}
		return numCadena;
	}
	
	public void guardarOperacion(String tipoOperacion, Cliente cliente, String nombre, Date fechaFin) {
		Operacion operacion = new Operacion();
		operacion.setTipoOperacion(tipoOperacion);
		operacion.setUsername(cliente.getUsername());
		Date fechaHoy = new Date();
		operacion.setFecha(fechaHoy);
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		operacion.setHora(dateFormat.format(fechaHoy));
		operacion.setCliente(cliente.getNombre() + " " + cliente.getApellidos());
		operacion.setClienteActual(nombre);
		operacion.setFechaFin(cliente.getFechaFin());
		operacion.setNuevaFecFin(fechaFin);
		operaciones.save(operacion);
	}

}

