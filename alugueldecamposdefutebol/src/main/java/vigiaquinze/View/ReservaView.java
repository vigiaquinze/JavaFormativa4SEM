package vigiaquinze.View;

import vigiaquinze.Control.ReservaController;
import vigiaquinze.Control.CampoController;
import vigiaquinze.Control.ClienteController;
import vigiaquinze.Control.PDFGenerator; // Importar PDFGenerator
import vigiaquinze.Model.Reserva;
import vigiaquinze.Model.Campo;
import vigiaquinze.Model.Cliente;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

public class ReservaView extends JFrame {
    private JPanel panel;
    private JDatePickerImpl dataPicker;
    private JComboBox<String> horaInicioBox;
    private JComboBox<String> horaFimBox;
    private JComboBox<Campo> campoComboBox;
    private JTextField clienteNomeField;
    private JButton adicionarButton;
    private JButton buscarButton;
    private JButton excluirButton; // Novo botão para excluir reservas
    private JButton gerarRelatorioButton; // Novo botão para gerar relatórios
    private JTable reservaTable;
    private DefaultTableModel tableModel;
    private ReservaController reservaController;
    private CampoController campoController;
    private ClienteController clienteController;

    public ReservaView() {
        reservaController = new ReservaController();
        campoController = new CampoController();
        clienteController = new ClienteController();

        setTitle("Gerenciar Reservas");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Painel de entrada
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        horaInicioBox = new JComboBox<>();
        horaFimBox = new JComboBox<>();
        campoComboBox = new JComboBox<>();
        clienteNomeField = new JTextField(15);
        adicionarButton = new JButton("Adicionar Reserva");
        buscarButton = new JButton("Buscar Cliente");
        excluirButton = new JButton("Excluir Reserva"); // Inicialização do botão de exclusão

        // Configurar o JDatePicker
        UtilDateModel model = new UtilDateModel();
        Properties properties = new Properties();
        properties.put("text.today", "Hoje");
        properties.put("text.month", "Mês");
        properties.put("text.year", "Ano");

        dataPicker = new JDatePickerImpl(new JDatePanelImpl(model, properties), new DateLabelFormatter());

        // Adiciona horários disponíveis às ComboBoxes
        for (int i = 6; i < 24; i++) {
            String hora = String.format("%02d:00:00", i);
            horaInicioBox.addItem(hora);
            horaFimBox.addItem(hora);
        }

        // Adiciona campos disponíveis à ComboBox
        List<Campo> campos = campoController.listarCampos();
        for (Campo campo : campos) {
            campoComboBox.addItem(campo);
        }

        // Adicionar componentes ao painel de entrada
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Data:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(dataPicker, gbc); // Adicione o JDatePicker aqui

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Hora Início:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(horaInicioBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Hora Fim:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(horaFimBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(new JLabel("Campo:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(campoComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        inputPanel.add(new JLabel("Cliente Nome:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(clienteNomeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        inputPanel.add(adicionarButton, gbc);
        gbc.gridx = 1;
        inputPanel.add(buscarButton, gbc);

        gbc.gridx = 0; // Ajuste a posição do botão de exclusão
        gbc.gridy = 6;
        inputPanel.add(excluirButton, gbc); // Adiciona o botão de exclusão

        panel.add(inputPanel, BorderLayout.NORTH);

        // Configurar a tabela
        tableModel = new DefaultTableModel(
                new Object[] { "ID", "Data", "Hora Início", "Hora Fim", "Campo", "Cliente", "Total a pagar" },
                0);
        reservaTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(reservaTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Novo botão para gerar o relatório
        gerarRelatorioButton = new JButton("Gerar Relatório");
        panel.add(gerarRelatorioButton, BorderLayout.SOUTH);

        // ActionListener para o botão de busca
        buscarButton.addActionListener(e -> {
            String nome = clienteNomeField.getText();
            List<Cliente> clientes = clienteController.buscarClientesPorNome(nome);
            if (!clientes.isEmpty()) {
                Cliente cliente = clientes.get(0); // Assume que pega o primeiro cliente encontrado
                clienteNomeField.setText(cliente.getNome());
                System.out.println("Cliente encontrado!");
            } else {
                System.out.println("Cliente não encontrado.");
            }
        });

        // ActionListener para o botão de adicionar reserva
        adicionarButton.addActionListener(e -> {
            String nome = clienteNomeField.getText();
            List<Cliente> clientes = clienteController.buscarClientesPorNome(nome);
            if (!clientes.isEmpty()) {
                Cliente cliente = clientes.get(0); // Assume que pega o primeiro cliente encontrado
                Campo campo = (Campo) campoComboBox.getSelectedItem();

                // Obter a data selecionada
                java.util.Date utilDate = (java.util.Date) dataPicker.getModel().getValue(); // Obtém a data como
                                                                                             // java.util.Date
                Date selectedDate = new Date(utilDate.getTime()); // Converte para java.sql.Date

                // Obter a hora de início e fim
                Time horaInicio = Time.valueOf((String) horaInicioBox.getSelectedItem());
                Time horaFim = Time.valueOf((String) horaFimBox.getSelectedItem());

                // Verificar se o horário está disponível
                if (!reservaController.verificarHorarioDisponivel(selectedDate, horaInicio, campo)) {
                    JOptionPane.showMessageDialog(panel,
                            "Este horário para o campo selecionado já está ocupado. Por favor, escolha outro.", "Erro",
                            JOptionPane.ERROR_MESSAGE);
                    return; // Não prosseguir se o horário já estiver ocupado
                }

                // Calcular o número de horas de reserva
                long diff = horaFim.getTime() - horaInicio.getTime();
                int horas = (int) Math.floor(diff / (1000 * 60 * 60)); // converter milissegundos para horas

                // Calcular o preço da reserva
                int precoReserva = (int) (horas * campo.getPreco()); // Truncar a parte decimal

                // Criar a reserva com o preço calculado
                Reserva reserva = new Reserva(
                        0,
                        selectedDate, // Usando a data selecionada
                        horaInicio,
                        horaFim,
                        precoReserva, // usando o preço calculado
                        campo,
                        cliente);

                // Adicionar a reserva ao controlador
                reservaController.adicionarReserva(reserva);
                System.out.println("Reserva adicionada com sucesso!");
                limparCampos(); // Limpar os campos após adicionar uma reserva
                atualizarTabela(); // Atualizar a tabela após adicionar uma reserva
            } else {
                System.out.println("Cliente não encontrado. Não é possível adicionar reserva.");
            }
        });

        // ActionListener para o botão de exclusão de reserva
        excluirButton.addActionListener(e -> {
            int selectedRow = reservaTable.getSelectedRow();
            if (selectedRow != -1) {
                int reservaId = (int) tableModel.getValueAt(selectedRow, 0); // Obter ID da reserva
                reservaController.deletarReserva(reservaId); // Chamar o método para excluir a reserva
                atualizarTabela(); // Atualizar a tabela após exclusão
                JOptionPane.showMessageDialog(panel, "Reserva excluída com sucesso!", "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(panel, "Selecione uma reserva para excluir.", "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // ActionListener para o botão de gerar relatório
        gerarRelatorioButton.addActionListener(e -> {
            int selectedRow = reservaTable.getSelectedRow();
            if (selectedRow != -1) {
                int reservaId = (int) tableModel.getValueAt(selectedRow, 0); // Obter ID da reserva
                Reserva reserva = reservaController.buscarReserva(reservaId); // Buscar a reserva pelo ID
                PDFGenerator pdfGenerator = new PDFGenerator();
                pdfGenerator.gerarRelatorioReserva(reserva); // Chamar o método para gerar o relatório
                JOptionPane.showMessageDialog(panel, "Relatório gerado com sucesso!", "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(panel, "Selecione uma reserva para gerar o relatório.", "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // Adicionando a tabela ao painel
        panel.add(scrollPane, BorderLayout.CENTER);
        setContentPane(panel);
        atualizarTabela(); // Atualizar a tabela ao abrir a janela
        setVisible(true);
    }

    // Método para limpar os campos de entrada
    private void limparCampos() {
        clienteNomeField.setText("");
        horaInicioBox.setSelectedIndex(0);
        horaFimBox.setSelectedIndex(0);
        campoComboBox.setSelectedIndex(0);
        dataPicker.getModel().setValue(null); // Limpar o JDatePicker
    }

    // Método para atualizar a tabela de reservas
    public void atualizarTabela() {
        tableModel.setRowCount(0); // Limpa a tabela atual
        List<Reserva> reservas = reservaController.listarReservas(); // Busca todas as reservas
        for (Reserva reserva : reservas) {
            tableModel.addRow(new Object[] {
                reserva.getId(),
                reserva.getData(),
                reserva.getHoraInicio(),
                reserva.getHoraFim(),
                reserva.getCampo().getNome(),
                reserva.getCliente().getNome(),
                "R$"+reserva.getPrecoReserva()+",00"
            });
        }
    }
}
