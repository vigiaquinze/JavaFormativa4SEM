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
    private JTable clienteTable;
    private DefaultTableModel tableModel;
    private ClienteController clienteController;

    public ClienteView() {
        clienteController = new ClienteController();

        setTitle("Gerenciar Clientes");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        panel = new JPanel();
        nomeField = new JTextField(10);
        emailField = new JTextField(15);
        telefoneField = new JTextField(10);
        
        // Inicializa o JComboBox com as opções
        tipoComboBox = new JComboBox<>(new String[]{"USER", "ADMIN"});
        
        adicionarButton = new JButton("Adicionar Cliente");
        buscarButton = new JButton("Buscar Cliente");

        panel.add(new JLabel("Nome:"));
        panel.add(nomeField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Telefone:"));
        panel.add(telefoneField);
        panel.add(new JLabel("Tipo:"));
        panel.add(tipoComboBox); // Adiciona o JComboBox ao painel
        panel.add(adicionarButton);
        panel.add(buscarButton);

        adicionarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Cliente cliente = new Cliente();
                cliente.setNome(nomeField.getText());
                cliente.setEmail(emailField.getText());
                cliente.setTelefone(telefoneField.getText());
                // Pega o valor selecionado do JComboBox
                cliente.setTipo((String) tipoComboBox.getSelectedItem());
                clienteController.adicionarCliente(cliente);
                System.out.println("Cliente adicionado com sucesso!");
            }
        });

        buscarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(nomeField.getText());
                Cliente cliente = clienteController.buscarCliente(id);
                if (cliente != null) {
                    nomeField.setText(cliente.getNome());
                    emailField.setText(cliente.getEmail());
                    telefoneField.setText(cliente.getTelefone());
                    // Define o tipo selecionado no JComboBox
                    tipoComboBox.setSelectedItem(cliente.getTipo());
                    System.out.println("Cliente encontrado!");
                } else {
                    System.out.println("Cliente não encontrado.");
                }
            }
        });

        add(panel);
    }

    // Método para atualizar a tabela com os clientes do banco de dados
    private void atualizarTabela() {
        // Limpar a tabela antes de adicionar novos dados
        tableModel.setRowCount(0);
        
        // Obter todos os clientes do banco de dados
        List<Cliente> clientes = clienteController.listarClientes();
        
        // Adicionar cada cliente na tabela
        for (Cliente cliente : clientes) {
            tableModel.addRow(new Object[]{cliente.getId(), cliente.getNome(), cliente.getEmail(), cliente.getTelefone(), cliente.getTipo()});
        }
    }
}
