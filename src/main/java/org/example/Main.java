package org.example;

import org.example.Livro;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Font;
import java.util.List;
import java.util.Objects;

import static org.example.Livro.consultarLivros;

public class Main extends JFrame {
    private JTextArea textArea;

    public Main() {
        setTitle("Lista de Livros");
        setSize(500, 1200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Lista de Livros");
        label.setFont(new Font("Arial", Font.BOLD, 30));
        add(label, BorderLayout.NORTH);

        textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.PLAIN, 15));
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void exibirLivros(List<Livro> listaLivros) {
        StringBuilder sb = new StringBuilder();
        for (Livro livro : listaLivros) {
            sb.append("ID: ").append(livro.getId()).append("\n");
            sb.append("Título: ").append(livro.getTitulo()).append("\n");
            sb.append("Descrição: ").append(livro.getDescricao()).append("\n");
            sb.append("Preço: ").append(livro.getPreco()).append("\n");
            sb.append("Autor: ").append(livro.getAutor()).append("\n\n");
        }
        textArea.setText(sb.toString());
    }

        public static void main (String[]args){
            Main frame = new Main();
            frame.setVisible(true);

            // Exibe os livros na interface
            frame.exibirLivros(Objects.requireNonNull(consultarLivros()));

        }
    }

