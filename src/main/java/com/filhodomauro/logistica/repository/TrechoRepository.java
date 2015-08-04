package com.filhodomauro.logistica.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.filhodomauro.logistica.model.Trecho;

/**
 * 
 * @author maurofilho
 *
 */
public interface TrechoRepository extends JpaRepository<Trecho, Long>{
	
	public Collection<Trecho> findByMapaNome(String nome);
	
	@Cacheable("byMapaPonto")
	@Query("select t from Trecho t where t.mapa.nome = :mapa and (t.origem = :ponto or t.destino = :ponto)")
	public List<Trecho> findByMapaAndPonto(@Param("mapa") String mapa, @Param("ponto") String ponto);
	
}
