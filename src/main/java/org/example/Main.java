package org.example;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import static org.example.Livro.consultarLivros;

public class Main extends JFrame {
    private List<Livro> listaLivros;
    private JList<String> livrosList;
    private DefaultListModel<String> livrosListModel;
    private static JTextField txtFiltro;
    private static JButton btnFiltrar;
    private JTextArea textArea;

    public Main() {
        JFrame frame = new JFrame("Livros");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);

        // Painel principal com BorderLayout
        JPanel panel = new JPanel(new BorderLayout());

        // Painel para o campo de texto e botão
        JPanel filterPanel = new JPanel(new BorderLayout());

        txtFiltro = new JTextField();
        btnFiltrar = new JButton("Filtrar");

        livrosListModel = new DefaultListModel<>();
        livrosList = new JList<>(livrosListModel);
        livrosList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(livrosList);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton btnCarregarLivros = new JButton("Carregar Livros");
        btnCarregarLivros.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exibirLivros(consultarLivros());
            }
        });
        panel.add(btnCarregarLivros, BorderLayout.SOUTH);

        livrosList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedIndex = livrosList.getSelectedIndex();
                    if (selectedIndex != -1) {
                        Livro livroSelecionado = listaLivros.get(selectedIndex);
                        exibirDetalhesLivro(livroSelecionado);
                    }
                }
            }
        });

        // Adiciona um listener para o botão de filtrar
        btnFiltrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String filtro = txtFiltro.getText();
                List<Livro> livrosFiltrados = filtrarLivrosPorTitulo(consultarLivros(), filtro);
                exibirLivros(livrosFiltrados);
            }
        });

        // Botão para listar todos os livros
        JButton btnListarTodos = new JButton("Listar Todos");
        btnListarTodos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exibirLivros(consultarLivros());
            }
        });

        // Adiciona o botão de listar todos no painel principal
        panel.add(btnListarTodos, BorderLayout.SOUTH);

        // Adiciona o campo de filtro e o botão no painel de filtro
        filterPanel.add(txtFiltro, BorderLayout.CENTER);
        filterPanel.add(btnFiltrar, BorderLayout.EAST);

        // Adiciona o painel de filtro no painel principal
        panel.add(filterPanel, BorderLayout.NORTH);



        // Adiciona o painel principal ao frame
        frame.add(panel);

        frame.setVisible(true);
    }

    public void exibirLivros(List<Livro> livros) {
        listaLivros = livros;
        livrosListModel.clear();

        for (Livro livro : livros) {
            livrosListModel.addElement(livro.getTitulo());
        }
    }

    public List<Livro> filtrarLivrosPorTitulo(List<Livro> livros, String titulo) {
        List<Livro> livrosFiltrados = new ArrayList<>();
        for (Livro livro : livros) {
            if (livro.getTitulo().toLowerCase().contains(titulo.toLowerCase())) {
                livrosFiltrados.add(livro);
            }
        }
        return livrosFiltrados;
    }

    public void exibirDetalhesLivro(Livro livro) {
        StringBuilder detalhes = new StringBuilder();
        detalhes.append("Título: \n").append(livro.getTitulo()).append("\n");
        detalhes.append("Descrição: \n").append(livro.getDescricao()).append("\n");
        detalhes.append("Preço: \n").append(livro.getPreco()).append("\n");
        detalhes.append("Autor: \n").append(livro.getAutor());

        Object[] options = { "Editar", "Excluir", "Fechar" };


        int choice = JOptionPane.showOptionDialog(this, detalhes.toString(), "Detalhes do Livro",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[2]);

        if (choice == 0) {

            System.out.println("editar");
        } else if (choice == 1) {
            System.out.println("excluir");
        }

    }

    public static void main(String[] args) {
        Main frame = new Main();
        frame.setVisible(true);

        // Exibe todos os livros na inicialização
        frame.exibirLivros(consultarLivros());
    }
}




