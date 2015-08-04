package com.filhodomauro.logistica.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @author maurofilho
 *
 */
@Entity
public class Trecho {
	
	@Id
    @GeneratedValue
    private Long id;
	
	@ManyToOne
	@JoinColumn(name = "id_mapa")
	@JsonIgnore
	private Mapa mapa;

	private String origem;
	private String destino;
	private Integer distancia;
	
	public String getOrigem() {
		return origem;
	}
	public void setOrigem(String origem) {
		this.origem = origem;
	}
	public String getDestino() {
		return destino;
	}
	public void setDestino(String destino) {
		this.destino = destino;
	}
	public Integer getDistancia() {
		return distancia;
	}
	public void setDistancia(Integer distancia) {
		this.distancia = distancia;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Mapa getMapa() {
		return mapa;
	}
	public void setMapa(Mapa mapa) {
		this.mapa = mapa;
	}
	
	public String getOutroPonto(String ponto){
		return this.origem.equals(ponto) ? this.destino : this.origem;
	}
	
}
