package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Livro {

    private long id;
    private String titulo;
    private double preco;
    private String descricao;
    private String autor;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    private static List<Livro> listaLivros = new ArrayList<>();

    public static List<Livro> consultarLivros() {
        List<Livro> listaLivros = new ArrayList<>();
        try {
            String apiUrl = "http://localhost:8080/livros";

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            System.out.println("Código de resposta: " + responseCode);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine);
            }
            reader.close();

            // Converte a resposta para um array de objetos JSON
            JsonArray jsonArray = new Gson().fromJson(response.toString(), JsonArray.class);

            // Itera sobre os objetos do array e adiciona-os à lista
            for (JsonElement elemento : jsonArray) {
                JsonObject livroJson = elemento.getAsJsonObject();
                long id = livroJson.get("id").getAsLong();
                String titulo = livroJson.has("titulo") ? livroJson.get("titulo").getAsString() : "";
                String descricao = livroJson.has("descricao") ? livroJson.get("descricao").getAsString() : "";
                double preco = livroJson.has("preco") ? livroJson.get("preco").getAsDouble() : 0.0;
                String autor = livroJson.has("autor") ? livroJson.get("autor").getAsString() : "";

                // Cria um objeto Livro e adiciona-o à lista
                Livro livro = new Livro();
                livro.setId(id);
                livro.setTitulo(titulo);
                livro.setAutor(autor);
                livro.setPreco(preco);
                livro.setDescricao(descricao);
                listaLivros.add(livro);
            }

            // Exibe a lista de livros
            for (Livro livro : listaLivros) {
                System.out.println("ID: " + livro.getId());
                System.out.println("Titulo: " + livro.getTitulo());
                System.out.println("Descricao: " + livro.getDescricao());
                System.out.println("Preco: " + livro.getPreco());
                System.out.println("Autor: " + livro.getAutor());
                System.out.println(" ");
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listaLivros;
    }

    public static List<Livro> consultarLivrosPorTitulo(String titulo) {
        List<Livro> livrosFiltrados = new ArrayList<>();

        try {
            String apiUrl = "http://localhost:8080/livros?titulo=" + titulo;

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            System.out.println("Código de resposta: " + responseCode);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine);
            }
            reader.close();

            JsonObject jsonResponse = new Gson().fromJson(response.toString(), JsonObject.class);

            // Verifica se o elemento "livros" existe no JSON response
            if (jsonResponse.has("livros")) {
                JsonArray livrosArray = jsonResponse.getAsJsonArray("livros");

                // Itera sobre os objetos da matriz e adiciona-os à lista
                for (JsonElement elemento : livrosArray) {
                    JsonObject livroJson = elemento.getAsJsonObject();
                    long id = livroJson.get("id").getAsLong();
                    String tituloLivro = livroJson.get("titulo").getAsString();
                    String descricao = livroJson.get("descricao").getAsString();
                    double preco = livroJson.get("preco").getAsDouble();
                    String autor = livroJson.get("autor").getAsString();

                    // Cria um objeto Livro e adiciona-o à lista
                    Livro livro = new Livro();
                    livro.setId(id);
                    livro.setTitulo(tituloLivro);
                    livro.setAutor(autor);
                    livro.setPreco(preco);
                    livro.setDescricao(descricao);
                    livrosFiltrados.add(livro);
                }
            } else {
                // Lidar com o caso em que "livros" não está presente ou é nulo
                System.out.println("O elemento 'livros' não está presente no JSON retornado.");
            }

            // Fecha a conexão
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return livrosFiltrados;
    }

    private static final String API_URL = "http://localhost:8080/update/";

    public static void editarLivro(Livro livro) throws IOException {
        String apiUrl = API_URL + livro.getId();

        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        // Montar o JSON com os dados do livro a serem atualizados
        String json = "{\"titulo\": \"" + livro.getTitulo() + "\", " +
                "\"autor\": \"" + livro.getAutor() + "\", " +
                "\"preco\": " + livro.getPreco() + "}";

        // Enviar o JSON no corpo da requisição
        try (OutputStream outputStream = connection.getOutputStream()) {
            byte[] input = json.getBytes(StandardCharsets.UTF_8);
            outputStream.write(input, 0, input.length);
        }

        // Obter a resposta da requisição
        int statusCode = connection.getResponseCode();
        if (statusCode == HttpURLConnection.HTTP_OK) {
            // Livro atualizado com sucesso
            System.out.println("Livro atualizado com sucesso!");



            // Atualizar a lista de livros
            listaLivros = consultarLivros();
        } else {
            // Erro ao atualizar o livro
            try (BufferedReader errorReader = new BufferedReader(
                    new InputStreamReader(connection.getErrorStream()))) {
                StringBuilder errorMessage = new StringBuilder();
                String line;
                while ((line = errorReader.readLine()) != null) {
                    errorMessage.append(line);
                }
                System.out.println("Erro ao atualizar o livro. Resposta da API: " + errorMessage.toString());
            }
        }

        connection.disconnect();
    }

    public static void criarLivro(Livro livro) {
        try {
            String apiUrl = "http://localhost:8080/save";

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Verifica se os campos obrigatórios estão preenchidos
            if (livro.getTitulo() == null || livro.getAutor() == null || livro.getDescricao() == null) {
                System.out.println("Erro ao criar o livro. Campos obrigatórios não preenchidos.");
                return;
            }

            // Monta o JSON com os dados do livro
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("titulo", livro.getTitulo());
            jsonObject.addProperty("autor", livro.getAutor());
            jsonObject.addProperty("preco", livro.getPreco());
            jsonObject.addProperty("descricao", livro.getDescricao());

            // Envia o JSON no corpo da requisição
            try (OutputStream outputStream = connection.getOutputStream()) {
                byte[] input = jsonObject.toString().getBytes(StandardCharsets.UTF_8);
                outputStream.write(input, 0, input.length);
            }

            // Obtém a resposta da requisição
            int statusCode = connection.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_CREATED) {
                // Livro criado com sucesso
                System.out.println("Livro criado com sucesso!");
            } else {
                // Erro ao criar o livro
                try (BufferedReader errorReader = new BufferedReader(
                        new InputStreamReader(connection.getErrorStream()))) {
                    StringBuilder errorMessage = new StringBuilder();
                    String line;
                    while ((line = errorReader.readLine()) != null) {
                        errorMessage.append(line);
                    }
                    System.out.println("Erro ao criar o livro. Resposta da API: " + errorMessage.toString());
                }
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deletarLivro(long id) {
        try {
            String apiUrl = "http://localhost:8080/delete/" + id;

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
                System.out.println("Livro deletado com sucesso!");
            } else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
                System.out.println("Livro não encontrado.");
            } else {
                System.out.println("Erro ao deletar o livro. Código de resposta: " + responseCode);
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static List<Livro> getListaLivros() {
        return listaLivros;
    }

    public static void setListaLivros(List<Livro> listaLivros) {
        Livro.listaLivros = listaLivros;
    }

}


