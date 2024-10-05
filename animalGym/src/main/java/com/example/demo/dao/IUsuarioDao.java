package com.example.demo.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Role;
import com.example.demo.entity.Usuario;

public interface IUsuarioDao extends JpaRepository<Usuario, Long>{
	
	@Query("SELECT u FROM Usuario u WHERE u.username != 'admin'")
	public Page<Usuario> findAllUsers(Pageable pageable);
	
	public Usuario findByUsername(String username);
	
	@Query("from Role")
	public List<Role> findAllRoles();
	
	@Query("SELECT r FROM Role r WHERE r.nombre = ?1")
	public Role findByNombre(String nombre);
	
}