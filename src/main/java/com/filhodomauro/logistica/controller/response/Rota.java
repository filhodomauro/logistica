package com.filhodomauro.logistica.controller.response;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author maurofilho
 *
 */
public class Rota {

	private List<String> trechos = new LinkedList<String>();
	private Double custo = 0d;
	private Integer distancia = 0;
	
	public List<String> getTrechos() {
		return trechos;
	}
	public void setTrechos(List<String> trechos) {
		this.trechos = trechos;
	}
	public Double getCusto() {
		return custo;
	}
	public void setCusto(Double custo) {
		this.custo = custo;
	}
	public Integer getDistancia() {
		return distancia;
	}
	public void setDistancia(Integer distancia) {
		this.distancia = distancia;
	}
	
	public Rota clone(){
		Rota rota = new Rota();
		rota.custo = this.custo;
		rota.distancia = this.distancia;
		rota.trechos = new LinkedList<String>(this.trechos);
		return rota;
	}
	
	public void addTrecho(String trecho, Integer distancia){
		this.distancia += distancia;
		this.trechos.add(trecho);
	}
}
