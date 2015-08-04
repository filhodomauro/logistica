package com.filhodomauro.logistica;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.filhodomauro.logistica.model.Mapa;
import com.filhodomauro.logistica.model.Trecho;
import com.filhodomauro.logistica.repository.MapaRepository;
import com.filhodomauro.logistica.repository.TrechoRepository;

/**
 * 
 * @author maurofilho
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LogisticaTest {
	
	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));
	
	private MockMvc mockMvc;
	
	@Autowired
	private MapaRepository mapaRepository;
	
	@Autowired
	private TrechoRepository trechoRepository;
	
	@Autowired
    private WebApplicationContext webApplicationContext;
	
	@Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }
	
	@Test
	public void adicionarMapaUm() throws Exception{
		InputStream input = getClass().getResourceAsStream("/mapaUm.json");
		String content = IOUtils.toString(input);
		
		mockMvc.perform(
				post("/mapa")
					.contentType(contentType)
					.content(content)
				)
				.andExpect(status().isCreated());
		List<Mapa> mapas = mapaRepository.findByNome("Mapa Um");
		
		Assert.assertThat("Mapa não encontrado", mapas, not(empty()));
	}
	
	@Test
	public void adicionarMapaDois() throws Exception{
		InputStream input = getClass().getResourceAsStream("/mapaDois.json");
		String content = IOUtils.toString(input);
		
		mockMvc.perform(
				post("/mapa")
					.contentType(contentType)
					.content(content)
				)
				.andExpect(status().isCreated());
		List<Mapa> mapas = mapaRepository.findByNome("Mapa Dois");
		
		Assert.assertThat("Mapa não encontrado", mapas, not(empty()));
	}
	
	@Test
	public void trechosDoMapa() throws Exception{
		List<Mapa> mapas = mapaRepository.findByNome("Mapa teste");
		Assert.assertThat("Mapa não encontrado", mapas, not(empty()));
		Mapa mapa = mapas.get(0);
		Collection<Trecho> trechos = trechoRepository.findByMapaNome(mapa.getNome());
		Assert.assertThat("Trechos não encontrados", trechos, not(empty()));
	}
	
	@Test
	public void trechosPorPonto(){
		List<Trecho> trechos = trechoRepository.findByMapaAndPonto("Mapa teste","B");
		Assert.assertThat(trechos, not(empty()));
		Assert.assertThat(trechos, hasSize(3));
	}
	
	@Test
	public void melhorRotaTrechoSimples() throws Exception{
		mockMvc.perform(
					get("/rota/Mapa teste/A/C/5/2")
				)
				.andExpect(status().isOk())
				.andExpect((content().contentType(contentType)))
				.andExpect(jsonPath("$.custo", is(8d)))
				.andExpect(jsonPath("$.distancia", is(20)))
				.andExpect(jsonPath("$.trechos", hasSize(3)))
				.andExpect(jsonPath("$.trechos[0]", is("A")))
				.andExpect(jsonPath("$.trechos[1]", is("B")))
				.andExpect(jsonPath("$.trechos[2]", is("C")));
	}
	
	@Test
	public void melhorRotaTrechoMedio() throws Exception{
		mockMvc.perform(
					get("/rota/Mapa teste/A/D/5/2")
				)
				.andExpect(status().isOk())
				.andExpect((content().contentType(contentType)))
				.andExpect(jsonPath("$.custo", is(10d)))
				.andExpect(jsonPath("$.distancia", is(25)))
				.andExpect(jsonPath("$.trechos", hasSize(3)))
				.andExpect(jsonPath("$.trechos[0]", is("A")))
				.andExpect(jsonPath("$.trechos[1]", is("B")))
				.andExpect(jsonPath("$.trechos[2]", is("D")));
	}
	
}
