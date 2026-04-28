package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner sc = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=6585022c";

    public void exibeMenu() {

        // 1️⃣ Solicita o nome da série e busca os dados gerais na API OMDb
        System.out.println("Digite o nome da série para busca: ");
        var nomeSerie = sc.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);

        // 2️⃣ Busca todas as temporadas da série e armazena em uma lista
        List<DadosTemporada> temporadas = new ArrayList<>();
        for (int i = 1; i <= dados.totalTemporadas(); i++) {
            json = consumo.obterDados(
                    ENDERECO + nomeSerie.replace(" ", "+") + "&season=" + i + API_KEY
            );
            temporadas.add(conversor.obterDados(json, DadosTemporada.class));
        }

        // 3️⃣ Exibe os dados de cada temporada e os títulos dos episódios
        temporadas.forEach(System.out::println);
        temporadas.forEach(t ->
                t.episodios().forEach(e -> System.out.println(e.titulo()))
        );

        // 4️⃣ Junta todos os episódios de todas as temporadas em uma única lista
        List<DadosEpisodio> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());

        // 5️⃣ Exibe o Top 10 episódios mais bem avaliados
        System.out.println("\nTop 10 Episódios mais bem avaliados:");
        dadosEpisodios.stream()
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
                .limit(10)
                .map(e -> e.titulo().toUpperCase())
                .forEach(System.out::println);

        // 6️⃣ Converte os dados brutos dos episódios em objetos Episodio (modelo de domínio)
        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numero(), d))
                ).collect(Collectors.toList());

        // 7️⃣ Busca um episódio pelo título (ou parte dele)
        System.out.println("Digite um trecho do episódio: ");
        var trechoTitulo = sc.nextLine();
        Optional<Episodio> episodioBuscado = episodios.stream()
                .filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
                .findFirst();

        if (episodioBuscado.isPresent()) {
            System.out.println("Episódio encontrado!");
            System.out.println("Episódio: " + episodioBuscado.get().getTitulo());
            System.out.println("Temporada: " + episodioBuscado.get().getTemporada());
        } else {
            System.out.println("Episódio não encontrado!");
        }

        // 8️⃣ Filtra episódios lançados a partir de um ano informado pelo usuário
        System.out.println("A partir de que ano você deseja ver os episódios: ");
        var ano = sc.nextInt();
        sc.nextLine();

        LocalDate dataBusca = LocalDate.of(ano, 1, 1);
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        episodios.stream()
                .filter(e -> e.getDataLancamento() != null &&
                        e.getDataLancamento().isAfter(dataBusca))
                .forEach(e -> System.out.println(
                        "Temporada: " + e.getTemporada() +
                                " Episódio: " + e.getTitulo() +
                                " Data de lançamento: " +
                                e.getDataLancamento().format(formatador)
                ));

        // 9️⃣ Calcula a média das avaliações por temporada
        Map<Integer, Double> avaliacoesPorTemporada = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.groupingBy(
                        Episodio::getTemporada,
                        Collectors.averagingDouble(Episodio::getAvaliacao)
                ));
        System.out.println(avaliacoesPorTemporada);

        // 🔟 Gera estatísticas gerais das avaliações (quantidade, média, maior e menor nota)
        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));

        System.out.println("Quantidade de episódios avaliados: " + est.getCount());
        System.out.println("Média: " + est.getAverage());
        System.out.println("Melhor episódio: " + est.getMax());
        System.out.println("Pior episódio: " + est.getMin());
    }
}