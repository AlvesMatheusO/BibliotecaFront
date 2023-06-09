package org.example;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.example.Livro.consultarLivros;

public class EdicaoLivroDialog extends JDialog {

    private JTextField txtTitulo;
    private JTextField txtAutor;
    private JTextField txtPreco;
    private JButton btnSalvar;
    private Livro livro;

    List<Livro> listaLivros = new ArrayList<>();

    public EdicaoLivroDialog(Livro livro) {
        this.livro = livro;

        setTitle("Editar Livro");
        setSize(400, 200);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setModalityType(ModalityType.APPLICATION_MODAL);

        JPanel panel = new JPanel(new GridLayout(4, 2));

        JLabel lblTitulo = new JLabel("Título:");
        txtTitulo = new JTextField(livro.getTitulo());
        JLabel lblAutor = new JLabel("Autor:");
        txtAutor = new JTextField(livro.getAutor());
        JLabel lblPreco = new JLabel("Preço:");
        txtPreco = new JTextField(String.valueOf(livro.getPreco()));

        panel.add(lblTitulo);
        panel.add(txtTitulo);
        panel.add(lblAutor);
        panel.add(txtAutor);
        panel.add(lblPreco);
        panel.add(txtPreco);

        btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarEdicao();
            }
        });

        panel.add(btnSalvar);

        add(panel);

        setVisible(true);
    }

    private void salvarEdicao() {
        // Obter os valores dos campos de texto
        String titulo = txtTitulo.getText();
        String autor = txtAutor.getText();
        double preco = Double.parseDouble(txtPreco.getText());

        livro.setTitulo(titulo);
        livro.setAutor(autor);
        livro.setPreco(preco);

        try {
            // Conectar-se à API HTTP para atualizar o livro
            Livro.editarLivro(livro);

            // Exemplo de mensagem de sucesso
            JOptionPane.showMessageDialog(this, "Livro atualizado com sucesso!");


            dispose();
        } catch (IOException e) {
            // Tratar o erro de conexão com a API
            JOptionPane.showMessageDialog(this, "Erro ao atualizar o livro. Verifique sua conexão com a internet.");
        }
        Main frame = new Main();
        frame.setVisible(true);
        frame.exibirLivros(consultarLivros());

    }



}
