package com.filhodomauro.logistica.service;

import com.filhodomauro.logistica.controller.response.Rota;

/**
 * 
 * @author maurofilho
 *
 */
public interface LogisticaService {

	Rota recuperarRota(String nomeMapa,String origem,String destino,Double autonomia,Double valorLitro);
}
