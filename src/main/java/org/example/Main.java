package org.example;

import org.example.Livro;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import static org.example.Livro.consultarLivros;
import static org.example.Livro.consultarLivrosPorTitulo;

public class Main extends JFrame {
    private static JTextField txtFiltro;
    private static JButton btnFiltrar;
    private static JTextArea textArea;

    public Main() {
        JFrame frame = new JFrame("Livros");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        // Painel principal com BorderLayout
        JPanel panel = new JPanel(new BorderLayout());

        // Painel para o campo de texto e botão
        JPanel filterPanel = new JPanel(new BorderLayout());

        txtFiltro = new JTextField();
        btnFiltrar = new JButton("Filtrar");

        // Adicionar o campo de texto e botão ao painel de filtro
        filterPanel.add(txtFiltro, BorderLayout.CENTER);
        filterPanel.add(btnFiltrar, BorderLayout.EAST);

        // Adicionar o painel de filtro ao painel principal no norte
        panel.add(filterPanel, BorderLayout.NORTH);

        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);

        // Adicionar a área de texto com barra de rolagem ao painel principal no centro
        panel.add(scrollPane, BorderLayout.CENTER);

        // Adicionar o painel principal ao frame
        frame.add(panel);



        // Adiciona um listener para o botão de filtrar

        btnFiltrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String filtro = txtFiltro.getText();
                List<Livro> livrosFiltrados = filtrarLivrosPorTitulo(consultarLivros(), filtro);
                exibirLivros(livrosFiltrados);
            }
        });

        frame.setVisible(true);
    }

    public void exibirLivros(List<Livro> livros) {
        StringBuilder sb = new StringBuilder();
        for (Livro livro : livros) {
            sb.append("ID: ").append(livro.getId()).append("\n");
            sb.append("Título: ").append(livro.getTitulo()).append("\n");
            sb.append("Descrição: ").append(livro.getDescricao()).append("\n");
            sb.append("Preço: ").append(livro.getPreco()).append("\n");
            sb.append("Autor: ").append(livro.getAutor()).append("\n\n");
        }
        textArea.setText(sb.toString());
    }

    public static List<Livro> filtrarLivrosPorTitulo(List<Livro> livros, String titulo) {
        List<Livro> livrosFiltrados = new ArrayList();
        for (Livro livro : livros) {
            if (livro.getTitulo().toLowerCase().contains(titulo.toLowerCase())) {
                livrosFiltrados.add(livro);
            }
        }
        return livrosFiltrados;
    }

        public static void main (String[]args){
            Main frame = new Main();
            frame.setVisible(true);

            // Exibe todos os livros na inicialização
            frame.exibirLivros(Livro.consultarLivros());


        }
    }

