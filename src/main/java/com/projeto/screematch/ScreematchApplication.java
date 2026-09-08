package com.projeto.screematch;

import com.projeto.screematch.model.DadosEpisodio;
import com.projeto.screematch.model.DadosSerie;
import com.projeto.screematch.model.DadosTemporada;
import com.projeto.screematch.service.ConsumoApi;
import com.projeto.screematch.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreematchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreematchApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        var consumoApi = new ConsumoApi();
        var json = consumoApi.obterDados("http://www.omdbapi.com/?t=Breaking+Bad&apikey=f8a9c447");
        //System.out.println(json);
        //json = consumoApi.obterDados("https://share.google/QdOO2CDkv0AfOyOML");
        System.out.println(json);

        ConverteDados converteDados = new ConverteDados();
        DadosSerie dadosSerie = converteDados.obterDados(json, DadosSerie.class);
        System.out.println(dadosSerie);

        json = consumoApi.obterDados("http://www.omdbapi.com/?t=Breaking+Bad&season=1&episode=2&apikey=f8a9c447");
        DadosEpisodio dadosEpisodio = converteDados.obterDados(json,DadosEpisodio.class);
        System.out.println(dadosEpisodio);

        List<DadosTemporada> temporadas = new ArrayList<>();

        for(int i = 1; i <= dadosSerie.totalTemporadas(); i++){
            json = consumoApi.obterDados("http://www.omdbapi.com/?t=Breaking+Bad&season=" + i +"&apikey=f8a9c447");
            DadosTemporada dadosTemporada = converteDados.obterDados(json,DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }
        temporadas.forEach(System.out::println);
    }
}