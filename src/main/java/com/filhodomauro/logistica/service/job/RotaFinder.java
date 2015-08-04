package com.filhodomauro.logistica.service.job;

import java.util.Observable;
import java.util.Observer;
import java.util.function.Predicate;

import com.filhodomauro.logistica.controller.response.Rota;
import com.filhodomauro.logistica.model.Trecho;
import com.filhodomauro.logistica.observer.LogisticaObservable;
import com.filhodomauro.logistica.repository.TrechoRepository;

/**
 * 
 * @author maurofilho
 *
 */
public class RotaFinder implements Observer {
	
	private LogisticaObservable observable;
	private TrechoRepository trechoRepository;

	public RotaFinder(LogisticaObservable observable, TrechoRepository trechoRepository, Rota rotaAtual) {
		this.observable = observable;
		this.trechoRepository = trechoRepository;
		this.rotaAtual = rotaAtual;
		this.observable.addObserver(this);
	}
	
	public RotaFinder(LogisticaObservable logisticaObservable, TrechoRepository trechoRepository) {
		this(logisticaObservable, trechoRepository, new Rota());
	}

	private final Rota rotaAtual; 
	private Rota melhorRota;
	
	@Override
	public void update(Observable o, Object rota) {
		Rota rotaEncontrada = (Rota) rota;
		if(this.melhorRota == null || this.melhorRota.getDistancia().compareTo(rotaEncontrada.getDistancia()) > 0){
			this.melhorRota = rotaEncontrada;
		}
	}
	
	public Rota findMelhorRota(final String mapa, final String origem, final String destino){
		this.rotaAtual.addTrecho(origem, 0);
		this.find(mapa, origem, destino);
		return this.melhorRota;
	}
	
	private void find(final String mapa, final String origem, final String destino){
		if(origem.equals(destino)){
			founded();
		} else {
			Predicate<Trecho> in = p -> rotaAtual.getTrechos().contains(p.getOutroPonto(origem));
			if(canContinue()){
				trechoRepository
					.findByMapaAndPonto(mapa, origem)
						.stream().filter( in.negate() )
						.forEach(t -> {
							if(canContinue()){
								String trecho = t.getOutroPonto(origem);
								Rota novaRota = this.rotaAtual.clone();
								novaRota.addTrecho(trecho, t.getDistancia());
								new RotaFinder(this.observable, this.trechoRepository, novaRota).find(mapa, trecho, destino);
							}
						});;
			}
		}
	}
	
	public Rota getMelhorRota(){
		return this.melhorRota;
	}
	
	private boolean canContinue(){
		return this.melhorRota == null || this.melhorRota.getDistancia().compareTo(rotaAtual.getDistancia()) > 0;
	}
	
	private void founded(){
		observable.rotaEncontrada(this.rotaAtual);
	}
	
}
