package vigiaquinze.View;

import vigiaquinze.Control.ClienteController;
import vigiaquinze.Model.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ClienteView extends JFrame {
    private JPanel panel;
    private JTextField nomeField;
    private JTextField emailField;
    private JTextField telefoneField;
    private JComboBox<String> tipoComboBox; // Alterado para JComboBox
    private JButton adicionarButton;
    private JButton buscarButton;
    private JButton atualizarButton; // Novo botão para atualizar
    private JTable clienteTable;
    private DefaultTableModel tableModel;
    private ClienteController clienteController;
    private int clienteId; // Campo para armazenar o ID do cliente selecionado

    public ClienteView() {
        clienteController = new ClienteController();

        setTitle("Gerenciar Clientes");
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
        emailField = new JTextField(15);
        telefoneField = new JTextField(10);
        
        // Inicializa o JComboBox com as opções
        tipoComboBox = new JComboBox<>(new String[]{"USER", "ADMIN"});
        
        adicionarButton = new JButton("Adicionar Cliente");
        buscarButton = new JButton("Buscar Cliente");
        atualizarButton = new JButton("Atualizar Cliente"); // Novo botão para atualizar

        // Adicionar componentes ao painel de entrada
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(nomeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Telefone:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(telefoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(new JLabel("Tipo:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(tipoComboBox, gbc); // Adiciona o JComboBox ao painel de entrada

        gbc.gridx = 0;
        gbc.gridy = 4;
        inputPanel.add(adicionarButton, gbc);
        gbc.gridx = 1;
        inputPanel.add(buscarButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        inputPanel.add(atualizarButton, gbc); // Adiciona o botão de atualizar

        panel.add(inputPanel, BorderLayout.NORTH);

        // Configurar a tabela
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "Email", "Telefone", "Tipo"}, 0);
        clienteTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(clienteTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        clienteTable.getSelectionModel().addListSelectionListener(event -> {
            if (clienteTable.getSelectedRow() != -1) {
                int selectedRow = clienteTable.getSelectedRow();
                clienteId = (int) tableModel.getValueAt(selectedRow, 0);
                nomeField.setText((String) tableModel.getValueAt(selectedRow, 1));
                emailField.setText((String) tableModel.getValueAt(selectedRow, 2));
                telefoneField.setText((String) tableModel.getValueAt(selectedRow, 3));
                tipoComboBox.setSelectedItem((String) tableModel.getValueAt(selectedRow, 4));
            }
        });

        adicionarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Cliente cliente = new Cliente();
                cliente.setNome(nomeField.getText());
                cliente.setEmail(emailField.getText());
                cliente.setTelefone(telefoneField.getText());
                cliente.setTipo((String) tipoComboBox.getSelectedItem());
                clienteController.adicionarCliente(cliente);
                System.out.println("Cliente adicionado com sucesso!");
                limparCampos(); // Limpar os campos após adicionar um cliente
                atualizarTabela(); // Atualizar a tabela após adicionar um cliente
            }
        });

        buscarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nome = nomeField.getText();
                List<Cliente> clientes = clienteController.buscarClientesPorNome(nome);
                if (!clientes.isEmpty()) {
                    tableModel.setRowCount(0);
                    for (Cliente cliente : clientes) {
                        tableModel.addRow(new Object[]{
                                cliente.getId(),
                                cliente.getNome(),
                                cliente.getEmail(),
                                cliente.getTelefone(),
                                cliente.getTipo()
                        });
                    }
                    System.out.println("Clientes encontrados!");
                } else {
                    System.out.println("Cliente não encontrado.");
                }
            }
        });

        atualizarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Cliente cliente = new Cliente();
                cliente.setId(clienteId);
                cliente.setNome(nomeField.getText());
                cliente.setEmail(emailField.getText());
                cliente.setTelefone(telefoneField.getText());
                cliente.setTipo((String) tipoComboBox.getSelectedItem());
                clienteController.atualizarCliente(cliente);
                System.out.println("Cliente atualizado com sucesso!");
                limparCampos(); // Limpar os campos após atualizar um cliente
                atualizarTabela(); // Atualizar a tabela após atualizar um cliente
            }
        });

        add(panel);
        atualizarTabela(); // Carregar a tabela ao iniciar a janela
    }

    private void atualizarTabela() {
        tableModel.setRowCount(0);
        List<Cliente> clientes = clienteController.listarClientes();
        for (Cliente cliente : clientes) {
            tableModel.addRow(new Object[]{cliente.getId(), cliente.getNome(), cliente.getEmail(), cliente.getTelefone(), cliente.getTipo()});
        }
    }

    private void limparCampos() {
        nomeField.setText("");
        emailField.setText("");
        telefoneField.setText("");
        tipoComboBox.setSelectedIndex(0);
        clienteId = 0; // Resetar o ID do cliente selecionado
    }
}
