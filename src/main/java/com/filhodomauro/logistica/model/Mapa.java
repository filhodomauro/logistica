package com.filhodomauro.logistica.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * @author maurofilho
 *
 */
@Entity
public class Mapa {

	@Id
    @GeneratedValue
    private Long id;
	
	@NotEmpty(message = "O nome do mapa é obrigatório")
	private String nome;
	
	@OneToMany(mappedBy = "mapa", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, targetEntity = Trecho.class)
	private Set<Trecho> trechos = new HashSet<Trecho>();
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Set<Trecho> getTrechos() {
		return trechos;
	}
	public void setTrechos(Set<Trecho> trechos) {
		this.trechos = trechos;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
}
