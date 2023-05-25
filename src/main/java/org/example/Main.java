package org.example;

import org.example.Livro;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import static org.example.Livro.consultarLivros;
import static org.example.Livro.consultarLivrosPorTitulo;

public class Main extends JFrame {
    private static JTextField txtFiltro;
    private static JButton btnFiltrar;
    private static JList<String> list;
    private static DefaultListModel<String> listModel;

    public Main() {
        JFrame frame = new JFrame("Livros");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);

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

        listModel = new DefaultListModel<>();
        list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);
        JScrollPane scrollPane = new JScrollPane(list);

        // Adicionar o scrollPane ao painel principal no centro
        panel.add(scrollPane, BorderLayout.CENTER);

        // Adicionar o painel principal ao frame
        frame.add(panel);

        // Adiciona um listener para o botão de filtrar
        btnFiltrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String filtro = txtFiltro.getText();
                if (filtro.isEmpty()) {
                    List<Livro> livrosOriginais = null;
                    exibirLivros(livrosOriginais);
                } else {
                    List<Livro> livrosFiltrados = filtrarLivrosPorTitulo(Livro.consultarLivros(), filtro);
                    exibirLivros(livrosFiltrados);
                }
            }
        });

        // Adiciona um listener para a seleção de item na lista
        list.addListSelectionListener(e -> {
            int selectedIndex = list.getSelectedIndex();
            if (selectedIndex != -1) {
                Livro livro = Livro.consultarLivros().get(selectedIndex);
                exibirDetalhesLivro(livro);
            }
        });

        frame.setVisible(true);
    }


    public void exibirDetalhesLivro(Livro livro) {
        StringBuilder detalhes = new StringBuilder();
        detalhes.append("ID: ").append(livro.getId()).append("\n");
        detalhes.append("Título: ").append(livro.getTitulo()).append("\n");
        detalhes.append("Descrição: ").append(livro.getDescricao()).append("\n");
        detalhes.append("Preço: ").append(livro.getPreco()).append("\n");
        detalhes.append("Autor: ").append(livro.getAutor());

        JTextArea textArea = new JTextArea(detalhes.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(300, 200));

        JOptionPane.showMessageDialog(this, scrollPane, "Detalhes do Livro", JOptionPane.PLAIN_MESSAGE);
    }



    public void exibirLivros(List<Livro> livros) {
        listModel.clear();

        for (Livro livro : livros) {
            listModel.addElement(livro.getTitulo());
        }
    }
    public static List<Livro> filtrarLivrosPorTitulo(List<Livro> livros, String titulo) {
        if(titulo.isEmpty()) {
            return livros; //Retorna a lista original se o título estiver vazio
        }

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

        List<Livro> livrosOriginais;
        livrosOriginais = Livro.consultarLivros();

        // Exibe todos os livros na inicialização
        frame.exibirLivros(Livro.consultarLivros());


    }
}
