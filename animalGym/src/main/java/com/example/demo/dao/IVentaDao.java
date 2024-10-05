package com.example.demo.dao;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.entity.Venta;

public interface IVentaDao extends CrudRepository<Venta, Long>{

}