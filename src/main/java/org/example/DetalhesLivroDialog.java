package org.example;

import javax.swing.*;

public class DetalhesLivroDialog extends JDialog {

    public DetalhesLivroDialog(Livro livro) {
        setTitle("Detalhes do Livro");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
        setModal(true);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel lblTitulo = new JLabel("Título: " + livro.getTitulo());
        JLabel lblAutor = new JLabel("Autor: " + livro.getAutor());
        JLabel lblPreco = new JLabel("Preço: " + livro.getPreco());

        panel.add(lblTitulo);
        panel.add(lblAutor);
        panel.add(lblPreco);

        add(panel);

        setVisible(true);
    }
}
