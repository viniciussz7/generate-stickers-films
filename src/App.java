import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {

        //String url = "https://api.mocki.io/v2/549a5d8b"; //IMDB
        //ExtratorConteudo extrator = new ExtratorConteudoImdb();
        
        String url = "https://api.mocki.io/v2/549a5d8b/NASA-APOD"; //NASA
        ExtratorConteudo extrator = new ExtratorConteudoNasa();

        var clienteHttp = new ClienteHttp();
        String json = clienteHttp.buscaDados(url);
        
        List<Conteudo> conteudos = extrator.extraiConteudos(json);  

        var geradoraDeFigurinhas = new GeradorDeFigurinhas();

        conteudos.forEach(conteudo -> {
            
            String nomeArquivo = "saida/" + conteudo.getTitulo() + ".png";

            InputStream inputStream = null;

            try {
                inputStream = new URL(conteudo.getUrlImagem()).openStream();
            } catch (IOException e) {
            }

            try {
                geradoraDeFigurinhas.cria(inputStream, nomeArquivo);
            } catch (IOException e) {
            }

            System.out.println(conteudo.getTitulo());
        });
    }
}
