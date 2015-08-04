# Sistema de Logística

Projeto para cálculo de rotas através de mapas pré-definidos.

##Criar Mapas

Serviço REST destinado a criar mapas a partir de uma malha logística.

###MAPPING

**/map** 

###METHOD
**PUT**

###REQUEST

Interface JSON para acionamento do serviço

* nome - Nome do mapa
* trechos - trechos de rota presentes no mapa 
 * * origem - nome do ponto de origem
 * * destino - nome do ponto de destino
 * * distancia - distância em kilômetros entre um ponto e outro

```

{
	"nome":"Mapa Um",
	"trechos":[
		{"origem":"A","destino":"B","distancia":10},
		{"origem":"B","destino":"C","distancia":10},
		{"origem":"C","destino":"D","distancia":10},
		{"origem":"B","destino":"D","distancia":15}
	]
}

```

###RESPONSE

**HTTP 201**

===============================================================================

##Consultar Melhor Rota

Retorna a melhor rota a partir de um mapa, sua origem e destino, além das informações de autonomia do caminhão e preço do litro do combustível

###MAPPING

**/rota**

###METHOD

**GET**

###REQUEST

**/rota/{nomeMapa}/{origem}/{destino}/{autonomia}/{valorLitro}**

* nomeMapa - Nome do mapa ao qual a consulta será realizada
* origem - Nome do ponto de origem
* destino - Nome do ponto de destino
* autonomia - autonomia do veículo (Consumo em km/l)
* valorLitro - valor do litro do combustível

###RESPONSE

```

{"trechos":["A","B","D"],"custo":10.0,"distancia":25}

```

* trechos - rota a percorrer
* custo - custo da viagem considerando a autonomia do veículo e valor do combustível
* distancia - distância a ser percorrida


