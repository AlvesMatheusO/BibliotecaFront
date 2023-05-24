package org.example;

import org.example.Livro;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ScrollPaneConstants;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import static org.example.Livro.consultarLivros;

public class Main extends JFrame {
    private JTextArea textArea;

    public Main() {
        setTitle("Lista de Livros");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Lista de Livros");
        label.setFont(new Font("Arial", Font.BOLD, 16));
        add(label, BorderLayout.NORTH);


       JTextField txtFiltro = new JTextField();
        JButton btnFiltrar = new JButton("Filtrar");
        textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        add(txtFiltro, BorderLayout.NORTH);
        add(btnFiltrar, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);

        // Adiciona um listener para o botão de filtrar

        btnFiltrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String filtro = txtFiltro.getText();
                List<Livro> livrosFiltrados = filtrarLivrosPorTitulo(consultarLivros(), filtro);
                exibirLivros(livrosFiltrados);
            }
        });

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

