package com.filhodomauro.logistica.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.filhodomauro.logistica.controller.response.Rota;
import com.filhodomauro.logistica.model.Mapa;
import com.filhodomauro.logistica.model.Trecho;
import com.filhodomauro.logistica.repository.MapaRepository;
import com.filhodomauro.logistica.repository.TrechoRepository;
import com.filhodomauro.logistica.service.LogisticaService;

/**
 * 
 * @author maurofilho
 *
 */
@RestController
public class LogisticaController {
	
	@Autowired
	private LogisticaService logisticaService;
	@Autowired
	private MapaRepository mapaRepository;
	@Autowired
	private TrechoRepository trechoRepository;
	
	@RequestMapping(value = "/mapa", method = RequestMethod.POST)
	public ResponseEntity<?> criarMapa(@RequestBody @Valid Mapa mapa, BindingResult result){
		if(result.hasErrors()){
			List<String> messages = new ArrayList<String>();
			result.getAllErrors().forEach( o -> messages.add(o.getDefaultMessage()));
			return new ResponseEntity<List<String>>(messages, HttpStatus.BAD_REQUEST);
		}
		List<Mapa> mapas = mapaRepository.findByNome(mapa.getNome());
		if(mapas != null && !mapas.isEmpty()){
			mapas.forEach( m -> trechoRepository.delete(m.getTrechos()));
			mapaRepository.delete(mapas);
		}
		mapaRepository.save(mapa);
		for(Trecho trecho : mapa.getTrechos()){
			if(trecho.getMapa() == null){
				trecho.setMapa(mapa);
			}
		}
		trechoRepository.save(mapa.getTrechos());
		return new ResponseEntity<Mapa>(mapa, HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/rota/{nomeMapa}/{origem}/{destino}/{autonomia}/{valorLitro}", method = RequestMethod.GET)
	public ResponseEntity<?> getRota(@PathVariable String nomeMapa, 
									 @PathVariable String origem,
									 @PathVariable String destino,
									 @PathVariable Double autonomia,
									 @PathVariable Double valorLitro
									 ){
		Rota rota = logisticaService.recuperarRota(nomeMapa, origem, destino, autonomia, valorLitro);
		
		return new ResponseEntity<Rota>(rota, HttpStatus.OK);
	}
}
