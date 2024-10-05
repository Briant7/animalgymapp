package com.example.demo.dao;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.entity.Usuario;


public interface IUserDao extends CrudRepository<Usuario, Long>{

}