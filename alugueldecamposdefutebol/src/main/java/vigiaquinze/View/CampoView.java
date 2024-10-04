package vigiaquinze.View;

import vigiaquinze.Control.CampoController;
import vigiaquinze.Model.Campo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CampoView extends JFrame {
    private JPanel panel;
    private JTextField nomeField;
    private JTextField localField;
    private JTextField precoField;
    private JButton adicionarButton;
    private JButton buscarButton;
    private JButton atualizarButton; // Novo botão para atualizar
    private JTable campoTable;
    private DefaultTableModel tableModel;
    private CampoController campoController;
    private int campoId; // Campo para armazenar o ID do campo selecionado

    public CampoView() {
        campoController = new CampoController();

        setTitle("Gerenciar Campos");
        setSize(600, 400); // Aumentado para acomodar a tabela
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(new BorderLayout()); // Usar BorderLayout para melhor organização

        // Painel de entrada
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout()); // Usar GridBagLayout para melhor controle
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        nomeField = new JTextField(15);
        localField = new JTextField(15);
        precoField = new JTextField(10);
        
        adicionarButton = new JButton("Adicionar Campo");
        buscarButton = new JButton("Buscar Campo");
        atualizarButton = new JButton("Atualizar Campo"); // Novo botão para atualizar

        // Adicionar componentes ao painel de entrada
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(nomeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Local:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(localField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Preço:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(precoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(adicionarButton, gbc);
        gbc.gridx = 1;
        inputPanel.add(buscarButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        inputPanel.add(atualizarButton, gbc); // Adiciona o botão de atualizar

        panel.add(inputPanel, BorderLayout.NORTH);

        // Configurar a tabela
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "Local", "Preço"}, 0);
        campoTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(campoTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        campoTable.getSelectionModel().addListSelectionListener(event -> {
            if (campoTable.getSelectedRow() != -1) {
                int selectedRow = campoTable.getSelectedRow();
                campoId = (int) tableModel.getValueAt(selectedRow, 0);
                nomeField.setText((String) tableModel.getValueAt(selectedRow, 1));
                localField.setText((String) tableModel.getValueAt(selectedRow, 2));
                precoField.setText(String.valueOf(tableModel.getValueAt(selectedRow, 3)));
            }
        });

        adicionarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Campo campo = new Campo(0, nomeField.getText(), localField.getText(), Double.parseDouble(precoField.getText()));
                campoController.adicionarCampo(campo);
                System.out.println("Campo adicionado com sucesso!");
                limparCampos(); // Limpar os campos após adicionar um campo
                atualizarTabela(); // Atualizar a tabela após adicionar um campo
            }
        });

        buscarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nome = nomeField.getText();
                List<Campo> campos = campoController.buscarCamposPorNome(nome);
                if (!campos.isEmpty()) {
                    tableModel.setRowCount(0);
                    for (Campo campo : campos) {
                        tableModel.addRow(new Object[]{
                                campo.getId(),
                                campo.getNome(),
                                campo.getLocal(),
                                campo.getPreco()
                        });
                    }
                    System.out.println("Campos encontrados!");
                } else {
                    System.out.println("Campo não encontrado.");
                }
            }
        });

        atualizarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Campo campo = new Campo(campoId, nomeField.getText(), localField.getText(), Double.parseDouble(precoField.getText()));
                campoController.atualizarCampo(campo);
                System.out.println("Campo atualizado com sucesso!");
                limparCampos(); // Limpar os campos após atualizar um campo
                atualizarTabela(); // Atualizar a tabela após atualizar um campo
            }
        });

        add(panel);
        atualizarTabela(); // Carregar a tabela ao iniciar a janela
    }

    private void atualizarTabela() {
        tableModel.setRowCount(0);
        List<Campo> campos = campoController.listarCampos();
        for (Campo campo : campos) {
            tableModel.addRow(new Object[]{campo.getId(), campo.getNome(), campo.getLocal(), campo.getPreco()});
        }
    }

    private void limparCampos() {
        nomeField.setText("");
        localField.setText("");
        precoField.setText("");
        campoId = 0; // Resetar o ID do campo selecionado
    }
}
