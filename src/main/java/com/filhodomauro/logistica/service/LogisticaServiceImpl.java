package com.filhodomauro.logistica.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.filhodomauro.logistica.controller.response.Rota;
import com.filhodomauro.logistica.observer.LogisticaObservable;
import com.filhodomauro.logistica.repository.TrechoRepository;
import com.filhodomauro.logistica.service.job.RotaFinder;

/**
 * 
 * @author maurofilho
 *
 */
@Component
public class LogisticaServiceImpl implements LogisticaService{
	
	@Autowired
	private TrechoRepository trechoRepository;

	@Override
	public Rota recuperarRota(String nomeMapa, String origem, String destino, Double autonomia, Double valorLitro) {
		RotaFinder finder = new RotaFinder(new LogisticaObservable(), trechoRepository);
		Rota melhorRota = finder.findMelhorRota(nomeMapa, origem, destino);
		melhorRota.setCusto(calcularCusto(melhorRota.getDistancia(), autonomia, valorLitro));
		return melhorRota;
	}
	
	private Double calcularCusto(Integer distancia, Double autonomia, Double valorLitro){
		BigDecimal custo = 
				BigDecimal.valueOf(distancia)
					.divide(BigDecimal.valueOf(autonomia))
					.multiply(BigDecimal.valueOf(valorLitro));
		return custo.doubleValue();
	}

}
