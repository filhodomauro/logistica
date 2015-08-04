package com.filhodomauro.logistica.observer;

import java.util.Observable;

import com.filhodomauro.logistica.controller.response.Rota;

/**
 * 
 * @author maurofilho
 *
 */
public class LogisticaObservable extends Observable{

	public void rotaEncontrada(Rota rota){
		setChanged();
		notifyObservers(rota);
	}
	
}
