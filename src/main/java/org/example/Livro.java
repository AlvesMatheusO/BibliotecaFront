package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
            String inputLine;
            StringBuilder response = new StringBuilder();

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
                String titulo = livroJson.get("titulo").getAsString();
                String descricao = livroJson.get("descricao").getAsString();
                double preco = livroJson.get("preco").getAsDouble();
                String autor = livroJson.get("autor").getAsString();

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

            // Fecha a conexão
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
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine);
            }
            reader.close();

            // Converte a resposta para um objeto JSON
            JsonObject jsonResponse = new Gson().fromJson(response.toString(), JsonObject.class);

            // Obtém a matriz de objetos "livros" do JSON
            JsonArray livrosArray = jsonResponse.getAsJsonArray("livros");

            // Itera sobre os objetos da matriz e adiciona-os à lista
            for (JsonElement elemento : livrosArray) {
                long id = elemento.getAsJsonObject().get("id").getAsLong();
                String tituloLivro = elemento.getAsJsonObject().get("titulo").getAsString();
                String descricao = elemento.getAsJsonObject().get("descricao").getAsString();
                double preco = elemento.getAsJsonObject().get("preco").getAsDouble();
                String autor = elemento.getAsJsonObject().get("autor").getAsString();

                // Cria um objeto Livro e adiciona-o à lista
                Livro livro = new Livro();
                livro.setId(id);
                livro.setTitulo(tituloLivro);
                livro.setAutor(autor);
                livro.setPreco(preco);
                livro.setDescricao(descricao);
                livrosFiltrados.add(livro);
            }

            // Fecha a conexão
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return livrosFiltrados;
    }
}


