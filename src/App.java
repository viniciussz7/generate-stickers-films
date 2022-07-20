import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {

        // fazer uma conexao http e buscar os top 250 filmes
        String url = "https://api.mocki.io/v2/549a5d8b";
        URI adress = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(adress).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        // selecionar e parsear os dados
        JsonParser parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);

        // exibir e manipular os dados pertinentes (titulo, poster, classificacao)
        var geradoraDeFigurinhas = new GeradorDeFigurinhas();

        listaDeFilmes.forEach(filme -> {
            String urlImagem = filme.get("image");
            String titulo = filme.get("title");
            String nomeArquivo = "saida/" + titulo + ".png";

            InputStream inputStream = null;

            try {
                inputStream = new URL(urlImagem).openStream();
            } catch (IOException e) {
            }

            try {
                geradoraDeFigurinhas.cria(inputStream, nomeArquivo);
            } catch (IOException e) {
            }

        });
    }
}
