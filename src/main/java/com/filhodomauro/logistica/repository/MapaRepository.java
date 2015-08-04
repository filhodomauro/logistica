package com.filhodomauro.logistica.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.filhodomauro.logistica.model.Mapa;

/**
 * 
 * @author maurofilho
 *
 */
public interface MapaRepository extends JpaRepository<Mapa, Long>{

	List<Mapa> findByNome(String nome);
}
